/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license. See terms of license at gnu.org.
 */
package net.java.sip.communicator.service.protocol

/**
 * An operation set that allows "inband" change of the account password
 *
 * @author Boris Grozev
 */
interface OperationSetChangePassword : OperationSet {
    /**
     * Changes the account password to newPass
     *
     * @param newPass
     * the new password.
     * @throws IllegalStateException
     * if the account is not registered.
     * @throws OperationFailedException
     * if the change failed for another reason.
     */
    @Throws(IllegalStateException::class, OperationFailedException::class)
    fun changePassword(newPass: String?)

    /**
     * Whether password changes are supported.
     *
     * @return True if the server supports password change, false otherwise.
     */
    fun supportsPasswordChange(): Boolean
}