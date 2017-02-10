package com.exallium.todoapp.allnotes

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife

/**
 * View of a Note in the AllNotesAdapter
 */
class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object : NoteViewHolderFactory {
        override fun create(parent: ViewGroup): NoteViewHolder {
            // TODO -- Add actual layout
            val view = LayoutInflater.from(parent.context)
                    .inflate(android.R.layout.two_line_list_item, parent, false)
            return NoteViewHolder(view)
        }
    }

    @BindView(android.R.id.text1)
    lateinit var titleView: TextView

    @BindView(android.R.id.text2)
    lateinit var bodyView: TextView

    init {
        ButterKnife.bind(this, itemView)
        titleView.maxLines = 1
        titleView.ellipsize = TextUtils.TruncateAt.END
        bodyView.maxLines = 1
        bodyView.ellipsize = TextUtils.TruncateAt.END
    }

    fun setTitle(title: String) {
        titleView.text = title
    }

    fun setBody(body: String) {
        bodyView.text = body
    }
}
