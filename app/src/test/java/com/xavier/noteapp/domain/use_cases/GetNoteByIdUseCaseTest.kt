package com.xavier.noteapp.domain.use_cases

import com.xavier.noteapp.data.repository.FakeNoteRepository
import com.xavier.noteapp.domain.model.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.ZonedDateTime

class GetNoteByIdUseCaseTest {
    private lateinit var getNoteByIdUseCase: GetNoteByIdUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var sampleNote: Note

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNoteByIdUseCase = GetNoteByIdUseCase(fakeNoteRepository)

        sampleNote = Note(
            id = 3,
            text = "Sample Note",
            createdAt = ZonedDateTime.now(),
            title = "Sample Title"
        )

        runBlocking {
            fakeNoteRepository.upsertNote(sampleNote)
        }
    }

    @Test
    fun `Get note by id`() = runBlocking {
        val retrievedNote = getNoteByIdUseCase(id = 3).first()
        assertEquals(sampleNote, retrievedNote)
    }
}