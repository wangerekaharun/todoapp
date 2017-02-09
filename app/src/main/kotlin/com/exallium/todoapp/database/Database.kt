package com.exallium.todoapp.database

import rx.Single

/**
 * Database Interface.  A Database represents the single source of
 * truth for all entities.  Entities should not be passed between screens.
 * Instead their ID should be passed, and queried from the Single source of
 * Truth.
 */
interface Database {
    /**
     * Returns all the notes currently in the database
     */
    fun getAllNotes(): Single<List<Note>>

    /**
     * Gets the Note with id from the database
     */
    fun getNoteById(id: String): Single<Note>

    /**
     * Saves note to the database
     */
    fun saveNote(note: Note): Single<Unit>

    /**
     * Removes note from the database
     */
    fun deleteNote(note: Note): Single<Unit>
}