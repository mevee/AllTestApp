package com.pareminder.noteFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.pareminder.R
import com.pareminder.data.local_db.NotesDatabase
import com.pareminder.data.local_db.tables.Note
import com.pareminder.data.repository.NotesRepository
import com.pareminder.databinding.FragmentAllNotesBinding
import com.pareminder.common.EventConst
import com.pareminder.common.ScreenState
import com.pareminder.ui.note.notesadapter.ListAdapterListener

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AllNotesFragment : Fragment(), ListAdapterListener {
    private var param1: String? = null
    private var param2: String? = null


    lateinit var binding: FragmentAllNotesBinding

    lateinit var viewModel: NoteViewModel
    lateinit var notesList: MutableList<Note>
    lateinit var database: NotesDatabase
    lateinit var notesRepository: NotesRepository
    private val notesAdapter by lazy {
        NoteAdapter(context, notesList, this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllNotesBinding.inflate(inflater, container, false)

        binding.addNewNote.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_allNotesFragment_to_editViewNoteFragment)

        }

        notesList = mutableListOf()
        binding.rvNotesList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        database = NotesDatabase.getInstance(requireContext())
        notesRepository = NotesRepository(database)
        val noteViewModelFactory = NoteViewModelFactory(notesRepository)
        viewModel = ViewModelProvider(this, noteViewModelFactory).get(NoteViewModel::class.java)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(NoteViewModel::class.java)

        viewModel.notes.observe(viewLifecycleOwner, { list ->
            if (list != null) {
                setReclerViewAdapter(list)
            }
        })
        viewModel.screenState.observe(viewLifecycleOwner, {
            handleScreenSate(it)
        })


        return binding.root
    }


    private fun handleScreenSate(it: ScreenState?) {
        it?.let {
            if (it is ScreenState.Lading) {
                binding.progressMain.isVisible = true
            } else if (it is ScreenState.Completed) {
                binding.progressMain.isVisible = false
            } else {
                binding.progressMain.isVisible = false
            }
        }

    }

    private fun setReclerViewAdapter(list: List<Note>) {
        notesList.clear()
        notesList.addAll(list)
        binding.rvNotesList.adapter = notesAdapter
    }

    override fun onItemClicked(position: Int, event: EventConst?) {
        event?.let {
            when (it) {
                EventConst.CLICKED -> viewNotesDetails(notesList[position])
                EventConst.EDIT -> editNote(notesList[position])
                EventConst.DELETE -> viewModel.removeNote(notesList[position], position)
            }
        }

    }

    private fun viewNotesDetails(note: Note) {

        val bundle = Bundle()
        bundle.putSerializable("note", note)
        bundle.putString("id", "")

        Navigation.findNavController(binding.root)
            .navigate(R.id.action_allNotesFragment_to_editViewNoteFragment, bundle)
    }

    private fun editNote(note: Note) {
        val bundle = Bundle()
        bundle.putSerializable("note", note)
        bundle.putString("id", "${note.id}")

        Navigation.findNavController(binding.root)
            .navigate(R.id.action_allNotesFragment_to_editViewNoteFragment, bundle)


    }



    override fun onResume() {
        super.onResume()
        viewModel.loadAllNotes()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllNotesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}