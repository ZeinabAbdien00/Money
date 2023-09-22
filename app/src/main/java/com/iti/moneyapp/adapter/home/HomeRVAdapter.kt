package com.iti.moneyapp.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.iti.moneyapp.databinding.HomeRvItemBinding
import com.iti.moneyapp.model.db.HomeModel


class HomeRVAdapter : RecyclerView.Adapter<HomeRVAdapter.AllCountriesAdapter>() {

    inner class AllCountriesAdapter(val binding: HomeRvItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<HomeModel>() {
        override fun areItemsTheSame(oldItem: HomeModel, newItem: HomeModel): Boolean {
            return oldItem.itemName == newItem.itemName
        }

        override fun areContentsTheSame(oldItem: HomeModel, newItem: HomeModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCountriesAdapter {
        val binding =
            HomeRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllCountriesAdapter(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AllCountriesAdapter, position: Int) {
        val item = differ.currentList[position]
        holder.binding.root.apply {

            holder.binding.apply {
                if (item.itemName != null) {
                    tvItemName.text = item.itemName
                } else {
                    tvItemName.text = "No item Name"
                }

                if (item.itemValue != null) {
                    tvItemValue.text = item.itemValue.toString()
                } else {
                    tvItemValue.text = "No item value"
                }
            }
            setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }

    }

    private var onItemClickListener: ((HomeModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (HomeModel) -> Unit) {
        onItemClickListener = listener
    }
}