package com.javatar.api.fs.extensions

import com.javatar.api.fs.FileType
import org.pf4j.ExtensionPoint

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

interface FileTypeExtension : ExtensionPoint {

    fun createFileType(): FileType

}
