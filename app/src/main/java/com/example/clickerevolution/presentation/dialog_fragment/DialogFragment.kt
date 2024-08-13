package com.example.clickerevolution.presentation.dialog_fragment

import android.app.Dialog
import android.media.SoundPool
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.clickerevolution.R
import com.example.clickerevolution.app.App
import com.example.clickerevolution.databinding.FragmentDialogBinding
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModel
import com.example.clickerevolution.presentation.home_fragment.sharedviewmodel.SharedViewModelFactory
import com.example.clickerevolution.utils.AnimationUtils.setTouchAnimation
import com.example.clickerevolution.utils.StringUtil.addCommaEveryThreeDigits
import javax.inject.Inject

class DialogFragment(
    private val goldEarned: Int
) : DialogFragment() {

    private lateinit var binding: FragmentDialogBinding

    @Inject
    lateinit var sharedViewModelFactory: SharedViewModelFactory
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var soundPool: SoundPool

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as App).appComponent.injectDialogFragment(this)

        injectSharedViewModel()

        binding = FragmentDialogBinding.inflate(inflater, container, false)

        soundPool = SoundPool.Builder().setMaxStreams(2).build()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val soundIdClick = soundPool.load(requireContext(), R.raw.sound_action1, 1)

        binding.dialogGoldEarned.text = "+ ${addCommaEveryThreeDigits(goldEarned)}"
        binding.dialogGoldCouldEarned.text = "x2 +${addCommaEveryThreeDigits(goldEarned * 2)}"

        binding.dialogButtonOk.apply {
            setTouchAnimation(0.9f)
            setOnClickListener {
                sharedViewModel.incrementGoldEarnedWhileOffline(goldEarned)
                soundPool.play(soundIdClick, 0.9f, 0.9f, 1, 0, 1.0f)
                dismiss()
            }
        }

        binding.dialogButtonDoubleIt.apply {
            setTouchAnimation(0.9f)
            setOnClickListener {
                sharedViewModel.incrementGoldEarnedWhileOffline(goldEarned * 2)
                soundPool.play(soundIdClick, 0.9f, 0.9f, 1, 0, 1.0f)
                dismiss()
            }
        }
    }

    private fun injectSharedViewModel() {
        sharedViewModel =
            ViewModelProvider(
                requireActivity(),
                sharedViewModelFactory
            )[SharedViewModel::class.java]
    }
}