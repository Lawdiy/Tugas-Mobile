package com.example.recyclerview.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.domain.model.Item
import com.google.android.material.card.MaterialCardView
import com.google.android.material.materialswitch.MaterialSwitch

class ItemAdapter(
    private var items: List<Item>,
    private val onItemClick: (Item) -> Unit,
    private val onSwitchChange: (Item, Boolean) -> Unit,
    private val onButtonClick: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    fun updateData(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: MaterialCardView = itemView.findViewById(R.id.card_view)
        private val ivItemIcon: ImageView = itemView.findViewById(R.id.iv_item_icon)
        private val tvImageText: TextView = itemView.findViewById(R.id.tv_image_text)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        private val switchItem: MaterialSwitch = itemView.findViewById(R.id.switch_item)
        private val btnAction: Button = itemView.findViewById(R.id.btn_action)

        fun bind(item: Item, position: Int) {
            tvTitle.text = item.title
            tvDescription.text = item.description
            ivItemIcon.setImageResource(item.imageResId)
            tvImageText.text = "Gambar\n${item.id}"
            switchItem.isChecked = item.isSwitchOn

            if (position % 2 == 0) {
                cardView.setCardBackgroundColor(Color.parseColor("#F0F0F0"))
            } else {
                cardView.setCardBackgroundColor(Color.parseColor("#CFE1C7"))
            }

            itemView.setOnClickListener { onItemClick(item) }

            switchItem.setOnCheckedChangeListener { _, isChecked ->
                item.isSwitchOn = isChecked
                onSwitchChange(item, isChecked)
            }

            btnAction.setOnClickListener { onButtonClick(item) }
        }
    }
}
