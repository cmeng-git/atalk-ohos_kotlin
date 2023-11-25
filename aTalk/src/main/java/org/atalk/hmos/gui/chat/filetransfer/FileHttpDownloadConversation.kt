/*
 * aTalk, android VoIP and Instant Messaging client
 * Copyright 2014 Eng Chong Meng
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.atalk.hmos.gui.chat.filetransfer

import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.java.sip.communicator.impl.filehistory.FileHistoryServiceImpl
import net.java.sip.communicator.impl.protocol.jabber.HttpFileDownloadJabberImpl
import net.java.sip.communicator.service.filehistory.FileRecord
import net.java.sip.communicator.service.protocol.event.FileTransferStatusChangeEvent
import net.java.sip.communicator.service.protocol.event.FileTransferStatusListener
import net.java.sip.communicator.util.ConfigurationUtils
import net.java.sip.communicator.util.GuiUtils
import org.atalk.hmos.R
import org.atalk.hmos.aTalkApp
import org.atalk.hmos.gui.AndroidGUIActivator
import org.atalk.hmos.gui.chat.ChatFragment
import org.atalk.hmos.gui.chat.ChatMessage
import org.atalk.persistance.FileBackend
import java.io.File
import java.util.*

/**
 * The `ReceiveFileConversationComponent` is the component shown in the conversation area
 * of the chat window to display a incoming file transfer.
 *
 * @author Eng Chong Meng
 */
class FileHttpDownloadConversation private constructor(
        cPanel: ChatFragment,
        dir: String,
) : FileTransferConversation(cPanel, dir), FileTransferStatusListener {

    private lateinit var httpFileTransferJabber: HttpFileDownloadJabberImpl
    override var xferStatus = 0
    private var fileSize = 0L
    private var fileName: String? = null
    private var mSender: String? = null

    /* previousDownloads <DownloadJobId, DownloadFileMimeType Link> */ // private final Hashtable<Long, String> mimeTypes = new Hashtable<>();
    /* DownloadManager Broadcast Receiver Handler */ // private DownloadReceiver downloadReceiver = null;
    private lateinit var mFHS: FileHistoryServiceImpl

    fun httpFileDownloadConversionForm(
            inflater: LayoutInflater, msgViewHolder: ChatFragment.MessageViewHolder,
            container: ViewGroup?, id: Int, init: Boolean,
    ): View? {
        val convertView = inflateViewForFileTransfer(inflater, msgViewHolder, container, init)

        msgViewId = id
        mFileTransfer = httpFileTransferJabber
        mFileTransfer.addStatusListener(this)

        val dnLink = httpFileTransferJabber.getDnLink()
        xferFile = createOutFile(httpFileTransferJabber.getLocalFile()!!)
        fileName = httpFileTransferJabber.getFileName()
        fileSize = httpFileTransferJabber.getFileSize()
        mEncryption = httpFileTransferJabber.getEncryptionType()
        setEncState(mEncryption)

        messageViewHolder.stickerView.setImageDrawable(null)
        messageViewHolder.fileLabel.text = getFileLabel(fileName, fileSize)

        /* Must keep track of file transfer status as Android always request view redraw on
		listView scrolling, new message send or received */
        val status = xferStatus
        if (status != FileTransferStatusChangeEvent.DECLINED
                && status != FileTransferStatusChangeEvent.COMPLETED) {
            // Must reset button image to fileIcon on new(); else reused view may contain an old thumbnail image
            messageViewHolder.fileIcon.setImageResource(R.drawable.file_icon)

            messageViewHolder.acceptButton.setOnClickListener { v: View? ->
                startDownload()
            }
            messageViewHolder.declineButton.setOnClickListener { v: View? ->
                updateView(FileTransferStatusChangeEvent.DECLINED, null)
            }

            updateXferFileViewState(FileTransferStatusChangeEvent.WAITING,
                aTalkApp.getResString(R.string.xFile_FILE_TRANSFER_REQUEST_RECEIVED, mSender))

            // Do not auto retry if it had failed previously; otherwise ANR if multiple such items exist
            if (FileRecord.STATUS_FAILED == xferStatus) {
                updateView(FileTransferStatusChangeEvent.FAILED, dnLink)
            }
            else {
                doInit()
            }
        }
        else {
            updateView(status, null)
        }
        return convertView
    }

    /**
     * Handles file transfer status changes. Updates the interface to reflect the changes.
     */
    override fun updateView(status: Int, reason: String?) {
        // setXferStatus(status);
        var statusText: String? = null
        if (FileTransferStatusChangeEvent.COMPLETED != status) {
            updateFTStatus(status, null, ChatMessage.MESSAGE_HTTP_FILE_DOWNLOAD)
        }

        when (status) {
            FileTransferStatusChangeEvent.PREPARING -> statusText = aTalkApp.getResString(R.string.xFile_FILE_TRANSFER_PREPARING, mSender)
            FileTransferStatusChangeEvent.IN_PROGRESS -> {
                statusText = aTalkApp.getResString(R.string.xFile_FILE_RECEIVING_FROM, mSender)
                mChatFragment.addActiveFileTransfer(httpFileTransferJabber.getID(), httpFileTransferJabber, msgViewId)
            }

            FileTransferStatusChangeEvent.COMPLETED -> {
                statusText = aTalkApp.getResString(R.string.xFile_FILE_RECEIVE_COMPLETED, mSender)
                if (xferFile == null) { // Android view redraw happen?
                    xferFile = mChatFragment.chatListAdapter!!.getFileName(msgViewId)!!
                }
                // Update the DB record status
                updateFTStatus(status, xferFile.toString(), ChatMessage.MESSAGE_FILE_TRANSFER_HISTORY)
            }

            FileTransferStatusChangeEvent.FAILED -> {
                statusText = aTalkApp.getResString(R.string.xFile_FILE_RECEIVE_FAILED, mSender)
                if (!TextUtils.isEmpty(reason)) {
                    statusText += "\n $reason"
                }
            }

            FileTransferStatusChangeEvent.CANCELED -> {
                statusText = aTalkApp.getResString(R.string.xFile_FILE_TRANSFER_CANCELED)
                if (!TextUtils.isEmpty(reason)) {
                    statusText += "\n$reason"
                }
            }

            FileTransferStatusChangeEvent.DECLINED ->
                statusText = aTalkApp.getResString(R.string.xFile_FILE_TRANSFER_DECLINED)
        }
        updateXferFileViewState(status, statusText)
        mChatFragment.scrollToBottom()
    }

    /**
     * Update the file transfer status into the DB, and also the msgCache to ensure the file send request will not
     * get trigger again. The msgCache record will be used for DisplayMessage on chat session resume.
     *
     * @param status File transfer status
     * @param fileName the downloaded fileName
     * @param msgType File transfer type see ChatMessage MESSAGE_FILE_
     */
    private fun updateFTStatus(status: Int, fileName: String?, msgType: Int) {
        mFHS.updateFTStatusToDB(msgUuid, status, fileName, mEncryption, msgType)
        mChatFragment.updateFTStatus(msgUuid, status, fileName, mEncryption, msgType)
    }

    /**
     * Handles status changes in file transfer.
     */
    override fun statusChanged(event: FileTransferStatusChangeEvent) {
        val fileTransfer = event.getFileTransfer()
        val status = event.getNewStatus()
        val reason = event.getReason()

        // Event thread - Must execute in UiThread to Update UI information
        runOnUiThread {
            updateView(status, reason)
            if (status == FileTransferStatusChangeEvent.COMPLETED
                    || status == FileTransferStatusChangeEvent.CANCELED
                    || status == FileTransferStatusChangeEvent.FAILED
                    || status == FileTransferStatusChangeEvent.DECLINED) {
                // must update this in UI, otherwise the status is not being updated to FileRecord
                fileTransfer.removeStatusListener(this)
            }
        }
    }

    /**
     * Creates the local file to save to.
     *
     * @return the local created file to save to.
     */
    private fun createOutFile(infile: File): File {
        val fileName = infile.name
        val mimeType = FileBackend.getMimeType(activity, Uri.fromFile(infile))
        setTransferFilePath(fileName, mimeType)

        // Change the file name to the name we would use on the local file system.
        if (xferFile!!.name != fileName) {
            val label = getFileLabel(xferFile!!.name, infile.length())
            messageViewHolder.fileLabel.text = label
        }
        return xferFile!!
    }

    /**
     * Returns the label to show on the progress bar.
     *
     * @param bytesString the bytes that have been transferred
     *
     * @return the label to show on the progress bar
     */
    override fun getProgressLabel(bytesString: Long): String {
        return aTalkApp.getResString(R.string.service_gui_RECEIVED, bytesString)
    }

    /**
     * Init the HttpFileTransferJabberImpl, and retrieve server fileSize info.
     * Proceed to startDownload() if isAutoAcceptFile() is met.
     */
    private fun doInit() {
        httpFileTransferJabber.initHttpFileDownload()
        fileSize = httpFileTransferJabber.getFileSize()
        setFileTransfer(httpFileTransferJabber, fileSize)
        messageViewHolder.fileLabel.text = getFileLabel(fileName, fileSize)

        if (fileSize <= 0) {
            aTalkApp.showToastMessage(R.string.service_gui_FILE_DOES_NOT_EXIST)
        }
        else if (ConfigurationUtils.isAutoAcceptFile(fileSize)) {
            startDownload()
        }
    }

    /**
     * Start Http download and save the file into mXerFile.
     * Method fired when the file transfer chat message 'Accept' is clicked.
     * Or when the fileSize is within autoAccept fileSize limit.
     */
    private fun startDownload() {
        httpFileTransferJabber.download(xferFile)
    }

    companion object {
        /**
         * Creates a `FileHttpDownloadConversation`.
         *
         * @param cPanel the chat panel
         * @param sender the message `sender`
         * @param date the date
         */
        // Constructor used by ChatFragment to start handle ReceiveFileTransferRequest
        fun newInstance(
                cPanel: ChatFragment, sender: String?,
                httpFileTransferJabber: HttpFileDownloadJabberImpl, date: Date,
        ): FileHttpDownloadConversation {
            val fragmentRFC = FileHttpDownloadConversation(cPanel, FileRecord.IN)
            fragmentRFC.mSender = sender
            fragmentRFC.httpFileTransferJabber = httpFileTransferJabber
            fragmentRFC.mDate = GuiUtils.formatDateTime(date)
            fragmentRFC.msgUuid = httpFileTransferJabber.getID()
            fragmentRFC.xferStatus = httpFileTransferJabber.getStatus()
            fragmentRFC.mFHS = AndroidGUIActivator.fileHistoryService as FileHistoryServiceImpl
            return fragmentRFC
        }
    }
}