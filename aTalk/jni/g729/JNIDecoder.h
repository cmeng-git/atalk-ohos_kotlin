#include <jni.h>

/* Header for class org_atalk_impl_neomedia_codec_audio_g729_JNIDecoder */

#ifndef _Included_org_atalk_impl_neomedia_codec_audio_g729_JNIDecoder
#define _Included_org_atalk_impl_neomedia_codec_audio_g729_JNIDecoder
#ifdef __cplusplus
extern "C" {
#endif

/*
 * Class:     org_atalk_impl_neomedia_codec_audio_g729_JNIDecoder
 * Method:    g729_decoder_open
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_atalk_impl_neomedia_codec_audio_g729_G729_g729_1decoder_1open
  (JNIEnv *, jclass);

/*
 * Class:     org_atalk_impl_neomedia_codec_audio_g729_JNIDecoder
 * Method:    g729_decoder_close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_atalk_impl_neomedia_codec_audio_g729_G729_g729_1decoder_1close
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_atalk_impl_neomedia_codec_audio_g729_JNIDecoder
 * Method:    g729_decoder_process
 * Signature: (J[BI[BII)V
 */
JNIEXPORT void JNICALL Java_org_atalk_impl_neomedia_codec_audio_g729_G729_g729_1decoder_1process
  (JNIEnv *, jclass, jlong, jbyteArray, jint, jint, jint, jint, jshortArray);

#ifdef __cplusplus
}
#endif
#endif
