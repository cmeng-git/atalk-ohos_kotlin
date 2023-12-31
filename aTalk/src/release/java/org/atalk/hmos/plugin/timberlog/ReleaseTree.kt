package org.atalk.hmos.plugin.timberlog

import android.util.Log
import timber.log.Timber

/**
 * Release tree to log only WARN, ERROR and WTF.
 * Do not log if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == TimberLevel.FINE);
 * Log Log.INFO only if enabled for released apk
 */
open class ReleaseTree : Timber.DebugTree() {
    protected override fun isLoggable(tag: String?, priority: Int): Boolean {
        // return (priority < TimberLog.FINE && priority > Log.DEBUG) && (priority != Log.INFO || TimberLog.isInfoEnable);
        return priority == Log.WARN
                || priority == Log.ERROR
                || priority == Log.ASSERT
                || (priority == Log.INFO && TimberLog.isInfoEnable)
    }

    //    @Override
    //    protected void log(int priority, String tag, String message, Throwable throwable)
    //    {
    //        super.log(priority, tag, message, throwable);
    //
    //        if (priority >= Log.ERROR) {
    //            Crashlytics.log(priority, tag, message);
    //
    //            if (throwable != null) {
    //                Crashlytics.logException(throwable);
    //            }
    //        }
    //    }
}