package com.xavier.noteapp.domain.use_cases

import com.xavier.noteapp.data.repository.FakeNoteRepository
import com.xavier.noteapp.domain.model.Note
import com.xavier.noteapp.domain.utils.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.ZonedDateTime


class CreateNoteUseCaseTest {

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var createNoteUseCase: CreateNoteUseCase
    private lateinit var getNotesUseCase: GetNotesUseCase


    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        createNoteUseCase = CreateNoteUseCase(fakeNoteRepository)
        getNotesUseCase = GetNotesUseCase(fakeNoteRepository)

        val notesToInsert = mutableListOf<Note>()
        (1..4).forEach { index ->
            notesToInsert.add(
                Note(
                    id = index,
                    title = "Sample Title $index",
                    text = "Sample Text $index",
                    createdAt = ZonedDateTime.now()
                )
            )
            notesToInsert.shuffle()
            runBlocking {
                notesToInsert.forEach { fakeNoteRepository.upsertNote(it) }
            }
        }
    }

    @Test
    fun `Test Insert of Note`() = runBlocking {
        // Create a new note
        val newNote = Note(
            id = 10,
            title = "Sample Title 10",
            text = "Sample Title 10",
            createdAt = ZonedDateTime.now()
        )
        createNoteUseCase(note = newNote)

        // Use the InsertNoteUseCase to insert the note
//        createNoteUseCase(newNote).first()
        // Retrieve the list of notes from the repository
        val notes = getNotesUseCase(orderType = OrderType.Ascending).first().toSet()
        println("List of Notes: $notes")

        // Check that the inserted note is in the list
        assertTrue(notes.contains(newNote))
    }
}