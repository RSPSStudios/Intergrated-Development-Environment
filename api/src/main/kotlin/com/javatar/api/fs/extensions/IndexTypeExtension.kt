package com.javatar.api.fs.extensions

import com.javatar.api.fs.IndexType
import org.pf4j.ExtensionPoint

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

interface IndexTypeExtension : ExtensionPoint {

    fun createIndexType(): IndexType

}
