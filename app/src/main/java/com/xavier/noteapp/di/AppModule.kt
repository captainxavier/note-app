package com.xavier.noteapp.di

import android.content.Context
import androidx.room.Room
import com.xavier.noteapp.data.database.dao.NotesDao
import com.xavier.noteapp.data.database.db.NoteDataBase
import com.xavier.noteapp.data.repository.NoteRepository
import com.xavier.noteapp.data.repository.NoteRepositoryImpl
import com.xavier.noteapp.domain.validators.NotesValidator
import com.xavier.noteapp.domain.validators.NotesValidatorImpl
import com.xavier.noteapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocalDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, NoteDataBase::class.java, Constants.LOCAL_DB
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    @Singleton
    fun provideNotesDao(db: NoteDataBase) = db.getNoteDao()

    @Provides
    @Singleton
    fun provideNoteRepository(dao: NotesDao): NoteRepository {
        return NoteRepositoryImpl(notesDao = dao)
    }

    @Provides
    @Singleton
    fun providesNotesValidator():NotesValidator{
        return NotesValidatorImpl()
    }
}