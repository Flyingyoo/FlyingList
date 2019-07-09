package com.flyingyoo.flyinglist.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flyingyoo.flyinglist.constant.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class ListItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0
    , var completed: Boolean? = false
    , var contents: String? = ""
    , var viewCount: Int? = 0
    , var createTime: Long? = 0L
    , var editedTime: Long? = 0L
)