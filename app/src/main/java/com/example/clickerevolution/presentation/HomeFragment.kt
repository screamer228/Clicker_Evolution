package com.example.clickerevolution.presentation

import android.media.SoundPool
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.clickerevolution.R
import com.example.clickerevolution.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var imageToClick: ImageView
    private lateinit var counterTV: TextView
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        bindViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.resourcesFlow.collect { resources ->

                binding.textViewCounter.text = resources.toString()
            }
        }

        val soundPool = SoundPool.Builder()
            .setMaxStreams(4)
            .build()
        val soundId = soundPool.load(requireContext(), R.raw.sound_cookie_click, 1)

        binding.imageViewImageToClick.setOnClickListener {

            viewModel.incrementResources()

            soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f)

            binding.imageViewImageToClick.animate().apply {
                duration = 50
                scaleXBy(1.0F)
                scaleX(0.9F)
                scaleYBy(1.0F)
                scaleY(0.9F)
            }.withEndAction {
                binding.imageViewImageToClick.animate().apply {
                    duration = 50
                    scaleXBy(0.9F)
                    scaleX(1.0F)
                    scaleYBy(0.9F)
                    scaleY(1.0F)
                }
            }
        }
    }

    private fun bindViews() {
        imageToClick = binding.imageViewImageToClick
        counterTV = binding.textViewCounter
    }
}