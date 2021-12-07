package com.shyptsolution.noteapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.firebase.firestore.auth.User


@Dao
interface NoteDao  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note:RoomEntity)

    @Query("SELECT * FROM roomentity" )
    fun getAllNotes(): List<RoomEntity>

    @Insert
    fun addMultipleNotes(vararg note:RoomEntity)
}