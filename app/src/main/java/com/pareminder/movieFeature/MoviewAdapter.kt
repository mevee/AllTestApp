package com.pareminder.movieFeature

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.pareminder.data.local_db.tables.Note
import com.pareminder.databinding.NotesItemLayoutBinding
import com.pareminder.common.EventConst
import com.pareminder.data.network.response_models.Movie
import com.pareminder.databinding.MovieItemLayoutBinding
import com.pareminder.ui.note.notesadapter.ListAdapterListener

class MoviewAdapter(
    private val context: Context?,
    private val dataList: List<Movie>,
    private val clickListener: ListAdapterListener?=null
) : RecyclerView.Adapter<MoviewAdapter.NoteViewHolder>() {

    class NoteViewHolder(val noteBinding: MovieItemLayoutBinding) :
        RecyclerView.ViewHolder(noteBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = MovieItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val bind = holder.noteBinding
        val data = dataList[position]
        bind.tvDate.text ="${ data.title}\n\n${ data.overview}"
        bind.ivMovie.load("https://image.tmdb.org/t/p/w500${data.backdrop_path}")

    }

    override fun getItemCount() = dataList.size

/**/
}