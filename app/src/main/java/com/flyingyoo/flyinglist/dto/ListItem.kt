package com.flyingyoo.flyinglist.dto

import androidx.room.PrimaryKey

data class ListItem(
    @PrimaryKey
    var id: String? = ""
    , var completed : Boolean? = false
    , var contents: String? = ""
    , var viewCount: Int? = 0
    , var createTime: Long? = 0L
    , var editedTime: Long? = 0L
)