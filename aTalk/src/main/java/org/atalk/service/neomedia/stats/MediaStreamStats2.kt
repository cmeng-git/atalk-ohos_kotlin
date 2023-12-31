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
package org.atalk.service.neomedia.stats

import org.atalk.service.neomedia.MediaStreamStats

/**
 * An extended interface for accessing the statistics of a [MediaStream].
 *
 * The reason to extend the [MediaStreamStats] interface rather than
 * adding methods into it is to allow the implementation to reside in a separate class.
 * This is desirable in order to:
 * 1. Help to keep the old interface for backward compatibility.
 * 2. Provide a "clean" place where future code can be added, thus avoiding
 * further cluttering of the already overly complicated
 * [org.atalk.impl.neomedia.MediaStreamStatsImpl].
 *
 * @author Boris Grozev
 * @author Eng Chong Meng
 */
interface MediaStreamStats2 : MediaStreamStats {
    /**
     * @return the instance which keeps aggregate statistics for the associated
     * [MediaStream] in the receive direction.
     */
    val receiveStats: ReceiveTrackStats

    /**
     * @return the instance which keeps aggregate statistics for the associated
     * [MediaStream] in the send direction.
     */
    val sendStats: SendTrackStats

    /**
     * @return the instance which keeps statistics for a particular SSRC in the receive direction.
     */
    fun getReceiveStats(ssrc: Long): ReceiveTrackStats?

    /**
     * @return the instance which keeps statistics for a particular SSRC in the send direction.
     */
    fun getSendStats(ssrc: Long): SendTrackStats

    /**
     * @return all per-SSRC statistics for the send direction.
     */
    val allSendStats: Collection<SendTrackStats?>

    /**
     * @return all per-SSRC statistics for the receive direction.
     */
    val allReceiveStats: Collection<ReceiveTrackStats?>

    /**
     * Clears send ssrc stats.
     *
     * @param ssrc the ssrc to clear.
     */
    fun clearSendSsrc(ssrc: Long)
}