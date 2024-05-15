package com.example.clickerevolution.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.clickerevolution.app.App
import com.example.clickerevolution.databinding.FragmentUpgradeBinding
import com.example.clickerevolution.presentation.viewmodel.SharedViewModel
import com.example.clickerevolution.presentation.viewmodel.SharedViewModelFactory
import javax.inject.Inject

class UpgradeFragment : Fragment() {

    private lateinit var binding: FragmentUpgradeBinding

    @Inject
    lateinit var viewModelFactory: SharedViewModelFactory
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as App).appComponent.injectUpgradeFragment(this)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[SharedViewModel::class.java]

        binding = FragmentUpgradeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}