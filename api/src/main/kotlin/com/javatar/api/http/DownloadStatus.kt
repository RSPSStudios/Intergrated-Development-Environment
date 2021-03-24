package com.javatar.api.http

sealed class DownloadStatus {

    object Success : DownloadStatus()

    data class Error(val message: String) : DownloadStatus()

    data class Progress(val progress: Double) : DownloadStatus()

}
