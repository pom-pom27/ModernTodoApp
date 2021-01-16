package com.example.moderntodoapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moderntodoapp.R
import com.example.moderntodoapp.databinding.FragmentUpdateBinding
import com.example.moderntodoapp.db.SharedViewModel
import com.example.moderntodoapp.db.models.TodoData
import com.example.moderntodoapp.db.viewModel.TodoViewModel


class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mTodoViewModel: TodoViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args

        //Set Menu
        setHasOptionsMenu(true)

        binding.dropdown.onItemSelectedListener = mSharedViewModel.listener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.upgrade_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.update_save_menu -> updateItem()
            R.id.update_delete_menu -> confirmItemRemoval()
        }

        return super.onOptionsItemSelected(item)
    }


    private fun updateItem() {
        val currentTitle = binding.etTitle.text.toString()
        val currentPriority = binding.dropdown.selectedItem.toString()
        val currentDescription = binding.etDescription.text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(currentTitle, currentDescription)

        if (validation) {
            val todoData = TodoData(
                    args.currentItem.id,
                    currentTitle,
                    mSharedViewModel.parsePriority(currentPriority),
                    currentDescription
            )
            mTodoViewModel.updateData(todoData)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill all the field", Toast.LENGTH_SHORT).show()

        }
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTodoViewModel.deleteData(args.currentItem)
            Toast.makeText(requireContext(), "Successfuly deleted!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ ->
        }
        builder.setTitle("Deleting '${args.currentItem.title}.'")
        builder.setMessage("Are you sure want to delete '${args.currentItem.title}' ?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
