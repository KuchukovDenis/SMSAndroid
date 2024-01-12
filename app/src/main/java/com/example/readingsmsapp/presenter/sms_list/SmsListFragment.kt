package com.example.readingsmsapp.presenter.sms_list

import android.Manifest.permission.READ_SMS
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.readingsmsapp.R
import com.example.readingsmsapp.data.SmsItem
import com.example.readingsmsapp.databinding.FragmentSmsListBinding
import com.example.readingsmsapp.requirePermission

class SmsListFragment : Fragment(R.layout.fragment_sms_list) {

    private val binding: FragmentSmsListBinding by viewBinding()

    private val viewModel: SmsListViewModel by viewModels()

    private val adapter = SmsListAdapter(
        ::onSmsItemClick,
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.smsList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        initializeRecycler()
        requireSmsPermission()
    }

    private fun requireSmsPermission() {
        requirePermission(
            permission = READ_SMS,
            successDelegate = {
                viewModel.loadSmsList(requireContext().contentResolver)
            },
            failureDelegate = {

            }
        )
    }


    private fun onSmsItemClick(smsItem: SmsItem) {
        val action = SmsListFragmentDirections.actionSmsListFragmentToChatFragment2(
            smsItem.massages.toTypedArray()
        )
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun initializeRecycler() = with(binding.smsRecyclerView) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = this@SmsListFragment.adapter
        addItemDecoration(createItemDecorator())
    }

    private fun createItemDecorator() =
        DividerItemDecoration(requireContext(), RecyclerView.VERTICAL).apply {
            ContextCompat.getDrawable(requireContext(), R.drawable.sms_item_decoration)
                ?.let { this@apply.setDrawable(it) }
        }

}