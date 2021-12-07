package com.shyptsolution.noteapp

import android.content.Context
import androidx.room.*

@Database(
    entities = [RoomEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class NoteDatabase :RoomDatabase(){
    abstract fun getNoteDao():NoteDao


    companion object {
        @Volatile private var instance: NoteDatabase?=null
        private val LOCK=Any()

        operator fun invoke(context: Context)= instance?: synchronized(LOCK){
            instance?: buildDatabase(context).also{

            }

        }
        private fun buildDatabase(context: Context)=Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "noteDatabase"
        ).build()


    }



}