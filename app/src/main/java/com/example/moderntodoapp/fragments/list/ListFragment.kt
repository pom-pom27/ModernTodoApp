package com.example.moderntodoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moderntodoapp.R
import com.example.moderntodoapp.databinding.FragmentListBinding
import com.example.moderntodoapp.db.SharedViewModel
import com.example.moderntodoapp.db.models.TodoData
import com.example.moderntodoapp.db.viewModel.TodoViewModel
import com.example.moderntodoapp.fragments.list.adapter.ListAdapter
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val mTodoViewModel: TodoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private val mAdapter: ListAdapter by lazy { ListAdapter(requireContext()) }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.sharedViewModel = mSharedViewModel

        setHasOptionsMenu(true)

        setupRecycleView()

        mTodoViewModel.getAllData.observe(
                viewLifecycleOwner,
                { data ->
                    mSharedViewModel.checkIsEmptyDatabase(data)
                    mAdapter.setData(data)
                })
        return binding.root
    }

    private fun setupRecycleView() {
        with(binding.rvTodo) {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

        swipeToDelete(binding.rvTodo)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = mAdapter.dataList[viewHolder.adapterPosition]
                //Delete Item
                mTodoViewModel.deleteData(deletedItem)
                mAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                //Restore Item
                restoreDeletedItem(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
            }
        }
        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedItem(view: View, deletedItem: TodoData, position: Int) {
        val snackBar = Snackbar.make(
                view, "Deleted '${deletedItem.title}.", Snackbar.LENGTH_LONG
        )
        snackBar.setAction("UNDO") {
            mTodoViewModel.insertData(deletedItem)
            mAdapter.notifyItemChanged(position)
        }
        snackBar.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.list_deleteAll_menu -> confirmRemoveAll()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoveAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTodoViewModel.deleteAll()
            Toast.makeText(requireContext(), "Successfully deleted!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Deleting All.")
        builder.setMessage("Are you sure want to delete all?")
        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}