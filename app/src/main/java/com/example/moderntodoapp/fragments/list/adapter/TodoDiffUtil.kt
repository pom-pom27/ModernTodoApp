package com.example.moderntodoapp.fragments.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.moderntodoapp.db.models.TodoData

class TodoDiffUtil(private val oldList: List<TodoData>, private val newList: List<TodoData>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = (oldList[oldItemPosition] === newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            (oldList[oldItemPosition].id == newList[newItemPosition].id
                    && oldList[oldItemPosition].title == newList[newItemPosition].title
                    && oldList[oldItemPosition].desc == newList[newItemPosition].desc
                    && oldList[oldItemPosition].priorityModel == newList[newItemPosition].priorityModel)
}