package com.skylight.android.volunteering.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.app.listerners.ListItemClickListener
import com.skylight.android.volunteering.app.model.PoiListItem
import com.skylight.android.volunteering.app.util.MConstants.FleetType.TAXI
import com.skylight.android.volunteering.databinding.SessionListItemBinding
import com.skylight.android.volunteering.databinding.SpeakerListItemBinding
import com.skylight.android.volunteering.databinding.VehicleListRowBinding

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 22-04-2022 at 21:12.
 */
class SpeakerListAdapter(
    private val sessionCount: Int,
) :
    RecyclerView.Adapter<SpeakerListAdapter.ItemViewHolder>() {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding =
            SpeakerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount() = sessionCount

    override fun getItemId(position: Int) = position.toLong()

    class ItemViewHolder(private val binding: SpeakerListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
        }
    }
}