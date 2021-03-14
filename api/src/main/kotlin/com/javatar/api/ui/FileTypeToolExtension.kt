package com.javatar.api.ui

import org.pf4j.ExtensionPoint
import tornadofx.Fragment

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

interface FileTypeToolExtension : ExtensionPoint {

    fun createToolModal(): Fragment

}
