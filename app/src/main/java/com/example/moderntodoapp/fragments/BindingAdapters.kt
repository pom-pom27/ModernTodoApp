package com.example.moderntodoapp.fragments

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.moderntodoapp.R
import com.example.moderntodoapp.db.models.PriorityModel
import com.example.moderntodoapp.db.models.TodoData
import com.example.moderntodoapp.fragments.list.ListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {
    companion object {
        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(fab: FloatingActionButton, navigate: Boolean) {
            fab.setOnClickListener {
                if (navigate) {
                    it.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:databaseEmptyIndicator")
        @JvmStatic
        fun databaseEmptyIndicator(view: View, isDatabaseEmpty: MutableLiveData<Boolean>) {
            when (isDatabaseEmpty.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:setSelectedItem")
        @JvmStatic
        fun setSelectedItem(spinner: Spinner, priority: PriorityModel) {
            return when (priority) {
                PriorityModel.LOW -> spinner.setSelection(0)
                PriorityModel.MEDIUM -> spinner.setSelection(1)
                PriorityModel.HIGH -> spinner.setSelection(2)
            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView: CardView, priority: PriorityModel) {
            when (priority) {
                PriorityModel.LOW -> cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.green))
                PriorityModel.MEDIUM -> cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.yellow))
                PriorityModel.HIGH -> cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.red))
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(cl: ConstraintLayout, currentTodoData: TodoData) {
            cl.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentTodoData)
                it.findNavController().navigate(action)

            }

        }

    }
}