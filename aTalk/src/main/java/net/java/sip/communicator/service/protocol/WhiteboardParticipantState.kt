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
package net.java.sip.communicator.service.protocol

/**
 * The WhiteboardParticipantState class reflects the current state of a whiteboard participant.
 *
 * @author Julien Waechter
 * @author Emil Ivov
 */
class WhiteboardParticipantState
/**
 * Create a participant whiteboard state object with a value corresponding to the specified
 * string.
 *
 * @param whiteboardParticipantState a string representation of the state.
 */ private constructor(
        /**
         * A string representationf this Participant Whiteboard State. Could be _CONNECTED, _FAILED ....
         */
        private val whiteboardStateStr: String) {
    /**
     * Returns a String representation of tha WhiteboardParticipantSte.
     *
     * @return A string value (one of the _BUSY, _CONNECTED, _CONNECTING, _DISCONNECTED, _FAILED
     * constants) representing this whiteboard participant state).
     */
    fun getStateString(): String {
        return whiteboardStateStr
    }

    /**
     * Returns a string represenation of this whiteboard state. Strings returned by this method have
     * the following format:
     *
     *
     * WhiteboardParticipantState:<STATE_STRING>
    </STATE_STRING> *
     *
     * and are meant to be used for loggin/debugging purposes.
     *
     * @return a string representation of this object.
     */
    override fun toString(): String {
        return javaClass.name + ":" + getStateString()
    }

    companion object {
        /**
         * This constant value indicates a String representation of the UNKNOWN whiteboard state. <br></br>
         * This constant has the String value "Unknown".
         */
        const val _UNKNOWN = "Unknown"

        /**
         * This constant value indicates that the state of the whiteboard participant is is UNKNOWN -
         * which means that there is no information on the state for the time being (this constant
         * should be used as a default value for newly created whiteboard participant that don't yet
         * have an attributed whiteboard state.
         */
        val UNKNOWN = WhiteboardParticipantState(
                _UNKNOWN)

        /**
         * This constant value indicates a String representation of the INITIATING_WHITEBOARD whiteboard
         * state. <br></br>
         * This constant has the String value "Initiating Whiteboard".
         */
        const val _INITIATING_WHITEBOARD = "Initiating Whiteboard"

        /**
         * This constant value indicates that the state of the whiteboard participant is is
         * INITIATING_WHITEBOARD - which means that we're currently trying to open a socket and send our
         * request. In the case of SIP for example we will leave this state the moment we receive a "100
         * Trying" request from a proxy or the remote side.
         */
        val INITIATING_WHITEBOARD = WhiteboardParticipantState(
                _INITIATING_WHITEBOARD)

        /**
         * This constant value indicates a String representation of the CONNECTING whiteboard state. <br></br>
         * This constant has the String value "Connecting".
         */
        const val _CONNECTING = "Connecting"

        /**
         * This constant value indicates that the state of the whiteboard participant is is CONNECTING -
         * which means that a network connection to that participant is currently being established.
         */
        val CONNECTING = WhiteboardParticipantState(
                _CONNECTING)

        /**
         * This constant value indicates a String representation of the INCOMING_WHITEBOARD whiteboard
         * state. <br></br>
         * This constant has the String value "Incoming Whiteboard".
         */
        const val _INCOMING_WHITEBOARD = "Incoming Whiteboard"

        /**
         * This constant value indicates that the state of the whiteboard participant is is
         * INCOMING_WHITEBOARD - which means that the participant is willing to start a whiteboard with
         * us. At that point local side should be playing a sound or a graphical alert (the phone is
         * ringing).
         */
        val INCOMING_WHITEBOARD = WhiteboardParticipantState(
                _INCOMING_WHITEBOARD)

        /**
         * This constant value indicates a String representation of the CONNECTED whiteboard state. <br></br>
         * This constant has the String value "Connected".
         */
        const val _CONNECTED = "Connected"

        /**
         * This constant value indicates that the state of the whiteboard participant is is CONNECTED -
         * which means that there is an ongoing whiteboard with that participant.
         */
        val CONNECTED = WhiteboardParticipantState(
                _CONNECTED)

        /**
         * This constant value indicates a String representation of the DISCONNECTED whiteboard state. <br></br>
         * This constant has the String value "Disconnected".
         */
        const val _DISCONNECTED = "Disconnected"

        /**
         * This constant value indicates that the state of the whiteboard participant is is DISCONNECTET
         * - which means that this participant is not participating :) in the whiteboard any more.
         */
        val DISCONNECTED = WhiteboardParticipantState(
                _DISCONNECTED)

        /**
         * This constant value indicates a String representation of the BUSY whiteboard state. <br></br>
         * This constant has the String value "Busy".
         */
        const val _BUSY = "Busy"

        /**
         * This constant value indicates that the state of the whiteboard participant is is BUSY - which
         * means that an attempt to establish a whiteboard with that participant has been made and that
         * it has been turned down by them (e.g. because they were already in a whiteboard).
         */
        val BUSY = WhiteboardParticipantState(_BUSY)

        /**
         * This constant value indicates a String representation of the FAILED whiteboard state. <br></br>
         * This constant has the String value "Failed".
         */
        const val _FAILED = "Failed"

        /**
         * This constant value indicates that the state of the whiteboard participant is is ON_HOLD -
         * which means that an attempt to establish a whiteboard with that participant has failed for an
         * unexpected reason.
         */
        val FAILED = WhiteboardParticipantState(_FAILED)
    }
}