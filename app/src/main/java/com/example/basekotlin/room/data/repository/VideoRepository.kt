package com.example.basekotlin.room.data.repository

import com.example.basekotlin.room.database.model.VideoEntity
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    suspend fun createVideo(video: VideoEntity): Long

    fun observeVideo(id: Long): Flow<VideoEntity?>

    fun observeVideos(): Flow<List<VideoEntity>>

    suspend fun getVideos(): List<VideoEntity>

    suspend fun getByDownloadManagerId(downloadManagerId: Long): VideoEntity?

    suspend fun getByMediaStoreId(mediaStoreId: Long): VideoEntity?

    suspend fun getByFileUri(fileUri: String): VideoEntity?

    suspend fun getByTitle(title: String): VideoEntity?

    suspend fun getReusableByUrl(url: String): VideoEntity?

    suspend fun updateDownloadManagerId(id: Long, downloadManagerId: Long)

    suspend fun updateState(
        id: Long,
        status: String,
        progress: Int,
        mediaStoreId: Long? = null,
        fileUri: String? = null,
        errorMessage: String? = null,
    )

    suspend fun updateThumbnail(
        id: Long,
        thumbnail: String,
    )

    suspend fun updateDuration(
        id: Long,
        duration: Int,
    )

    suspend fun updateMediaStoreIdentity(
        id: Long,
        mediaStoreId: Long?,
        fileUri: String?,
    ): Boolean

    suspend fun renameVideo(
        id: Long,
        title: String,
        fileUri: String?,
    ): Boolean

    suspend fun renameVideoByFileUri(
        oldFileUri: String,
        title: String,
        newFileUri: String?,
    ): Boolean

    suspend fun renameVideoByMediaStoreId(
        mediaStoreId: Long,
        title: String,
        newFileUri: String?,
    ): Boolean

    suspend fun deleteVideo(id: Long): Boolean

    suspend fun deleteVideoByFileUri(fileUri: String): Boolean

    suspend fun deleteVideoByMediaStoreId(mediaStoreId: Long): Boolean

    suspend fun deleteVideoByTitle(title: String): Boolean
}
