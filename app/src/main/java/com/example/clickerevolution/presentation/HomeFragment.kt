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
import com.example.clickerevolution.presentation.sharedviewmodel.SharedViewModel
import com.example.clickerevolution.presentation.sharedviewmodel.SharedViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var imageToClick: ImageView
    private lateinit var clickTick: TextView
    private lateinit var tickPerSec: TextView

    private lateinit var soundPool: SoundPool

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

        soundPool = SoundPool.Builder().setMaxStreams(4).build()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var resIdClick = R.raw.sound_cookie_click

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentSkin.collect {
                imageToClick.setImageResource(it.imageId)
                resIdClick = it.soundId
            }
        }

        val soundIdClick = soundPool.load(requireContext(), resIdClick, 1)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentResources.collect {
                clickTick.text = "${it.goldClickTickValue} за клик"
                tickPerSec.text = "${it.goldTickPerSecValue} в секунду"
            }
        }

        imageToClick.setOnClickListener {

            viewModel.incrementGoldByClick()

            soundPool.play(soundIdClick, 1.0f, 1.0f, 1, 0, 1.0f)

            imageToClick.animate().apply {
                duration = 50
                scaleXBy(1.0F)
                scaleX(0.9F)
                scaleYBy(1.0F)
                scaleY(0.9F)
            }.withEndAction {
                imageToClick.animate().apply {
                    duration = 50
                    scaleXBy(0.9F)
                    scaleX(1.0F)
                    scaleYBy(0.9F)
                    scaleY(1.0F)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.saveResources()
        soundPool.release()
    }

    private fun bindViews() {
        imageToClick = binding.imageViewImageToClick
        clickTick = binding.textViewClickTickValue
        tickPerSec = binding.textViewTickPerSecondValue
    }
}