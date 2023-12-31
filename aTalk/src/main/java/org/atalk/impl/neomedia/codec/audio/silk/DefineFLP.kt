/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 * 
 * Distributable under LGPL license. See terms of license at gnu.org.
 */
package org.atalk.impl.neomedia.codec.audio.silk

/**
 *
 * @author Jing Dai
 * @author Dingxin Xu
 */
object DefineFLP {
    /** */ /* Pitch estimator */
    /** */ /* Level of noise floor for whitening filter LPC analysis in pitch analysis */
    const val FIND_PITCH_WHITE_NOISE_FRACTION = 1e-3f

    /* Bandwidth expansion for whitening filter in pitch analysis */
    const val FIND_PITCH_BANDWITH_EXPANSION = 0.99f

    /* Threshold used by pitch estimator for early escape */
    const val FIND_PITCH_CORRELATION_THRESHOLD_HC_MODE = 0.7f
    const val FIND_PITCH_CORRELATION_THRESHOLD_MC_MODE = 0.75f
    const val FIND_PITCH_CORRELATION_THRESHOLD_LC_MODE = 0.8f
    /** */ /* Long-Term predictor */
    /** */ /* Regualarization factor for correlation matrix. Equivalent to adding noise at -50 dB */
    const val FIND_LTP_COND_FAC = 1e-5f
    const val FIND_LPC_COND_FAC = 6e-5f

    /* Find prediction coefficients defines */
    const val LTP_DAMPING = 0.001f
    const val LTP_SMOOTHING = 0.1f

    /* LTP quantization settings */
    const val MU_LTP_QUANT_NB = 0.03f
    const val MU_LTP_QUANT_MB = 0.025f
    const val MU_LTP_QUANT_WB = 0.02f
    const val MU_LTP_QUANT_SWB = 0.016f
    /** */ /* High pass filtering */
    /** */ /* Smoothing parameters for low end of pitch frequency range estimation */
    const val VARIABLE_HP_SMTH_COEF1 = 0.1f
    const val VARIABLE_HP_SMTH_COEF2 = 0.015f

    /* Min and max values for low end of pitch frequency range estimation */
    const val VARIABLE_HP_MIN_FREQ = 80.0f
    const val VARIABLE_HP_MAX_FREQ = 150.0f

    /*
	 * Max absolute difference between log2 of pitch frequency and smoother state, to enter the
	 * smoother
	 */
    const val VARIABLE_HP_MAX_DELTA_FREQ = 0.4f
    /** */ /* Various */
    /** */ /* Required speech activity for counting frame as active */
    const val WB_DETECT_ACTIVE_SPEECH_LEVEL_THRES = 0.7f
    const val SPEECH_ACTIVITY_DTX_THRES = 0.1f

    /* Speech Activity LBRR enable threshold (needs tuning) */
    var LBRR_SPEECH_ACTIVITY_THRES = 0.5f
    const val Q14_CONVERSION_FAC = 6.1035e-005f // 1 / 2^14
}