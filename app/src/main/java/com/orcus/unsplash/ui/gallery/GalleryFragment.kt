package com.orcus.unsplash.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.filter
import com.orcus.unsplash.R
import com.orcus.unsplash.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "galleryFrag"

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<GalleryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        var adapter = UnsplashPhotoAdapter()

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
            adapter.notifyDataSetChanged()

            Log.d(TAG, "onViewCreated: ${adapter.itemCount}")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}