/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package net.java.sip.communicator.service.protocol

/**
 * This interface represents an invitation, which is send from a whiteboard participant to another
 * user in order to invite this user to join the whiteboard.
 *
 * @author Yana Stamcheva
 */
interface WhiteboardInvitation {
    /**
     * Returns the `WhiteboardSession`, which is the target of this invitation. The
     * whiteboard returned by this method will be the one to which the user is invited to join to.
     *
     * @return the `WhiteboardSession`, which is the target of this invitation
     */
    fun getTargetWhiteboard(): WhiteboardSession?

    /**
     * Returns the password to use when joining the whiteboard.
     *
     * @return the password to use when joining the whiteboard
     */
    fun getWhiteboardPassword(): ByteArray?

    /**
     * Returns the `WhiteboardParticipant` that sent this invitation.
     *
     * @return the `WhiteboardParticipant` that sent this invitation.
     */
    fun getInviter(): String?

    /**
     * Returns the reason of this invitation, or null if there is no reason specified.
     *
     * @return the reason of this invitation, or null if there is no reason specified
     */
    fun getReason(): String?
}