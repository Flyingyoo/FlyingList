package com.flyingyoo.flyinglist.data.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.flyingyoo.flyinglist.constant.Constants
import com.flyingyoo.flyinglist.data.dto.ListItem

@Dao
interface ItemDao {
    @Insert(onConflict = REPLACE)
    fun insert(item: ListItem)

    @Update(onConflict = REPLACE)
    fun update(item: ListItem)

    @Delete
    fun delete(item: ListItem)

    @Query("select * from ${Constants.TABLE_NAME}")
    fun getAll(): MutableList<ListItem>

    @Query("select * from ${Constants.TABLE_NAME} where ${Constants.ID} = :id")
    fun getItem(id: Int): ListItem

    @Query("select count(*) from ${Constants.TABLE_NAME} where not ${Constants.COMPLETED}")
    fun getActiveCount(): Int

    @Query("select * from ${Constants.TABLE_NAME} where ${Constants.COMPLETED} = :completed")
    fun getItemByCompleted(completed: Boolean): MutableList<ListItem>

    @Query("delete from ${Constants.TABLE_NAME} where ${Constants.COMPLETED}")
    fun deleteCompleted()

    @Query("update ${Constants.TABLE_NAME} set ${Constants.VIEW_COUNT} = ${Constants.VIEW_COUNT} + 1 where ${Constants.ID} = :id")
    fun increaseViewCount(id: Int)
}