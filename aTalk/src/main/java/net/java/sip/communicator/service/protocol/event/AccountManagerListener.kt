/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 * * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
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
 * Represents a listener receiving notifications from [AccountManager].
 *
 * @author Lubomir Marinov
 */
interface AccountManagerListener : EventListener {
    /**
     * Notifies this listener about an event fired by a specific `AccountManager`.
     *
     * @param event the `AccountManagerEvent` describing the `AccountManager`
     * firing the notification and the other details of the specific notification.
     */
    fun handleAccountManagerEvent(event: AccountManagerEvent)
}