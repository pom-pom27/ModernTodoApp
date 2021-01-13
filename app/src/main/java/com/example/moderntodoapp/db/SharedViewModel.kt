package com.example.moderntodoapp.db

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.example.moderntodoapp.R
import com.example.moderntodoapp.db.models.PriorityModel

class SharedViewModel(application: Application) : AndroidViewModel(application) {


    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            parent?.let {
                when (position) {
                    0 -> {
                        (parent.getChildAt(0) as TextView).setTextColor(
                            ContextCompat.getColor(
                                application,
                                R.color.green
                            )
                        )
                    }
                    1 -> {
                        (parent.getChildAt(0) as TextView).setTextColor(
                            ContextCompat.getColor(
                                application,
                                R.color.yellow
                            )
                        )
                    }
                    2 -> {
                        (parent.getChildAt(0) as TextView).setTextColor(
                            ContextCompat.getColor(
                                application,
                                R.color.red
                            )
                        )
                    }
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }

    fun verifyDataFromUser(title: String, description: String): Boolean {
        return if (title.isBlank() || description.isBlank()) {
            false
        } else !(title.isBlank() || description.isBlank())

    }

    fun parsePriority(priority: String): PriorityModel = when (priority) {
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

    fun parsePriorityToInt(priority: PriorityModel): Int {
        return when (priority) {
            PriorityModel.LOW -> 0
            PriorityModel.MEDIUM -> 1
            PriorityModel.HIGH -> 2
        }
    }


}