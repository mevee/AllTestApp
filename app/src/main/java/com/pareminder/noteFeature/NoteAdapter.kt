package com.pareminder.noteFeature

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pareminder.data.local_db.tables.Note
import com.pareminder.databinding.NotesItemLayoutBinding
import com.pareminder.common.EventConst
import com.pareminder.ui.note.notesadapter.ListAdapterListener

class NoteAdapter(
    private val context: Context?,
    private val dataList: List<Note>,
    private val clickListener: ListAdapterListener
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(val noteBinding: NotesItemLayoutBinding) :
        RecyclerView.ViewHolder(noteBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NotesItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val bind = holder.noteBinding
        val data = dataList[position]
        bind.tvDate.text = data.time
        bind.tvMessage.text = "${data.message}\n.......\n......."

        bind.mainNote.setOnClickListener {
            clickListener.onItemClicked(position, EventConst.CLICKED)
        }

        bind.ivDelete.setOnClickListener {
            showDeletCOnfirmationDialoge(position)
        }
        bind.ivEdit.setOnClickListener {
            clickListener.onItemClicked(position, EventConst.EDIT)
        }
    }

    override fun getItemCount() = dataList.size


    fun showDeletCOnfirmationDialoge(position: Int) {

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Alert!!")
        builder.setMessage("Do you want to delete this note?")

        builder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
                clickListener.onItemClicked(position, EventConst.DELETE)

            }
        })

        builder.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
            }
        })
        builder.show()
    }
}