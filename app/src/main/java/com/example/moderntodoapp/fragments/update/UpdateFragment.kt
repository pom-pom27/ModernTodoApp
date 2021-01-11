package com.example.moderntodoapp.fragments.update

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.moderntodoapp.R
import com.example.moderntodoapp.databinding.FragmentUpdateBinding


class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        //Set Menu
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.upgrade_fragment_menu, menu)
    }
}