package com.skylight.android.volunteering.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skylight.android.volunteering.app.listerners.ListItemClickListener
import com.skylight.android.volunteering.databinding.AdminHomeScreenListRowItemBinding

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 22-02-2022 at 23:14.
 * Recyclerview adapter to bind data to recyclerview and notify about item click to host fragment/activity
 */
class AdminHomeListAdapter(
    private val itemList: Array<String>,
    private val listener: ListItemClickListener<Int>
) :
    RecyclerView.Adapter<AdminHomeListAdapter.ItemViewHolder>() {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        itemList?.get(position)?.let { holder.bind(it, listener = listener, position = position) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding =
            AdminHomeScreenListRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ItemViewHolder(binding)
    }

    override fun getItemCount() = itemList!!.size

    override fun getItemId(position: Int) = position.toLong()

    class ItemViewHolder(private val binding: AdminHomeScreenListRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String, listener: ListItemClickListener<Int>, position: Int) {
            binding.menuText.text = title
            binding.menuText.setOnClickListener {
                listener.onItemClick(position)
            }
        }
    }
}