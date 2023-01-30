package com.rmaproject.myqoran.data.local

import androidx.room.*
import com.rmaproject.myqoran.data.local.entities.Bookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("DELETE FROM bookmark")
    suspend fun deleteAllBookmark()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query("SELECT * FROM bookmark ORDER BY timeAdded ASC")
    fun getBookmarks(): Flow<List<Bookmark>>
}