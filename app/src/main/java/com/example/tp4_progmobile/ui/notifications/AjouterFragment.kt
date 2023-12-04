package com.example.tp4_progmobile.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.tp4_progmobile.databinding.FragmentAjouterBinding

class AjouterFragment : Fragment() {

    private var _binding: FragmentAjouterBinding? = null
    private val binding get() = _binding!!

    // Use viewModels() delegate to get an instance of the ViewModel
    private val notificationsViewModel: AjouterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAjouterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Use null-safe access to avoid NullPointerException
        binding.textViewAjouter?.let { textView ->
            notificationsViewModel.text.observe(viewLifecycleOwner) {
                textView.text = it
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding reference to avoid memory leaks
        _binding = null
    }
}
