package dev.shreyaspatil.foodium.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.shreyaspatil.foodium.model.Post

@Database(
    entities = [Post::class],
    version = FoodiumPostsDatabase.DB_VERSION
)
abstract class FoodiumPostsDatabase : RoomDatabase() {

    abstract fun getPostsDao(): PostsDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "foodium_database"

        @Volatile
        private var INSTANCE: FoodiumPostsDatabase? = null

        fun getInstance(context: Context): FoodiumPostsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodiumPostsDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }

    }
}