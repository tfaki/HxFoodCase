package com.hxfood.newsdemo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], exportSchema = false, version=1)
abstract class DatabaseManager : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        private var INSTANCE: DatabaseManager? = null

        fun getInstance(context: Context): DatabaseManager? {
            if (INSTANCE == null) {
                synchronized(DatabaseManager::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DatabaseManager::class.java, "applicationDatabase.db")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}