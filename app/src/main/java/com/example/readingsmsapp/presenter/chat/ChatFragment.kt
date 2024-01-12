package com.example.readingsmsapp.presenter.chat

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.readingsmsapp.R
import com.example.readingsmsapp.data.MassageItem
import com.example.readingsmsapp.databinding.FragmentChatBinding

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val binding: FragmentChatBinding by viewBinding()

    private val adapter = ChatAdapter()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelableArray("massage_list", MassageItem::class.java)?.let {
            adapter.submitList(it.toList())
        }

        initializeRecycler()
    }

    private fun initializeRecycler() = with(binding.chatRecyclerView) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = this@ChatFragment.adapter
        addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
    }
}