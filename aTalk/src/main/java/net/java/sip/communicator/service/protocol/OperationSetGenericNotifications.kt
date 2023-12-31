/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license. See terms of license at gnu.org.
 */
package net.java.sip.communicator.service.protocol

import net.java.sip.communicator.service.protocol.event.GenericEventListener

/**
 * Provides notification for custom/generic events and possibility to generate such events.
 *
 * @author Damian Minkov
 */
interface OperationSetGenericNotifications : OperationSet {
    /**
     * Generates new generic event notification and send it to the supplied contact.
     *
     * @param contact
     * the contact to receive the event notification.
     * @param eventName
     * the event name of the notification.
     * @param eventValue
     * the event value of the notification.
     */
    fun notifyForEvent(contact: Contact?, eventName: String?, eventValue: String?)

    /**
     * Generates new generic event notification and send it to the supplied contact.
     *
     * @param jid
     * the contact jid which will receive the event notification.
     * @param eventName
     * the event name of the notification.
     * @param eventValue
     * the event value of the notification.
     */
    fun notifyForEvent(jid: String?, eventName: String?, eventValue: String?)

    /**
     * Generates new generic event notification and send it to the supplied contact.
     *
     * @param jid
     * the contact jid which will receive the event notification.
     * @param eventName
     * the event name of the notification.
     * @param eventValue
     * the event value of the notification.
     * @param source
     * the source that will be reported in the event.
     */
    fun notifyForEvent(jid: String?, eventName: String?, eventValue: String?, source: String?)

    /**
     * Registers a `GenericEventListener` with this operation set so that it gets
     * notifications for new event notifications.
     *
     * @param eventName
     * register the listener for certain event name.
     * @param listener
     * the `GenericEventListener` to register.
     */
    fun addGenericEventListener(eventName: String?, listener: GenericEventListener?)

    /**
     * Unregisters `listener` so that it won't receive any further notifications upon new
     * event notifications.
     *
     * @param eventName
     * unregister the listener for certain event name.
     * @param listener
     * the `GenericEventListener` to unregister.
     */
    fun removeGenericEventListener(eventName: String?, listener: GenericEventListener?)
}