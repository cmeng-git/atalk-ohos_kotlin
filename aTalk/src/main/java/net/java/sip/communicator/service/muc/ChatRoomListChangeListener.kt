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
package net.java.sip.communicator.service.muc

/**
 *
 * @author Yana Stamcheva
 * @author Eng Chong Meng
 */
interface ChatRoomListChangeListener {
    /**
     * Indicates that a change has occurred in the chat room data list.
     */
    fun contentChanged(evt: ChatRoomListChangeEvent)
}