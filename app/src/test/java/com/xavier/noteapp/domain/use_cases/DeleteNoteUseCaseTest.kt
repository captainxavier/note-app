package com.xavier.noteapp.domain.use_cases

import com.xavier.noteapp.data.repository.FakeNoteRepository
import com.xavier.noteapp.domain.model.Note
import com.xavier.noteapp.domain.utils.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.ZonedDateTime

class DeleteNoteUseCaseTest {
    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNotesUseCase = GetNotesUseCase(fakeNoteRepository)
        deleteNoteUseCase = DeleteNoteUseCase(fakeNoteRepository)

        // Sequentially insert notes
        val notesToInsert = mutableListOf<Note>()
        ('a'..'c').forEachIndexed { index, c ->
            val note = Note(
                title = c.toString(),
                text = c.toString(),
                createdAt = ZonedDateTime.now()
            )
            runBlocking {
                fakeNoteRepository.upsertNote(note)
            }
        }
    }

    @Test
    fun `Test note Deletion`() = runBlocking {
        // Retrieve the list of notes from the repository
        val notes = getNotesUseCase(orderType = OrderType.Ascending).first().toSet()

        // Randomly pick a note from the list
        val retrievedNote = notes.toList()[1]

        // Confirm that the randomly picked note is initially in the list
        assertTrue(notes.contains(retrievedNote))

        // Delete the randomly picked note
        deleteNoteUseCase.invoke(retrievedNote)

        // Get a fresh list of notes from the repository
        val updatedNotes = getNotesUseCase().first()

        // Confirm that the randomly picked note is no longer in the updated list
        assertFalse(updatedNotes.contains(retrievedNote))
    }
}


