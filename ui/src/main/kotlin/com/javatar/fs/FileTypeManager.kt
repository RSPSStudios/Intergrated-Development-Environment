package com.javatar.fs

import com.javatar.api.fs.ArchiveType
import com.javatar.api.fs.FileType
import com.javatar.api.fs.IFileTypeManager
import com.javatar.api.fs.IndexType

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @created March 13 2021
 */

class FileTypeManager : IFileTypeManager {

    override val fileTypes: MutableMap<String, FileType> = mutableMapOf()
    override val archiveTypes: MutableMap<String, ArchiveType> = mutableMapOf()
    override val indexTypes: MutableMap<Int, IndexType> = mutableMapOf()

    private val singleFileArchiveType = mutableMapOf<Int, ArchiveType>()

    override fun clear() {
        fileTypes.clear()
        archiveTypes.clear()
        indexTypes.clear()
    }

    override fun registerFileType(type: FileType) {
        val extId = "${type.indexId}:${type.archiveId}"
        if (fileTypes.containsKey(extId))
            error("File type already registered. $extId")

        fileTypes[extId] = type
    }

    override fun registerArchiveType(type: ArchiveType) {
        val extId = "${type.indexId}:${type.archiveId}"
        if (archiveTypes.containsKey(extId))
            error("Archive type already registered.")
        if (type.indexId > 0 && type.archiveId == -1) {
            singleFileArchiveType[type.indexId] = type
        } else {
            archiveTypes[extId] = type
        }
    }

    override fun registerIndexType(type: IndexType) {
        if (indexTypes.containsKey(type.indexId)) {
            error("Index Type already registered.")
        } else {
            indexTypes[type.indexId] = type
        }
    }

    override fun getFileType(indexId: Int, archiveId: Int): FileType? {
        return fileTypes["$indexId:$archiveId"]
    }

    override fun getArchiveType(indexId: Int, archiveId: Int): ArchiveType? {
        if (singleFileArchiveType.containsKey(indexId)) {
            return singleFileArchiveType[indexId]
        }
        return archiveTypes["$indexId:$archiveId"]
    }

    override fun getIndexType(indexId: Int): IndexType? {
        return indexTypes[indexId]
    }

}
