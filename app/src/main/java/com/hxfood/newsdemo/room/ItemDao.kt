package com.hxfood.newsdemo.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ItemDao {

    @Query("SELECT * FROM item")
    fun getAllItem(): List<Item>

    @Query("DELETE FROM item WHERE title = :title")
    fun deleteByTitle(title: String)

    @Insert
    fun insert(item: Item)
}