/*
 * Copyright @ 2015 Atlassian Pty Ltd
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
package org.atalk.impl.neomedia.device

import javax.media.Buffer
import javax.media.rtp.ReceiveStream

/**
 * Represents a listener for every packet which is read by a
 * `MediaDevice`
 *
 * @author Boris Grozev
 * @author Nik Vaessen
 */
interface ReceiveStreamBufferListener {
    /**
     * Notify the listener that the data in the `Buffer` (as byte[])
     * has been read by the MediaDevice the listener is attached to
     *
     * @param receiveStream the `ReceiveStream` which provided the
     * packet(s)
     * @param buffer the `Buffer` into which the packets has been read
     */
    fun bufferReceived(receiveStream: ReceiveStream?, buffer: Buffer?)
}