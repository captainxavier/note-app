package com.xavier.noteapp.data.database.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.xavier.noteapp.data.database.Converters
import com.xavier.noteapp.data.database.dao.NotesDao
import com.xavier.noteapp.data.entities.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2,)
    ]
)
@TypeConverters(Converters::class)
abstract class NoteDataBase:RoomDatabase() {
    abstract fun getNoteDao():NotesDao
}