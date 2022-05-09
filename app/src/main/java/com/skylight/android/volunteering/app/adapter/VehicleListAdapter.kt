package com.skylight.android.volunteering.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.app.listerners.ListItemClickListener
import com.skylight.android.volunteering.app.model.PoiListItem
import com.skylight.android.volunteering.app.util.MConstants.FleetType.TAXI
import com.skylight.android.volunteering.databinding.VehicleListRowBinding

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 22-02-2022 at 23:14.
 * Recyclerview adapter to bind data to recyclerview and notify about item click to host fragment/activity
 */
class VehicleListAdapter(
    private val itemList: List<PoiListItem?>?,
    private val listener: ListItemClickListener<PoiListItem>
) :
    RecyclerView.Adapter<VehicleListAdapter.ItemViewHolder>() {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        itemList?.get(position)?.let { holder.bind(it, listener = listener) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding =
            VehicleListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount() = itemList!!.size

    override fun getItemId(position: Int) = position.toLong()

    class ItemViewHolder(private val binding: VehicleListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(vehicleData: PoiListItem, listener: ListItemClickListener<PoiListItem>) {
            binding.carNum.text = vehicleData.id.toString()
            binding.carTypeTv.text = vehicleData.fleetType
            if (vehicleData.fleetType?.equals(TAXI.name) == true) {
                binding.carTypeIv.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.carNum.context,
                        R.drawable.taxi
                    )
                )
            } else {
                binding.carTypeIv.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.carNum.context,
                        R.drawable.car_sharing
                    )
                )
            }

            binding.root.setOnClickListener {
                listener.onItemClick(vehicleData)
            }
        }
    }
}