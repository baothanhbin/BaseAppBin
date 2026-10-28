package com.example.basekotlin.room.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.basekotlin.room.database.model.VideoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(video: VideoEntity): Long

    @Query("SELECT * FROM videos WHERE id = :id")
    fun observeById(id: Long): Flow<VideoEntity?>

    @Query("SELECT * FROM videos ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<VideoEntity>>

    @Query("SELECT * FROM videos ORDER BY createdAt DESC")
    suspend fun getAll(): List<VideoEntity>

    @Query("SELECT * FROM videos WHERE downloadManagerId = :downloadManagerId LIMIT 1")
    suspend fun getByDownloadManagerId(downloadManagerId: Long): VideoEntity?

    @Query("SELECT * FROM videos WHERE mediaStoreId = :mediaStoreId LIMIT 1")
    suspend fun getByMediaStoreId(mediaStoreId: Long): VideoEntity?

    @Query("SELECT * FROM videos WHERE fileUri = :fileUri LIMIT 1")
    suspend fun getByFileUri(fileUri: String): VideoEntity?

    @Query("SELECT * FROM videos WHERE title = :title LIMIT 1")
    suspend fun getByTitle(title: String): VideoEntity?

    @Query(
        """
        SELECT * FROM videos
        WHERE (pasteUrl = :url OR originalUrl = :url)
            AND status = :completedStatus
        ORDER BY updatedAt DESC
        LIMIT 1
        """
    )
    suspend fun getReusableByUrl(
        url: String,
        completedStatus: String,
    ): VideoEntity?

    @Query(
        """
        UPDATE videos
        SET downloadManagerId = :downloadManagerId,
            updatedAt = :updatedAt
        WHERE id = :id
        """
    )
    suspend fun updateDownloadManagerId(
        id: Long,
        downloadManagerId: Long,
        updatedAt: Long = System.currentTimeMillis(),
    )

    @Query(
        """
        UPDATE videos
        SET status = :status,
            progress = :progress,
            mediaStoreId = :mediaStoreId,
            fileUri = :fileUri,
            errorMessage = :errorMessage,
            updatedAt = :updatedAt
        WHERE id = :id
        """
    )
    suspend fun updateState(
        id: Long,
        status: String,
        progress: Int,
        mediaStoreId: Long?,
        fileUri: String?,
        errorMessage: String?,
        updatedAt: Long = System.currentTimeMillis(),
    )

    @Query(
        """
        UPDATE videos
        SET thumbnail = :thumbnail,
            updatedAt = :updatedAt
        WHERE id = :id
        """
    )
    suspend fun updateThumbnail(
        id: Long,
        thumbnail: String,
        updatedAt: Long = System.currentTimeMillis(),
    )

    @Query(
        """
        UPDATE videos
        SET duration = :duration,
            updatedAt = :updatedAt
        WHERE id = :id
        """
    )
    suspend fun updateDuration(
        id: Long,
        duration: Int,
        updatedAt: Long = System.currentTimeMillis(),
    )

    @Query(
        """
        UPDATE videos
        SET mediaStoreId = :mediaStoreId,
            fileUri = :fileUri,
            updatedAt = :updatedAt
        WHERE id = :id
        """
    )
    suspend fun updateMediaStoreIdentity(
        id: Long,
        mediaStoreId: Long?,
        fileUri: String?,
        updatedAt: Long = System.currentTimeMillis(),
    ): Int

    @Query(
        """
        UPDATE videos
        SET title = :title,
            fileUri = :fileUri,
            updatedAt = :updatedAt
        WHERE id = :id
        """
    )
    suspend fun renameVideo(
        id: Long,
        title: String,
        fileUri: String?,
        updatedAt: Long = System.currentTimeMillis(),
    ): Int

    @Query(
        """
        UPDATE videos
        SET title = :title,
            fileUri = :newFileUri,
            updatedAt = :updatedAt
        WHERE fileUri = :oldFileUri
        """
    )
    suspend fun renameVideoByFileUri(
        oldFileUri: String,
        title: String,
        newFileUri: String?,
        updatedAt: Long = System.currentTimeMillis(),
    ): Int

    @Query(
        """
        UPDATE videos
        SET title = :title,
            fileUri = :newFileUri,
            updatedAt = :updatedAt
        WHERE mediaStoreId = :mediaStoreId
        """
    )
    suspend fun renameVideoByMediaStoreId(
        mediaStoreId: Long,
        title: String,
        newFileUri: String?,
        updatedAt: Long = System.currentTimeMillis(),
    ): Int

    @Query("DELETE FROM videos WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Query("DELETE FROM videos WHERE fileUri = :fileUri")
    suspend fun deleteByFileUri(fileUri: String): Int

    @Query("DELETE FROM videos WHERE mediaStoreId = :mediaStoreId")
    suspend fun deleteByMediaStoreId(mediaStoreId: Long): Int

    @Query("DELETE FROM videos WHERE title = :title")
    suspend fun deleteByTitle(title: String): Int
}
