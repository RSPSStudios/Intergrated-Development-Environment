package com.javatar.api.ui

import org.pf4j.ExtensionPoint
import tornadofx.Fragment

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 16 2021
 */

interface PreferencesExtension : ExtensionPoint {

    fun createPreferencesUI(): Fragment

    fun name(): String

}
