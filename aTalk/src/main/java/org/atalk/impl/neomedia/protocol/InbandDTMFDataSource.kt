/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 * 
 * Distributable under LGPL license. See terms of license at gnu.org.
 */
package org.atalk.impl.neomedia.protocol

import org.atalk.service.neomedia.DTMFInbandTone

/**
 * All datasources that support inband DTMF functionalities implement `InbandDTMFDataSource`.
 *
 * @author Vincent Lucas
 */
interface InbandDTMFDataSource {
    /**
     * Adds a new inband DTMF tone to send.
     *
     * @param tone
     * the DTMF tone to send.
     */
    fun addDTMF(tone: DTMFInbandTone)
}