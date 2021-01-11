package com.example.moderntodoapp.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moderntodoapp.R
import com.example.moderntodoapp.databinding.FragmentAddBinding
import com.example.moderntodoapp.db.TodoViewModel
import com.example.moderntodoapp.db.models.PriorityModel
import com.example.moderntodoapp.db.models.TodoData


class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val mTodoViewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root

        //Set Menu
        setHasOptionsMenu(true)

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
        val validation = verifyDataFromUser(mTitle, mDescription)

        if (validation) {
            val newData = TodoData(0, mTitle, parsePriority(mPriority), mDescription)
            mTodoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Todo successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please input all field", Toast.LENGTH_SHORT).show()

        }
    }

    private fun verifyDataFromUser(title: String, description: String): Boolean {
        return if (title.isBlank() || description.isBlank()) {
            false
        } else !(title.isBlank() || description.isBlank())

    }

    private fun parsePriority(priority: String): PriorityModel = when (priority) {
        "High Priority" -> {
            PriorityModel.HIGH
        }
        "Medium Priority" -> {
            PriorityModel.MEDIUM
        }
        "Low Priority" -> {
            PriorityModel.LOW
        }
        else -> PriorityModel.LOW
    }

}