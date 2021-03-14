package com.javatar.api.fs

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

interface IFileTypeManager {

    val fileTypes: MutableMap<String, FileType>
    val archiveTypes: MutableMap<String, ArchiveType>

    fun registerFileType(type: FileType)
    fun registerArchiveType(type: ArchiveType)

    fun getFileType(indexId: Int, archiveId: Int): FileType?
    fun getArchiveType(indexId: Int, archiveId: Int): ArchiveType?

}
