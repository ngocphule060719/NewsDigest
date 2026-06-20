package com.app.newsdigest.repo

import androidx.room.Room
import com.app.newsdigest.data.local.db.AppDatabase
import org.robolectric.RuntimeEnvironment

object InMemoryDatabaseHelper {
    fun create(): AppDatabase =
        Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.getApplication(),
            AppDatabase::class.java,
        )
            .allowMainThreadQueries()
            .build()
}
