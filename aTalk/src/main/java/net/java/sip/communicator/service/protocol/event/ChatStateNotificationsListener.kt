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
package net.java.sip.communicator.service.protocol.event

import java.util.*

/**
 * This listener receives events whenever a contact has sent us a chat state notification. The
 * source contact and the exact type of the notification are indicated in
 * `TypingNotificationEvent` instances.
 *
 * @author Emil Ivov
 * @author Eng Chong Meng
 */
interface ChatStateNotificationsListener : EventListener {
    /**
     * Called to indicate that a remote `Contact` has sent us a chat state notification.
     *
     * @param event a `ChatStateNotificationEvent` containing the sender of the
     * notification and its type.
     */
    fun chatStateNotificationReceived(event: ChatStateNotificationEvent)

    /**
     * Called to indicate that sending chat state notification has failed.
     *
     * @param event a `ChatStateNotificationEvent` containing the sender of the
     * notification and its type.
     */
    fun chatStateNotificationDeliveryFailed(event: ChatStateNotificationEvent)
}