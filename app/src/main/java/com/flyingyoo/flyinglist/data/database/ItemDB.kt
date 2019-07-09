package com.flyingyoo.flyinglist.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.flyingyoo.flyinglist.constant.Constants.Companion.DB_NAME
import com.flyingyoo.flyinglist.data.dao.ItemDao
import com.flyingyoo.flyinglist.data.dto.ListItem

@Database(entities = [ListItem::class], version = 1)
abstract class ItemDB : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {


        private var INSTANCE: ItemDB? = null
        fun getInstance(context: Context): ItemDB? {
            if (INSTANCE == null) {
                synchronized(ItemDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ItemDB::class.java, DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}