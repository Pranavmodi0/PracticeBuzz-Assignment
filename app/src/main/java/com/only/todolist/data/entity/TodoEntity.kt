package com.only.todolist.data.entity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = tableName)

@RequiresApi(Build.VERSION_CODES.O)
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    @SerialName("id")
    val id: Int = 0,

    @SerialName("title")
    val title: String = "",

    @SerialName("description")
    val description: String = "",

    )

const val tableName = "TodoEntity"
