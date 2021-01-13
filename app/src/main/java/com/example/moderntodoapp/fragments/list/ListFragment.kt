package com.example.moderntodoapp.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moderntodoapp.R
import com.example.moderntodoapp.databinding.FragmentListBinding
import com.example.moderntodoapp.db.ListAdapter
import com.example.moderntodoapp.db.viewModel.TodoViewModel


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    private val mTodoViewModel: TodoViewModel by viewModels()

    val mAdapter: ListAdapter by lazy { ListAdapter(requireContext()) }

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root



        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }



        with(binding.rvTodoList) {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

        mTodoViewModel.getAllData.observe(
            viewLifecycleOwner,
            Observer { data -> mAdapter.setData(data) })

        setHasOptionsMenu(true)

        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        

        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}