package com.pareminder.noteFeature

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.pareminder.R
import com.pareminder.common.ScreenState
import com.pareminder.common.printMessage
import com.pareminder.data.local_db.NotesDatabase
import com.pareminder.data.local_db.tables.Note
import com.pareminder.data.repository.NotesRepository
import com.pareminder.databinding.FragmentEditNoteBinding
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "id"

class EditViewNoteFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var note: Note? = null
    lateinit var viewModel: NoteViewModel
    lateinit var notesRepository: NotesRepository
    lateinit var database: NotesDatabase

    lateinit var binding: FragmentEditNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.getSerializable("note") != null)
                note = it.getSerializable("note") as Note

            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            FragmentEditNoteBinding.inflate(inflater, container, false)
        database = NotesDatabase.getInstance(requireContext())
        notesRepository = NotesRepository(database)
        val noteViewModelFactory = NoteViewModelFactory(notesRepository)
        viewModel = ViewModelProvider(this, noteViewModelFactory).get(NoteViewModel::class.java)

        viewModel.screenState.observe(viewLifecycleOwner, {
            handleScreenSate(it)
        })

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.save.setOnClickListener {
            validateMessage()
        }

        if (param2?.length==0){
            binding.etEditorField.isFocusableInTouchMode=false
            binding.save.isVisible=false
        }
        note?.let {
            binding.etEditorField.setText(it.message)
            binding.tvTitle.setText(it.title)
        }

        binding.etEditorField.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val message =s.toString()
                if (message.length < 15)
                   binding.tvTitle.text =  message
                else
                    binding.tvTitle.text = message.substring(0, 10)

            }
            override fun afterTextChanged(s: Editable?) {
             }
        })

        return binding.root
    }

    private fun validateMessage() {
        val message = binding.etEditorField.text.toString()

        val caneder = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("DD-MMM-YYYY")

        val currentDate = dateFormat.format(caneder.time)

        if (binding.etEditorField.length() == 0) {
            activity?.printMessage("Message is required")
        }
        var title =""
            if (message.length < 15)
                title = message
            else
                title = message.substring(0, 10)

        if (note == null)
            note = Note(null, title, message, currentDate)
        else
        {
            note = Note(id =note?.id , title,message, currentDate)
        }

        viewModel.saveNotes(note!!)
        onBackPressed()
    }
    fun onBackPressed(){
        Navigation.findNavController(binding.root).popBackStack()

    }

    private fun handleScreenSate(it: ScreenState?) {
        it?.let {
            if (it is ScreenState.Lading) {
                binding.progressEdit.isVisible = true
            } else if (it is ScreenState.Completed) {
                binding.progressEdit.isVisible = false
            } else {
                binding.progressEdit.isVisible = false
            }
        }

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditViewNoteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}