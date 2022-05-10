package com.skylight.android.volunteering.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.app.listerners.ListItemClickListener
import com.skylight.android.volunteering.app.model.event.EventInfo
import com.skylight.android.volunteering.databinding.AdminHomeScreenListRowItemBinding
import com.skylight.android.volunteering.databinding.EventListRowItemBinding

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 22-02-2022 at 23:14.
 * Recyclerview adapter to bind data to recyclerview and notify about item click to host fragment/activity
 */
class EventListAdapter(
    private var itemList: ArrayList<EventInfo?>,
    private val listener: ListItemClickListener<EventInfo>
) :
    RecyclerView.Adapter<EventListAdapter.ItemViewHolder>() {

    fun getData() = itemList

    fun setData(itemList: ArrayList<EventInfo?>) {
        this.itemList.clear()
        this.itemList = itemList
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        itemList?.get(position)?.let { holder.bind(it, listener = listener, position = position) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding =
            EventListRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ItemViewHolder(binding)
    }

    override fun getItemCount() = itemList!!.size

    override fun getItemId(position: Int) = position.toLong()

    class ItemViewHolder(private val binding: EventListRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(eventData: EventInfo, listener: ListItemClickListener<EventInfo>, position: Int) {
            binding.eventName.text = eventData.eventName
            binding.orgName.text = eventData.organisationName
//            binding.eventDate.text = eventData.eventStartDate
            binding.eventAddress.text = eventData.address
//            binding.langTv.text = eventData.sessionLanguage
//            binding.sessionCountTv.text = eventData.sessions?.size.toString().plus(" Session")
            binding.root.setOnClickListener {
                listener.onItemClick(eventData)
            }

            if (eventData.isVolunteeredForThisEvent) {
                binding.eventCardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.primaryDarkColor
                    )
                )
            } else {
                binding.eventCardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.primaryColor
                    )
                )
            }
        }
    }
}