package com.rmaproject.myqoran.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rmaproject.myqoran.data.local.entities.Bookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("DELETE FROM bookmark")
    suspend fun deleteAllBookmark()

    @Insert
    suspend fun insertBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query("SELECT * FROM bookmark ORDER BY timeAdded ASC")
    fun getBookmarks(): Flow<List<Bookmark>>
}