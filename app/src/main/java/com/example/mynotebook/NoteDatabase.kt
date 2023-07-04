package com.example.mynotebook

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynotebook.Dao.NotesDao
import com.example.mynotebook.entity.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun myNotesDao():NotesDao

    companion object{
        @Volatile
        var INSTANCE: NoteDatabase?= null
        fun getDatabaseInstance(context: Context):NoteDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
               return tempInstance
            }
            synchronized(this){
                val roomDatabaseInstance = Room.databaseBuilder(context,NoteDatabase::class.java,"Notes").allowMainThreadQueries().build()
                INSTANCE = roomDatabaseInstance
                return roomDatabaseInstance
            }
        }
    }
}