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
 * Represents a listener of changes in the capabilities of a `Contact` as known by an
 * associated protocol provider delivered in the form of `ContactCapabilitiesEvent`s.
 *
 * @author Lubomir Marinov
 * @author Eng Chong Meng
 */
interface ContactCapabilitiesListener : EventListener {
    /**
     * Notifies this listener that the list of the `OperationSet` capabilities of a
     * `Contact` has changed.
     *
     * @param event a `ContactCapabilitiesEvent` which specifies the
     * `Contact` whose list of `OperationSet` capabilities has changed
     */
    fun supportedOperationSetsChanged(event: ContactCapabilitiesEvent?)
}