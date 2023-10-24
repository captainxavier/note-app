package com.xavier.noteapp.domain.use_cases

import com.xavier.noteapp.data.repository.FakeNoteRepository
import com.xavier.noteapp.domain.model.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.ZonedDateTime

class SearchNoteUseCaseTest{

    private lateinit var searchNoteUseCase: SearchNoteUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository
    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        searchNoteUseCase = SearchNoteUseCase(fakeNoteRepository)

        val notesToInsert = mutableListOf<Note>()
        (1..10).forEach { index ->
            notesToInsert.add(
                Note(
                    title = "Title $index",
                    text = "Text $index",
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
    fun `Search notes`() = runBlocking {

        val query = "Title 2" // Query to search for notes
        val searchResult = searchNoteUseCase(query).first().toSet()

        val expectedResult = listOf(
            Note(
                title = "Title 2",
                text = "Text 2",
                createdAt = ZonedDateTime.now()
            )
        )

        assertEquals(expectedResult.map { it.title }, searchResult.toList().map { it.title })

    }
}