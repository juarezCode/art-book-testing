package com.juarezcode.artbooktesting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.juarezcode.artbooktesting.R
import com.juarezcode.artbooktesting.databinding.FragmentArtDetailsBinding
import com.juarezcode.artbooktesting.util.Status
import com.juarezcode.artbooktesting.view.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailsFragment @Inject constructor(
    private val glide: RequestManager
) : Fragment(R.layout.fragment_art_details) {

    private var _binding: FragmentArtDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArtViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()

        binding.imgArt.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.btnSave.setOnClickListener {
            viewModel.makeArt(
                binding.txtArtName.text.toString(),
                binding.txtArtistName.text.toString(),
                binding.txtYear.text.toString()
            )
        }
    }

    private fun subscribeToObservers() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner) { url ->
            glide.load(url).into(binding.imgArt)
        }
        viewModel.insertArtMessage.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMessage()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Error ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
                Status.LOADING -> TODO()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}