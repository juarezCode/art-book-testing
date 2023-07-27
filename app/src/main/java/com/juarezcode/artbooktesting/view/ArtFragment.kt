package com.juarezcode.artbooktesting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juarezcode.artbooktesting.R
import com.juarezcode.artbooktesting.adapter.ArtRecyclerAdapter
import com.juarezcode.artbooktesting.databinding.FragmentArtsBinding
import com.juarezcode.artbooktesting.view.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtFragment @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter
) : Fragment(R.layout.fragment_arts) {

    private var _binding: FragmentArtsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArtViewModel by activityViewModels()
    private val swipeCallBack =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val layoutPosition = viewHolder.layoutPosition
                val selectedArt = artRecyclerAdapter.arts[layoutPosition]
                viewModel.deleteArt(selectedArt)

            }

        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = binding

        subscribeToObservers()

        binding.recyclerArt.apply {
            adapter = artRecyclerAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(swipeCallBack).attachToRecyclerView(this)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }
    }

    private fun subscribeToObservers() {
        viewModel.artList.observe(viewLifecycleOwner) {
            artRecyclerAdapter.arts = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}