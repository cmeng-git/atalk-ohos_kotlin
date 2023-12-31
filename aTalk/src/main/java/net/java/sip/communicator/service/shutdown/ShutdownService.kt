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
package net.java.sip.communicator.service.shutdown

/**
 * Abstracts the shutdown-related procedures of the application so that they
 * can be used throughout various bundles.
 *
 * @author Linus Wallgren
 */
interface ShutdownService {
    /**
     * Invokes the UI action commonly associated with the "File &gt; Quit" menu
     * item which begins the application shutdown procedure.
     *
     *
     * The method avoids duplication since the "File &gt; Quit" functionality
     * may be invoked not only from the main application menu but also from the
     * systray, for example.
     *
     */
    fun beginShutdown()
}