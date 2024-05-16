package com.example.clickerevolution.presentation

import android.media.SoundPool
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.clickerevolution.R
import com.example.clickerevolution.app.App
import com.example.clickerevolution.databinding.FragmentHomeBinding
import com.example.clickerevolution.presentation.viewmodel.SharedViewModel
import com.example.clickerevolution.presentation.viewmodel.SharedViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var imageToClick: ImageView
    private lateinit var counterTV: TextView

    @Inject
    lateinit var viewModelFactory: SharedViewModelFactory
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as App).appComponent.injectHomeFragment(this)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[SharedViewModel::class.java]

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        bindViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.resourcesFlow.collect {

                binding.textViewCounter.text = it.gold.toString()
            }
        }

        val soundPool = SoundPool.Builder()
            .setMaxStreams(4)
            .build()
        val soundId = soundPool.load(requireContext(), R.raw.sound_click, 1)

        binding.imageViewImageToClick.setOnClickListener {

            viewModel.incrementGold()

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