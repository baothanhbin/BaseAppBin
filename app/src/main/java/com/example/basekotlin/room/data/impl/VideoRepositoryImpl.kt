package com.example.basekotlin.room.data.impl

import com.example.basekotlin.room.data.repository.VideoRepository
import com.example.basekotlin.room.database.dao.VideoDao
import com.example.basekotlin.room.database.model.DownloadStatus
import com.example.basekotlin.room.database.model.VideoEntity
import kotlinx.coroutines.flow.Flow

class VideoRepositoryImpl(
    private val dao: VideoDao,
) : VideoRepository {

    override suspend fun createVideo(video: VideoEntity): Long {
        return dao.insert(video)
    }

    override fun observeVideo(id: Long): Flow<VideoEntity?> {
        return dao.observeById(id)
    }

    override fun observeVideos(): Flow<List<VideoEntity>> {
        return dao.observeAll()
    }

    override suspend fun getVideos(): List<VideoEntity> {
        return dao.getAll()
    }

    override suspend fun getByDownloadManagerId(downloadManagerId: Long): VideoEntity? {
        return dao.getByDownloadManagerId(downloadManagerId)
    }

    override suspend fun getByMediaStoreId(mediaStoreId: Long): VideoEntity? {
        return dao.getByMediaStoreId(mediaStoreId)
    }

    override suspend fun getByFileUri(fileUri: String): VideoEntity? {
        return dao.getByFileUri(fileUri)
    }

    override suspend fun getByTitle(title: String): VideoEntity? {
        return dao.getByTitle(title)
    }

    override suspend fun getReusableByUrl(url: String): VideoEntity? {
        return dao.getReusableByUrl(
            url = url,
            completedStatus = DownloadStatus.COMPLETED.name,
        )
    }

    override suspend fun updateDownloadManagerId(id: Long, downloadManagerId: Long) {
        dao.updateDownloadManagerId(id, downloadManagerId)
    }

    override suspend fun updateState(
        id: Long,
        status: String,
        progress: Int,
        mediaStoreId: Long?,
        fileUri: String?,
        errorMessage: String?,
    ) {
        dao.updateState(
            id = id,
            status = status,
            progress = progress,
            mediaStoreId = mediaStoreId,
            fileUri = fileUri,
            errorMessage = errorMessage,
        )
    }

    override suspend fun updateThumbnail(
        id: Long,
        thumbnail: String,
    ) {
        dao.updateThumbnail(
            id = id,
            thumbnail = thumbnail,
        )
    }

    override suspend fun updateDuration(
        id: Long,
        duration: Int,
    ) {
        dao.updateDuration(
            id = id,
            duration = duration,
        )
    }

    override suspend fun updateMediaStoreIdentity(
        id: Long,
        mediaStoreId: Long?,
        fileUri: String?,
    ): Boolean {
        return dao.updateMediaStoreIdentity(
            id = id,
            mediaStoreId = mediaStoreId,
            fileUri = fileUri,
        ) > 0
    }

    override suspend fun renameVideo(
        id: Long,
        title: String,
        fileUri: String?,
    ): Boolean {
        return dao.renameVideo(
            id = id,
            title = title,
            fileUri = fileUri,
        ) > 0
    }

    override suspend fun renameVideoByFileUri(
        oldFileUri: String,
        title: String,
        newFileUri: String?,
    ): Boolean {
        return dao.renameVideoByFileUri(
            oldFileUri = oldFileUri,
            title = title,
            newFileUri = newFileUri,
        ) > 0
    }

    override suspend fun renameVideoByMediaStoreId(
        mediaStoreId: Long,
        title: String,
        newFileUri: String?,
    ): Boolean {
        return dao.renameVideoByMediaStoreId(
            mediaStoreId = mediaStoreId,
            title = title,
            newFileUri = newFileUri,
        ) > 0
    }

    override suspend fun deleteVideo(id: Long): Boolean {
        return dao.deleteById(id) > 0
    }

    override suspend fun deleteVideoByFileUri(fileUri: String): Boolean {
        return dao.deleteByFileUri(fileUri) > 0
    }

    override suspend fun deleteVideoByMediaStoreId(mediaStoreId: Long): Boolean {
        return dao.deleteByMediaStoreId(mediaStoreId) > 0
    }

    override suspend fun deleteVideoByTitle(title: String): Boolean {
        return dao.deleteByTitle(title) > 0
    }
}
