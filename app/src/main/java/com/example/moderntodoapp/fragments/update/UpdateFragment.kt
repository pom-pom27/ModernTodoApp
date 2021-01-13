package com.example.moderntodoapp.fragments.update

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
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        //Set Menu
        setHasOptionsMenu(true)


        with(binding) {
            etTitle.setText(args.currentItem.title)
            etDesc.setText(args.currentItem.desc)
            dropdown.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priorityModel))
            dropdown.onItemSelectedListener = mSharedViewModel.listener
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.upgrade_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.update_save_menu) {
            updateItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val currentTitle = binding.etTitle.text.toString()
        val currentPriority = binding.dropdown.selectedItem.toString()
        val currentDescription = binding.etDesc.text.toString()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
