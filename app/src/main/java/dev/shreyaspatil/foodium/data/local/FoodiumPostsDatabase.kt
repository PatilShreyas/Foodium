package dev.shreyaspatil.foodium.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.shreyaspatil.foodium.data.local.dao.PostsDao
import dev.shreyaspatil.foodium.model.Post

/**
 * Abstract Foodium database.
 * It provides DAO [PostsDao] by using method [getPostsDao].
 */
@Database(
    entities = [Post::class],
    version = DatabaseMigrations.DB_VERSION
)
abstract class FoodiumPostsDatabase : RoomDatabase() {

    /**
     * @return [PostsDao] Foodium Posts Data Access Object.
     */
    abstract fun getPostsDao(): PostsDao

    companion object {
        const val DB_NAME = "foodium_database"

        @Volatile
        private var INSTANCE: FoodiumPostsDatabase? = null

        fun getInstance(context: Context): FoodiumPostsDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        FoodiumPostsDatabase::class.java,
                        DB_NAME
                    )
                    .addMigrations(*DatabaseMigrations.MIGRATIONS)
                    .build()

                INSTANCE = instance
                return instance
            }
        }

    }
}