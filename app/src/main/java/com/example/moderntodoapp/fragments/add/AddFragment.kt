package com.example.moderntodoapp.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moderntodoapp.R
import com.example.moderntodoapp.databinding.FragmentAddBinding
import com.example.moderntodoapp.db.SharedViewModel
import com.example.moderntodoapp.db.models.TodoData
import com.example.moderntodoapp.db.viewModel.TodoViewModel


class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val mTodoViewModel: TodoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root

        //Set Menu
        setHasOptionsMenu(true)

        binding.dropdown.onItemSelectedListener = mSharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.Add_menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitle = binding.etTitle.text.toString()
        val mPriority = binding.dropdown.selectedItem.toString()
        val mDescription = binding.etDescription.text.toString()
        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)

        if (validation) {
            val newData =
                TodoData(0, mTitle, mSharedViewModel.parsePriority(mPriority), mDescription)
            mTodoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Todo successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please input all field", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}