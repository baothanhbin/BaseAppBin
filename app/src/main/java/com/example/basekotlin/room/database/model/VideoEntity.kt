package com.example.basekotlin.room.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pasteUrl: String? = null,
    val originalUrl: String,
    val title: String,
    val thumbnail: String,
    val source: String,
    val duration: Int,
    val status: String = DownloadStatus.WAITING.name,
    val progress: Int = 0,
    val downloadManagerId: Long? = null,
    val mediaStoreId: Long? = null,
    val fileUri: String? = null,
    val errorMessage: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)

enum class DownloadStatus {
    WAITING,
    DOWNLOADING,
    COMPLETED,
    FAILED,
}
