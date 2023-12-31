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
 * The ChatRoomPropertyChangeListener receives events notifying interested parties that a property
 * of the corresponding chat room (e.g. such as its subject or type) has been modified or failed to
 * be modified. <br></br>
 * The modification of a property could fail, because the implementation doesn't support such a
 * property.
 *
 * @author Emil Ivov
 * @author Yana Stamcheva
 * @author Eng Chong Meng
 */
interface ChatRoomPropertyChangeListener : EventListener {
    /**
     * Called to indicate that a chat room property has been modified.
     *
     * @param event the ChatRoomPropertyChangeEvent containing the name of the property that has just
     * changed, as well as its old and new values.
     */
    fun chatRoomPropertyChanged(event: ChatRoomPropertyChangeEvent)

    /**
     * Called to indicate that a change of a chat room property has failed. The modification of a
     * property could fail, because the implementation doesn't support such a property.
     *
     * @param event the ChatRoomPropertyChangeFailedEvent containing the name of the property that has
     * failed, as well as its old and new values.
     */
    fun chatRoomPropertyChangeFailed(event: ChatRoomPropertyChangeFailedEvent)
}