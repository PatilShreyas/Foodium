package dev.shreyaspatil.foodium.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.shreyaspatil.foodium.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(posts: List<Post>)

    @Query("DELETE FROM ${Post.TABLE_NAME}")
    fun deleteAllPosts()

    @Query("SELECT * FROM ${Post.TABLE_NAME}")
    fun getAllPosts(): Flow<List<Post>>
}