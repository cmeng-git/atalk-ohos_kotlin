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
 * The `ChatRoomMemberPropertyChangeListener` receives events notifying interested parties
 * that a property of the corresponding chat room member (e.g. such as its nickname) has been
 * modified.
 *
 * @author Emil Ivov
 * @author Yana Stamcheva
 * @author Eng Chong Meng
 */
interface ChatRoomMemberPropertyChangeListener : EventListener {
    /**
     * Called to indicate that a chat room member property has been modified.
     *
     * @param event the `ChatRoomMemberPropertyChangeEvent` containing the name of
     * the property that has just changed, as well as its old and new values.
     */
    fun chatRoomPropertyChanged(event: ChatRoomMemberPropertyChangeEvent?)
}