package com.example.kotlin_ex1.ui.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_ex1.R
import com.example.kotlin_ex1.ui.models.Todo
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity(),
    AddNoteDialogFragment.AddNoteDialogFragmentActions {

    companion object {
        const val LIST_SPACING = 10
        const val ADD_NOTE_DIALOG_TAG = "com.example.kotlin_ex1.ui.ui.AddNoteDialogFragment"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

/*
    @Inject
    @field:Named("TestStringA") lateinit var testA: String

    @Inject
    @field:Named("TestStringB") lateinit var testB: String
    Toast.makeText(this, "Tests results are: $testA and $testB and $test",
        Toast.LENGTH_LONG).show()
 */

    private val mainViewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var mainListAdapter: MainRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadUi()
        observeViewModel()

    }

    private fun loadUi() {
        mainListAdapter = MainRecyclerAdapter()
        main_todoRecycler.apply {
            addItemDecoration(
                MainListItemDecoration(LIST_SPACING)
            )
            layoutManager = LinearLayoutManager(context)
            adapter = mainListAdapter
        }

        main_activityMainAddNoteFab.apply {
            setOnClickListener {
                val dialog =
                    AddNoteDialogFragment.getInstance(this@MainActivity)
                dialog.show(
                    supportFragmentManager,
                    ADD_NOTE_DIALOG_TAG
                )
            }
        }
    }

    private fun observeViewModel() {
        mainViewModel.todoListLiveData.observe(this, Observer {
            mainListAdapter.submitList(it)
        })
    }

    override fun userClickedOk(todo: Todo) {
        mainViewModel.addTodo(todo)
    }

}