package dev.shreyaspatil.foodium.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.shreyaspatil.foodium.model.Post
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PostsDaoTest {

    private lateinit var mDatabase: FoodiumPostsDatabase

    @Before
    fun init() {
        mDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FoodiumPostsDatabase::class.java
        ).build()
    }

    @Test
    @Throws(InterruptedException::class)
    fun insert_and_select_posts() = runBlocking {
        val posts = listOf(
            Post(1, "Test 1", "Test 1", "Test 1"),
            Post(2, "Test 2", "Test 2", "Test 3")
        )

        mDatabase.getPostsDao().insertPosts(posts)

        val dbPosts = mDatabase.getPostsDao().getAllPosts()

        assertThat(dbPosts.toList().size, equalTo(posts.size))
        assertThat(dbPosts.toList(), equalTo(posts))
    }

    @After
    fun cleanup() {
        mDatabase.close()
    }
}
