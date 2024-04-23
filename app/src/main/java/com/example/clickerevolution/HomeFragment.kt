package com.example.clickerevolution

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.clickerevolution.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Собираем Flow с помощью корутин
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.resourcesFlow.collect { resources ->
                // Обновляем интерфейс с новым количеством ресурсов
                binding.resourcesTextView.text = resources.toString()
            }
        }

        binding.backgroundImage.setOnClickListener {
            viewModel.incrementResources()
            binding.backgroundImage.animate().apply {
                duration = 50
                scaleXBy(1.0F)
                scaleX(0.9F)
                scaleYBy(1.0F)
                scaleY(0.9F)
            }.withEndAction {
                binding.backgroundImage.animate().apply {
                    duration = 50
                    scaleXBy(0.9F)
                    scaleX(1.0F)
                    scaleYBy(0.9F)
                    scaleY(1.0F)
                }
            }
        }

//        // Обработка клика на кнопке
//        binding.clickButton.setOnClickListener {
//            // Увеличиваем количество ресурсов при каждом клике
//            viewModel.incrementResources()
//            binding.clickButton.animate().apply {
//
//            }
//        }

    }

}