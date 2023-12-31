/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.atalk.impl.osgi.framework.launch

import org.osgi.framework.launch.Framework
import org.osgi.framework.launch.FrameworkFactory

/**
 *
 * @author Lyubomir Marinov
 * @author Eng Chong Meng
 */
class FrameworkFactoryImpl : FrameworkFactory {
    override fun newFramework(configuration: Map<String, String>): Framework {
        return FrameworkImpl(configuration)
    }
}