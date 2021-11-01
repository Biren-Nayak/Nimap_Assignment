package com.example.nimapassignment.adapter

import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nimapassignment.R
import com.example.nimapassignment.models.Collection
import com.example.nimapassignment.databinding.ItemViewBinding
import kotlin.coroutines.coroutineContext


class FundsAdapter : RecyclerView.Adapter<FundsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.titleTextview.text = item.title
        holder.binding.descriptionTextview.text = item.description
        holder.binding.fundedTextview.text = item.collected
        holder.binding.goalTextview.text = item.goal
        holder.binding.pledgeButton.setOnClickListener {
            Toast.makeText(holder.binding.root.context, "Thank you for your support 😊", Toast.LENGTH_SHORT).show()
        }
        val dateDiff = (item.endDate.time - item.startDate.time)/3600/24000
        holder.binding.remainingTextview.text = dateDiff.toString()
        Glide.with(holder.binding.root)
            .load(item.imgUrl)
            .into(holder.binding.image)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Collection>() {
        override fun areItemsTheSame(oldItem: Collection, newItem: Collection): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Collection, newItem: Collection): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemViewBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }


    override fun getItemCount(): Int = differ.currentList.size
}