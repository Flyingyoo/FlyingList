package com.flyingyoo.flyinglist.data.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.flyingyoo.flyinglist.constant.Constants
import com.flyingyoo.flyinglist.data.dto.ListItem

@Dao
interface ItemDao {

    @Query("select * from ${Constants.TABLE_NAME}")
    fun getAll(): MutableList<ListItem>

    @Query("select * from ${Constants.TABLE_NAME} where ${Constants.ID} = :id")
    fun getItem(id: Int) : ListItem

    @Query("select * from ${Constants.TABLE_NAME} where ${Constants.COMPLETED} = :completed")
    fun getItemByCompleted(completed: Boolean)

    @Insert(onConflict = REPLACE)
    fun insert(item: ListItem)

    @Update(onConflict = REPLACE)
    fun update(item: ListItem)

    @Delete
    fun delete(item : ListItem)



//    @Query("delete from $TABLE_NAME where $COMPLETED")
//    fun deleteCompleted()

//    @Query("delete from ${Constants.TABLE_NAME}")
//    fun delete(id: Int)
}