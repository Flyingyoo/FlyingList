package com.flyingyoo.flyinglist.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.flyingyoo.flyinglist.constant.Constants
import com.flyingyoo.flyinglist.data.dao.ItemDao
import com.flyingyoo.flyinglist.data.dto.ListItem

@Database(entities = [ListItem::class], version = 1)
abstract class ItemDataBase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        private var INSTANCE: ItemDataBase? = null
        fun getInstance(context: Context): ItemDataBase? {
            if (INSTANCE == null) {
                synchronized(ItemDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ItemDataBase::class.java, Constants.DB_NAME
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