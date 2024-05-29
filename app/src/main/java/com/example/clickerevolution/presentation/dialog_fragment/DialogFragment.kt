package com.example.clickerevolution.presentation.dialog_fragment

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

        binding.dialogGoldEarned.text = goldEarned.toString()

        binding.okButton.setOnClickListener {
            sharedViewModel.incrementGoldEarnedWhileOffline(goldEarned)
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
//
//    private fun okButtonClicker() {
//        if (isNewItem) {
//            okAddItemBeenClicked()
//        } else {
//            okUpdateItemBeenClicked()
//        }
//        dismiss()
//    }
//
//    private fun okAddItemBeenClicked() {
//        shouldClearPrefs = true
//
//        val inputTitleResult = inputFieldTitle.text.toString()
//        val inputDescriptionResult = inputFieldDescription.text.toString()
//        mainViewModel.insertItem(ToDoItem(inputTitleResult, inputDescriptionResult))
//        inputFieldTitle.text.clear()
//        inputFieldDescription.text.clear()
//    }
//
//    private fun okUpdateItemBeenClicked() {
//        val inputTitleResult = inputFieldTitle.text.toString()
//        val inputDescriptionResult = inputFieldDescription.text.toString()
//        item?.let { ToDoItem(it.id, inputTitleResult, inputDescriptionResult) }
//            ?.let { mainViewModel.updateItem(it) }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        if (isNewItem) {
//            //if (!shouldClearPrefs) {
//            val inputTitleResult = inputFieldTitle.text.toString()
//            val inputDescriptionResult = inputFieldDescription.text.toString()
//            dialogFragmentViewModel.saveDataInPrefs(PREFS_TITLE_KEY, inputTitleResult)
//            dialogFragmentViewModel.saveDataInPrefs(PREFS_DESCRIPTION_KEY, inputDescriptionResult)
//            //}
//        }
//    }
}