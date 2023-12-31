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
 * An event listener that should be implemented by parties interested in changes that occur in the
 * registration state of a `ProtocolProviderService`.
 *
 * @author Emil Ivov
 * @author Eng Chong Meng
 */
interface RegistrationStateChangeListener : EventListener {
    /**
     * The method is called by a `ProtocolProviderService` implementation whenever a change
     * in its registration state has occurred.
     *
     * @param evt a `RegistrationStateChangeEvent` which describes the registration state change.
     */
    fun registrationStateChanged(evt: RegistrationStateChangeEvent)
}