package com.juarezcode.artbooktesting.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.juarezcode.artbooktesting.R
import com.juarezcode.artbooktesting.adapter.ImageRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageApiFragment @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment(R.layout.fragment_image_api) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}