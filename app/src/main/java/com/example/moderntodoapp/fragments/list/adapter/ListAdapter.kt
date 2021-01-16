package com.example.moderntodoapp.fragments.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moderntodoapp.databinding.RowLayoutBinding
import com.example.moderntodoapp.db.models.TodoData
import java.util.Collections.emptyList

class ListAdapter(private val context: Context) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<TodoData>()

    class MyViewHolder(private val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todoData: TodoData) {
            binding.todoData = todoData
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
            MyViewHolder.from(parent)

    override fun getItemCount(): Int =
            dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    fun setData(listTodoData: List<TodoData>) {
        dataList = listTodoData
        notifyDataSetChanged()
    }
}