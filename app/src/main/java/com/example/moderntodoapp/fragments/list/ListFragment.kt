package com.example.moderntodoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.moderntodoapp.R
import com.example.moderntodoapp.databinding.FragmentListBinding
import com.example.moderntodoapp.db.SharedViewModel
import com.example.moderntodoapp.db.models.TodoData
import com.example.moderntodoapp.db.viewModel.TodoViewModel
import com.example.moderntodoapp.fragments.list.adapter.ListAdapter
import com.example.moderntodoapp.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

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

        hideKeyboard(requireActivity())
        return binding.root
    }

    private fun setupRecycleView() {
        with(binding.rvTodo) {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
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
                restoreDeletedItem(viewHolder.itemView, deletedItem)
            }
        }
        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedItem(view: View, deletedItem: TodoData) {
        val snackBar = Snackbar.make(
            view, "Deleted '${deletedItem.title}.", Snackbar.LENGTH_LONG
        )
        snackBar.setAction("UNDO") {
            mTodoViewModel.insertData(deletedItem)
        }
        snackBar.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.list_deleteAll_menu -> confirmRemoveAll()
            R.id.sort_low -> mTodoViewModel.sortByLowPriority.observe(
                this,
                { mAdapter.setData(it) })
            R.id.sort_high -> mTodoViewModel.sortByHighPriority.observe(
                this,
                { mAdapter.setData(it) })
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoveAll() {
        val builder = AlertDialog.Builder(requireContext())

        with(builder) {
            setPositiveButton("Yes") { _, _ ->
                mTodoViewModel.deleteAll()
                Toast.makeText(requireContext(), "Successfully deleted!", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("No") { _, _ -> }
            setTitle("Deleting All.")
            setMessage("Are you sure want to delete all?")
            create().show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        val searchMenu = menu.findItem(R.id.menu_list_search)
        val searchView = searchMenu.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            searchThroughDatabase(it)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        query?.let {
            searchThroughDatabase(it)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"

        mTodoViewModel.searchQuery(searchQuery).observe(this, { list ->
            list?.let { mAdapter.setData(it) }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}