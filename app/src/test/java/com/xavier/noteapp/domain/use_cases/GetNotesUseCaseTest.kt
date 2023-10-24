package com.xavier.noteapp.domain.use_cases

import com.google.common.truth.Truth.assertThat
import com.xavier.noteapp.data.repository.FakeNoteRepository
import com.xavier.noteapp.domain.model.Note
import com.xavier.noteapp.domain.utils.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.ZonedDateTime


class GetNotesUseCaseTest {

    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNotesUseCase = GetNotesUseCase(fakeNoteRepository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    text = c.toString(),
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
    fun `Order notes by time created descending order`() = runBlocking {
        val notes = getNotesUseCase(orderType = OrderType.Descending).first()
        for (i in 0 until notes.size - 1) {
            assertThat(notes[i].createdAt.isBefore(notes[i + 1].createdAt))
        }
    }

    @Test
    fun `Order notes by time created ascending order`() = runBlocking {
        val notes = getNotesUseCase(orderType = OrderType.Ascending).first()
        for (i in 0 until notes.size - 1) {
            assertThat(notes[i].createdAt.isAfter(notes[i + 1].createdAt))
        }
    }
}


