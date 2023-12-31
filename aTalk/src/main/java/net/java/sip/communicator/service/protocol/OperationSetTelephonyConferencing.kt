/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license. See terms of license at gnu.org.
 */
package net.java.sip.communicator.service.protocol

import org.jxmpp.stringprep.XmppStringprepException

/**
 * Provides operations necessary to create and handle conferencing calls.
 *
 * @author Emil Ivov
 * @author Lubomir Marinov
 * @author Eng Chong Meng
 */
interface OperationSetTelephonyConferencing : OperationSet {
    /**
     * Creates a conference call with the specified callees as call peers.
     *
     * @param callees the list of addresses that we should call
     * @return the newly created conference call containing all CallPeers
     * @throws OperationFailedException if establishing the conference call fails
     * @throws OperationNotSupportedException if the provider does not have any conferencing features.
     */
    @Throws(OperationFailedException::class, OperationNotSupportedException::class, XmppStringprepException::class)
    fun createConfCall(callees: Array<String>): Call<*>

    /**
     * Creates a conference call with the specified callees as call peers.
     *
     * @param callees the list of addresses that we should call
     * @param conference the `CallConference` which represents the state of the telephony conference
     * into which the specified callees are to be invited
     * @return the newly created conference call containing all CallPeers
     * @throws OperationFailedException if establishing the conference call fails
     * @throws OperationNotSupportedException if the provider does not have any conferencing features.
     */
    @Throws(OperationFailedException::class, OperationNotSupportedException::class, XmppStringprepException::class)
    fun createConfCall(callees: Array<String>, conference: CallConference?): Call<*>

    /**
     * Invites the callee represented by the specified uri to an already existing call. The
     * difference between this method and createConfCall is that inviteCalleeToCall allows a user to
     * transform an existing 1-to-1 call into a conference call, or add new peers to an already
     * established conference.
     *
     * @param uri the callee to invite to an existing conf call.
     * @param call the call that we should invite the callee to.
     * @return the CallPeer object corresponding to the callee represented by the specified uri.
     * @throws OperationFailedException if inviting the specified callee to the specified call fails
     * @throws OperationNotSupportedException if allowing additional callees to a pre-established call is not supported.
     */
    @Throws(OperationFailedException::class, OperationNotSupportedException::class, XmppStringprepException::class)
    fun inviteCalleeToCall(uri: String, call: Call<*>): CallPeer

    /**
     * Sets up a conference with no participants, which members of `chatRoom` can join.
     * Returns a `ConferenceDescription` that describes how the call can be joined.
     *
     * @param chatRoom the `ChatRoom` for which to set up a conference.
     * @return a `ConferenceDescription` corresponding to the created conference.
     */
    fun setupConference(chatRoom: ChatRoom): ConferenceDescription?
}