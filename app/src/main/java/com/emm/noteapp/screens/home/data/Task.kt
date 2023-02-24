package com.emm.noteapp.screens.home.data


data class Task(
    val id: String,
    val title: String,
    val description: String,
    val type: Int,
    val taskCreateDate: Long,
    val taskUpdateDate: Long,
    val isNew: Boolean = false,
    val timeAgo: String = "",
    val dateInString: String = ""
)