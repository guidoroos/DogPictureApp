package com.example.dogsapplication.doghistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsapplication.database.Dog
import com.example.dogsapplication.databinding.DogListItemBinding


class DogAdapter (private val clickListener: DogListener, private val longClickListener:DogLongClickListener) : ListAdapter<Dog, DogAdapter.ViewHolder>(DogDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, longClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: DogListItemBinding): RecyclerView.ViewHolder(binding.root) {

        /**
         * icon is being set via binding adapter
         *
         * @param item
         * @param clickListener
         * @param longClickListener
         */
        fun bind(item:Dog, clickListener: DogListener, longClickListener: DogLongClickListener) {
            binding.dog = item
            binding.clickListener = clickListener
            binding.longClickListener = longClickListener
            binding.executePendingBindings()

        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DogListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class DogDiffCallback :
        DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.dogId == newItem.dogId
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
        }
    }
}

/**
 * Dog listener used for navigation to dog picture fragment to see larger picture on icon click
 *
 * @property clickListener
 * @constructor Create empty Dog listener
 */
class DogListener(val clickListener: (dog: Dog) -> Unit) {
    fun onClick(dog: Dog) = clickListener(dog)
}

/**
 * Dog long click listener to be used for deleting dog picture on longclick
 *
 * @property clickListener
 * @constructor Create empty Dog long click listener
 */
class DogLongClickListener(val clickListener: (dog: Dog) -> Boolean) {
    fun onClick(dog: Dog):Boolean {
        clickListener(dog)
        return true
    }
}



