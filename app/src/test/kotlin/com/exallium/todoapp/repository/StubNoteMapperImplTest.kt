package com.exallium.todoapp.repository

import com.exallium.todoapp.entities.Note
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Unit Testing for {@link RepositoryImpl} to verify expected behaviour
 */
class StubNoteMapperImplTest {
    val NOTE_ID_NOT_FOUND = "UNKNOWN"

    lateinit var testSubject: StubNoteMapperImpl

    @Mock
    lateinit var idFactory: IdFactory

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(idFactory.createId()).thenReturn("1", "2", "3", "4", "5")
        testSubject = StubNoteMapperImpl(idFactory)
    }

    @Test
    fun getAllNotes_returns5PremadeNotes() {
        // WHEN
        val result = testSubject.query(Query.AllObjectsQuery())
                as QueryResponse.AllObjectsQueryResponse<Note>

        // THEN
        assertThat(result.items.size, `is`(5))
    }

    @Test
    fun getNoteById_whenIdExists_returnsNote() {
        // GIVEN
        val note = newNote()
        testSubject.save(note)

        // WHEN
        val result = testSubject.query(Query.SingleObjectByIdQuery(note.id))
            as QueryResponse.SingleObjectByIdQueryResponse<Note>

        // THEN
        assertThat(result.item, `is`(note))
    }

    @Test(expected = RepositoryException::class)
    fun getNoteById_whenIdDoesNotExist_returnsRepositoryError() {
        // WHEN
        testSubject.query(Query.SingleObjectByIdQuery(NOTE_ID_NOT_FOUND))
    }

    @Test
    fun deleteNote_whenNoteExists_removesNoteWithoutFailure() {
        // GIVEN
        val note = newNote()
        testSubject.save(note)

        // WHEN
        testSubject.remove(note)
    }

    @Test(expected = RepositoryException::class)
    fun deleteNote_whenNoteDoesNotExist_returnsRepositoryError() {
        // WHEN
        testSubject.remove(newNote())
    }

    private fun newNote() = Note("id", "title", "body", 0, 0)
}