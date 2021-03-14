package com.javatar.api.fs

import com.javatar.api.fs.directories.RootDirectory
import javafx.scene.image.ImageView

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

interface IndexType {
    val indexId: Int
    fun identifier(root: RootDirectory): String
    fun icon(root: RootDirectory): ImageView?
}
