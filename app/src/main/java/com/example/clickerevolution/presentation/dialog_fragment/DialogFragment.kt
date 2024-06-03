package com.example.clickerevolution.presentation.dialog_fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.clickerevolution.app.App
import com.example.clickerevolution.databinding.FragmentDialogBinding
import com.example.clickerevolution.presentation.sharedviewmodel.SharedViewModel
import com.example.clickerevolution.presentation.sharedviewmodel.SharedViewModelFactory
import javax.inject.Inject

class DialogFragment(private val goldEarned: Int) : DialogFragment() {

    private lateinit var binding: FragmentDialogBinding

    @Inject
    lateinit var sharedViewModelFactory: SharedViewModelFactory
    private lateinit var sharedViewModel: SharedViewModel

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

        sharedViewModel =
            ViewModelProvider(
                requireActivity(),
                sharedViewModelFactory
            )[SharedViewModel::class.java]

        binding = FragmentDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dialogGoldEarned.text = "+$goldEarned"
        binding.dialogGoldCouldEarned.text = "x2 +${goldEarned * 2}"

        binding.buttonOk.setOnClickListener {
            sharedViewModel.incrementGoldEarnedWhileOffline(goldEarned)
            dismiss()
        }

        binding.buttonDoubleIt.setOnClickListener {
            sharedViewModel.incrementGoldEarnedWhileOffline(goldEarned * 2)
            dismiss()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        observers()
//    }
//
//    private fun clickListeners() {
//        okButton.setOnClickListener {
//            okButtonClicker()
//        }
//        closeButton.setOnClickListener {
//            dismiss()
//        }
//    }
//
//    private fun observers() {
//        dialogFragmentViewModel.todoItemResult.observe(this) {
//            if (isNewItem) {
//                //if (!shouldClearPrefs) {
//                inputFieldTitle.setText(it.title)
//                inputFieldDescription.setText(it.description)
//                //}
//            }
//        }
//    }
}