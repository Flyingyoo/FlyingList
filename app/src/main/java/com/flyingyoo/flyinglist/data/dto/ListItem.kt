package com.flyingyoo.flyinglist.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flyingyoo.flyinglist.constant.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class ListItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.ID)
    var id: Int? = 0,

    @ColumnInfo(name = Constants.COMPLETED)
    var completed: Boolean? = false,

    @ColumnInfo(name = Constants.CONTENTS)
    var contents: String? = "",

    @ColumnInfo(name = Constants.VIEW_COUNT)
    var viewCount: Int? = 0,

    @ColumnInfo(name = Constants.CREATE_TIME)
    var createTime: Long? = 0L,

    @ColumnInfo(name = Constants.EDITED_TIME)
    var editedTime: Long? = 0L
)