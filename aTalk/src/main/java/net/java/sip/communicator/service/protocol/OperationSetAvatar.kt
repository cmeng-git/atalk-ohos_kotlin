/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license. See terms of license at gnu.org.
 */
package net.java.sip.communicator.service.protocol

import net.java.sip.communicator.service.protocol.event.AvatarListener

/**
 * This interface is an extension of the operation set, meant to be implemented by protocols that
 * support user avatar.
 *
 * @author Damien Roth
 */
interface OperationSetAvatar : OperationSet {
    /**
     * Returns the maximum width of the avatar. This method should return 0 (zero) if there is no
     * maximum width.
     *
     * @return the maximum width of the avatar
     */
    fun getMaxWidth(): Int

    /**
     * Returns the maximum height of the avatar. This method should return 0 (zero) if there is no
     * maximum height.
     *
     * @return the maximum height of the avatar
     */
    fun getMaxHeight(): Int

    /**
     * Returns the maximum size of the avatar. This method should return 0 (zero) if there is no
     * maximum size.
     *
     * @return the maximum size of the avatar
     */
    fun getMaxSize(): Int

    /**
     * Defines a new avatar for this protocol
     *
     * @param avatar
     * the new avatar
     */
    fun setAvatar(avatar: ByteArray)

    /**
     * Returns the current avatar of this protocol. May return null if the account has no avatar
     *
     * @return avatar's bytes or null if no avatar set
     */
    fun getAvatar(): ByteArray?

    /**
     * Registers a listener that would receive events upon avatar changes.
     *
     * @param listener
     * a VCardAvatarListener that would receive events upon avatar changes.
     */
    fun addAvatarListener(listener: AvatarListener)

    /**
     * Removes the specified group change listener so that it won't receive any further events.
     *
     * @param listener
     * the VCardAvatarListener to remove
     */
    fun removeAvatarListener(listener: AvatarListener)
}