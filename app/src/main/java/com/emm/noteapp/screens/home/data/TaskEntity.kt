package com.emm.noteapp.screens.home.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val type: Int,
    val taskCreateDate: Long,
    val taskUpdateDate: Long
)