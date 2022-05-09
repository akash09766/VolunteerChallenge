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
import com.skylight.android.volunteering.databinding.VehicleListRowBinding

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 22-04-2022 at 21:12.
 */
class SessionNumberListAdapter(
    private val sessionCount: Int,
    private val selectedItem: Int = 0
) :
    RecyclerView.Adapter<SessionNumberListAdapter.ItemViewHolder>() {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position, position == selectedItem)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding =
            SessionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount() = sessionCount

    override fun getItemId(position: Int) = position.toLong()

    class ItemViewHolder(private val binding: SessionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, isSelected: Boolean) {

            binding.sessionNum.text = (position + 1).toString()

            if (isSelected) {
                binding.sessionNum.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.primaryDarkColor
                    )
                )
                binding.sessionNum.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.selected_session_list_item_bg
                    )
                )
            } else {
                binding.sessionNum.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.cardview_dark_background
                    )
                )
                binding.sessionNum.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.unselected_session_list_item_bg
                    )
                )
            }
        }
    }
}