package com.javatar.api.fs

import com.javatar.api.fs.directories.RootDirectory
import javafx.scene.image.ImageView

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

interface FileType {

    val indexId: Int
    val archiveId: Int

    fun open(file: JFile, root: RootDirectory): String
    fun save(data: String, file: JFile, root: RootDirectory)
    fun identifier(file: JFile, root: RootDirectory): String

    fun cache(jfiles: List<JFile>, root: RootDirectory)

    fun icon(file: JFile, root: RootDirectory): ImageView?

}
