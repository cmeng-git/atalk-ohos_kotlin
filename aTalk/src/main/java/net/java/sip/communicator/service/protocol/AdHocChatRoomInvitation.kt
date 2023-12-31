/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license. See terms of license at gnu.org.
 */
package net.java.sip.communicator.service.protocol

import org.jxmpp.jid.EntityFullJid

/**
 * This interface represents an invitation, which is send from an ad-hoc chat room participant to
 * another user in order to invite this user to join the ad-hoc chat room.
 *
 * @author Valentin Martinet
 */
interface AdHocChatRoomInvitation {
    /**
     * Returns the `AdHocChatRoom`, which is the target of this invitation. The ad-hoc chat
     * room returned by this method will be the room to which the user is invited to join to.
     *
     * @return the `AdHocChatRoom`, which is the target of this invitation
     */
    fun getTargetAdHocChatRoom(): AdHocChatRoom

    /**
     * Returns the `Contact` that sent this invitation.
     *
     * @return the `Contact` that sent this invitation.
     */
    fun getInviter(): EntityFullJid

    /**
     * Returns the reason of this invitation, or null if there is no reason.
     *
     * @return the reason of this invitation, or null if there is no reason
     */
    fun getReason(): String?
}