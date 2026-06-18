package com.blank.fakeapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blank.fakeapp.data.local.dao.FavoriteDao
import com.blank.fakeapp.data.local.entity.FavoriteProductEntity

@Database(
    entities = [FavoriteProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FakeAppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
