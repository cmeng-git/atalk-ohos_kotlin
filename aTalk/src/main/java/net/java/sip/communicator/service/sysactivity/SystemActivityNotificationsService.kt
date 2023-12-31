/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.java.sip.communicator.service.sysactivity

/**
 * Listens for some system specific events such as sleep, wake, network change,
 * desktop activity, screensaver etc. and informs the registered listeners.
 *
 * @author Damian Minkov
 */
interface SystemActivityNotificationsService {
    /**
     * Registers a listener that would be notified of changes that have occurred
     * in the underlying system.
     *
     * @param listener the listener that we'd like to register for changes in
     * the underlying system.
     */
    fun addSystemActivityChangeListener(
            listener: SystemActivityChangeListener?)

    /**
     * Remove the specified listener so that it won't receive further
     * notifications of changes that occur in the underlying system
     *
     * @param listener the listener to remove.
     */
    fun removeSystemActivityChangeListener(
            listener: SystemActivityChangeListener?)

    /**
     * Registers a listener that would be notified for idle of the system
     * for `idleTime`.
     *
     * @param idleTime the time in milliseconds after which we will consider
     * system to be idle. This doesn't count when system seems idle as
     * monitor is off or screensaver is on, or desktop is locked.
     * @param listener the listener that we'd like to register for changes in
     * the underlying system.
     */
    fun addIdleSystemChangeListener(
            idleTime: Long,
            listener: SystemActivityChangeListener?)

    /**
     * Remove the specified listener so that it won't receive further
     * notifications for idle system.
     *
     * @param listener the listener to remove.
     */
    fun removeIdleSystemChangeListener(
            listener: SystemActivityChangeListener?)

    /**
     * Can check whether an event id is supported on
     * current operation system.
     * @param eventID the event to check.
     * @return whether the supplied event id is supported.
     */
    fun isSupported(eventID: Int): Boolean

    /**
     * The time since last user input. The time the system has been idle.
     * Or -1 if there is no such information or error has occured.
     * @return time the system has been idle.
     */
    fun getTimeSinceLastInput(): Long
}