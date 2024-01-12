package com.example.readingsmsapp.presenter.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.readingsmsapp.data.MassageItem
import com.example.readingsmsapp.databinding.MassageItemBinding
import java.text.SimpleDateFormat

class ChatAdapter: RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(
        private val binding: MassageItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(massageItem: MassageItem) = with(binding) {
            val formatted = SimpleDateFormat("HH:mm")
            date.text = formatted.format(massageItem.date)

            when(massageItem.type) {
                1 -> {
                    senderMassage.text = massageItem.text
                    youMassage.visibility = View.GONE
                }
                2 -> {
                    youMassage.text = massageItem.text
                    senderMassage.visibility = View.GONE
                }
            }
        }
    }

    private val list = mutableListOf<MassageItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = MassageItemBinding.inflate(layoutInflater, parent, false)
        return ChatViewHolder(binding)
    }

    override fun getItemCount(): Int =
        list.size
//df
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun submitList(list: List<MassageItem>) = with(this.list) {
        clear()
        addAll(list)
        reverse()
        notifyDataSetChanged()
    }
}