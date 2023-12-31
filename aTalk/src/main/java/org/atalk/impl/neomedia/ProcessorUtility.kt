/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license. See terms of license at gnu.org.
 */
package org.atalk.impl.neomedia

import timber.log.Timber
import javax.media.ControllerClosedEvent
import javax.media.ControllerErrorEvent
import javax.media.ControllerEvent
import javax.media.ControllerListener
import javax.media.Processor

/**
 * A utility class that provides utility functions when working with processors.
 *
 * @author Emil Ivov
 * @author Ken Larson
 * @author Lyubomir Marinov
 * @author Eng Chong Meng
 */
class ProcessorUtility
/**
 * Initializes a new `ProcessorUtility` instance.
 */
    : ControllerListener {
    /**
     * The `Object` used for syncing when waiting for a processor to enter a specific state.
     */
    private val stateLock = Any()

    /**
     * The indicator which determines whether the waiting of this instance on a processor for it to
     * enter a specific state has failed.
     */
    private var failed = false

    /**
     * Gets the `Object` to use for syncing when waiting for a processor to enter a specific state.
     *
     * @return the `Object` to use for syncing when waiting for a processor to enter a
     * specific state
     */
    private fun getStateLock(): Any {
        return stateLock
    }

    /**
     * Specifies whether the wait operation has failed or completed with success.
     *
     * @param failed `true` if waiting has failed; `false`, otherwise
     */
    private fun setFailed(failed: Boolean) {
        this.failed = failed
    }

    /**
     * This method is called when an event is generated by a `Controller` that this listener
     * is registered with. We use the event to notify all waiting on our lock and record success or failure.
     *
     * @param ce The event generated.
     */
    override fun controllerUpdate(ce: ControllerEvent) {
        val stateLock = getStateLock()
        synchronized(stateLock) {

            // If there was an error during configure or
            // realize, the processor will be closed
            if (ce is ControllerClosedEvent) {
                if (ce is ControllerErrorEvent) Timber.w("ControllerErrorEvent: %s", ce.message) else Timber.d("ControllerClosedEvent: %s", ce.message)
                setFailed(true)
                // All controller events, send a notification to the waiting thread in waitForState method.
            }
            (stateLock as Object).notifyAll()
        }
    }

    /**
     * Waits until `processor` enters state and returns a boolean indicating success or
     * failure of the operation.
     *
     * @param processor Processor
     * @param state one of the Processor.XXXed state vars
     * @return `true` if the state has been reached; `false`, otherwise
     */
    @Synchronized
    fun waitForState(processor: Processor, state: Int): Boolean {
        processor.addControllerListener(this)
        setFailed(false)

        // Call the required method on the processor
        if (state == Processor.Configured) processor.configure() else if (state == Processor.Realized) processor.realize()
        var interrupted = false
        val stateLock = getStateLock()
        synchronized(stateLock) {
            // Wait until we get an event that confirms the
            // success of the method, or a failure event.
            // See JmStateListener inner class
            while (processor.state < state && !failed) {
                try {
                    // don't wait forever, there is some other problem where we wait on an already closed
                    // processor and we never leave this wait
//                        Instant startTime = Instant.now();
//                        stateLock.wait(WAIT_TIMEOUT * 1000);
//                        if (Duration.between(startTime, Instant.now()).getSeconds() >= WAIT_TIMEOUT) {
//                            // timeout reached we consider failure
//                            setFailed(true);
//                       }
                    val timeStart = System.currentTimeMillis()
                    (stateLock as Object).wait((WAIT_TIMEOUT * 1000).toLong())
                    if (System.currentTimeMillis() - timeStart > WAIT_TIMEOUT * 1000) {
                        // timeout reached we consider failure
                        setFailed(true)
                    }
                } catch (ie: InterruptedException) {
                    Timber.w(ie, "Interrupted while waiting on Processor %s for state %s", processor, state)
                    /*
                     * XXX It is not really clear what we should do. It seems that an
                     * InterruptedException may be thrown and the Processor will still work fine.
                     * Consequently, we cannot fail here. Besides, if the Processor fails, it will
                     * tell us with a ControllerEvent anyway and we will get out of the loop.
                     */interrupted = true
                    // processor.removeControllerListener(this);
                    // return false;
                }
            }
        }
        if (interrupted) Thread.currentThread().interrupt()
        processor.removeControllerListener(this)
        return !failed
    }

    companion object {
        /**
         * The maximum amount of time in seconds we will spend in waiting between
         * processor state changes to avoid locking threads forever.
         * Default value of 10 seconds, should be long enough.
         */
        private const val WAIT_TIMEOUT = 10
    }
}