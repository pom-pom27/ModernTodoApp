package com.example.moderntodoapp.db

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.moderntodoapp.R
import com.example.moderntodoapp.databinding.ItemGridBinding
import com.example.moderntodoapp.db.models.PriorityModel
import com.example.moderntodoapp.db.models.TodoData
import com.example.moderntodoapp.fragments.list.ListFragmentDirections

class ListAdapter(val context: Context) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<TodoData>()

    class MyViewHolder(val binding: ItemGridBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataItem = dataList[position]

        with(holder.binding) {
            tvTitle.text = dataItem.title
            tvDescription.text = dataItem.desc
            itemGridCL.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataItem)
                it.findNavController().navigate(action)
            }

            when (dataItem.priorityModel) {
                PriorityModel.LOW -> cvPriorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context, R.color.green
                    )
                )

                PriorityModel.MEDIUM -> cvPriorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context, R.color.yellow
                    )
                )

                PriorityModel.HIGH -> cvPriorityIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context, R.color.red
                    )
                )
            }
        }
    }

    fun setData(listTodoData: List<TodoData>) {
        dataList = listTodoData

        notifyDataSetChanged()
    }
}