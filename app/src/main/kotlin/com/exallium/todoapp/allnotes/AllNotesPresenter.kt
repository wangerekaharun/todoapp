package com.exallium.todoapp.allnotes

import android.os.Bundle
import com.exallium.todoapp.entities.Note
import com.exallium.todoapp.mvp.BasePresenter
import com.exallium.todoapp.screenbundle.BundleFactory
import com.exallium.todoapp.screenbundle.ScreenBundleHelper

/**
 * Presenter for AllNotes Screen
 */
class AllNotesPresenter(view: AllNotesView,
                        private val adapter: AllNotesAdapter,
                        private val model: AllNotesModel,
                        private val screenBundleHelper: ScreenBundleHelper,
                        private val bundleFactory: BundleFactory) : BasePresenter<AllNotesView>(view) {

    private val showNoteSubscriberFn = { note: Note? ->
        view.showSingleNote(makeNoteDetailBundle(note?.id))
    }

    private val showCreateNoteSubscriberFn: (Unit?) -> (Unit) = {
        view.showCreateNote(bundleFactory.createBundle())
    }

    override fun onViewCreated(restore: Boolean) {
        view.setAdapter(adapter)
        adapter.requestUpdate()

        adapter.noteClicks().subscribe(showNoteSubscriberFn).addToComposite()
        view.addNoteClicks().map { null }.subscribe(showCreateNoteSubscriberFn).addToComposite()

        setupDeleteNoteSubscription()
    }

    fun setupDeleteNoteSubscription() {
        adapter.noteSwipes()
                .flatMap { model.deleteNote(it).toObservable() }
                .subscribe( {
                    adapter.requestUpdate()
                    view.showNoteDeletedMessage()
                }, { view.showUnableToLoadNoteDetailError() }).addToComposite()
    }

    override fun onViewDestroyed() {
        adapter.cleanup()
    }

    private fun makeNoteDetailBundle(id: String?) : Bundle {
        return bundleFactory.createBundle().apply { screenBundleHelper.setNoteId(this, id) }
    }
}
