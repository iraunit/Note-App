package com.shyptsolution.noteapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomEntity(
    @PrimaryKey val id:Int,
    val noteName:String,
    val noteDes:String
)