package com.javatar.api.fs

import com.javatar.api.fs.directories.RootDirectory
import javafx.scene.image.ImageView

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 14 2021
 */

interface ArchiveType : FileType {

    fun icon(archive: JDirectory<JFile>, root: RootDirectory): ImageView? {
        return null
    }

}
