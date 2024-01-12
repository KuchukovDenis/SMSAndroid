package com.example.readingsmsapp.presenter.sms_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.readingsmsapp.data.SmsItem
import com.example.readingsmsapp.databinding.SmsItemBinding
import java.text.SimpleDateFormat

class SmsListAdapter(
    private val onSmsItemClick: (SmsItem) -> Unit,
) : RecyclerView.Adapter<SmsListAdapter.SmsListViewHolder>() {

    class SmsListViewHolder(
        private val binding: SmsItemBinding,
        private val onSmsItemClick: (SmsItem) -> Unit,
        ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(smsItem: SmsItem) = with(binding) {

            val formatted = SimpleDateFormat("HH:mm")
            date.text = formatted.format(smsItem.massages.first().date)

            sender.text = smsItem.sender
            message.text = smsItem.massages.first().text

            root.setOnClickListener {
                onSmsItemClick(smsItem)
            }
        }
    }

    private val list = mutableListOf<SmsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsListViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = SmsItemBinding.inflate(layoutInflater, parent, false)
        return SmsListViewHolder(binding, onSmsItemClick)
    }

    override fun getItemCount(): Int =
        list.size

    override fun onBindViewHolder(holder: SmsListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun submitList(list: List<SmsItem>) = with(this.list) {
        clear()
        addAll(list)
        notifyDataSetChanged()
    }
}