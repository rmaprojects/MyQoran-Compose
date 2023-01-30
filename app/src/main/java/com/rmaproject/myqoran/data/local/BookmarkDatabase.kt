package com.rmaproject.myqoran.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rmaproject.myqoran.data.local.entities.Bookmark

@Database(
    entities = [Bookmark::class],
    version = 1,
    exportSchema = false
)
abstract class BookmarkDatabase: RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        const val DATABASE_NAME = "bookmark.db"
    }
}