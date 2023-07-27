package com.juarezcode.artbooktesting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.juarezcode.artbooktesting.R
import com.juarezcode.artbooktesting.adapter.ImageRecyclerAdapter
import com.juarezcode.artbooktesting.databinding.FragmentImageApiBinding
import com.juarezcode.artbooktesting.util.Status
import com.juarezcode.artbooktesting.view.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImageApiFragment @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment(R.layout.fragment_image_api) {

    private var _binding: FragmentImageApiBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArtViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var job: Job? = null

        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        subscribeToObservers()

        binding.recyclerImage.adapter = imageRecyclerAdapter
        binding.recyclerImage.layoutManager = GridLayoutManager(requireContext(), 3)

        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }
    }

    private fun subscribeToObservers() {
        viewModel.imageList.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult -> imageResult.previewURL }
                    imageRecyclerAdapter.images = urls ?: listOf()
                    binding.progressBar.visibility = View.GONE

                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG)
                        .show()
                    binding.progressBar.visibility = View.GONE

                }

                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE

                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}