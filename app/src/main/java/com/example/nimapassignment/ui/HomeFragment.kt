package com.example.nimapassignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nimapassignment.adapter.FundsAdapter
import com.example.nimapassignment.databinding.FragmentHomeBinding
import com.example.nimapassignment.ui.LoadingStates.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val newAdapter = FundsAdapter()

        binding.recycler.apply {
            adapter = newAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.items.observe(viewLifecycleOwner,{ item ->
            newAdapter.differ.submitList(item)
        } )

        viewModel.loadingStates.observe(viewLifecycleOwner, { states ->
            when(states){
                LOADING -> showProgressBar()
                SUCCESS -> hideProgressBar()
                ERROR -> {
                    Toast.makeText(requireContext(), "Can't load items. Please check your internet connection", Toast.LENGTH_SHORT).show()
                    hideProgressBar()
                }
            }
        })

        return binding.root
    }

    private fun hideProgressBar(){
        binding.loadingHome.visibility = View.GONE
    }

    private fun showProgressBar(){
        binding.loadingHome.visibility = View.VISIBLE
    }

}