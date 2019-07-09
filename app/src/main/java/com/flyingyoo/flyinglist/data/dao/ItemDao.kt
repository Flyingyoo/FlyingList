package com.flyingyoo.flyinglist.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.flyingyoo.flyinglist.constant.Constants
import com.flyingyoo.flyinglist.data.dto.ListItem

@Dao
interface ItemDao {

    companion object {
        const val COMPLETED = "completed"
    }

    @Query("select * from ${Constants.TABLE_NAME}")
    fun getAll(): MutableList<ListItem>

    @Insert(onConflict = REPLACE)
    fun insert(item: ListItem)

    //fun update(item: ListItem) {}

//    @Query("delete from $TABLE_NAME where $COMPLETED")
//    fun deleteCompleted()

//    @Query("delete from ${Constants.TABLE_NAME}")
//    fun delete(id: Int)
}