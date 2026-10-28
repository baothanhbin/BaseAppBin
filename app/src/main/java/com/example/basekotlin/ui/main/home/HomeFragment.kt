package com.example.basekotlin.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.FragmentHomeBinding
import com.example.basekotlin.ui.main.MainActivity
import com.example.basekotlin.ui.setting.SettingActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentHomeBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applySystemBarInsets()
        bindView()
        observeUiState()
    }

    private fun bindView() {
        binding.ivSettingsAction.tap {
            (requireActivity() as MainActivity)
                .startNextActivity(SettingActivity::class.java, null)
        }
        binding.ivGuideAction.setOnClickListener(null)
    }

    private fun applySystemBarInsets() {
        val topBarTopMargin =
            (binding.topBar.layoutParams as androidx.constraintlayout.widget.ConstraintLayout.LayoutParams).topMargin

        ViewCompat.setOnApplyWindowInsetsListener(binding.contentContainer) { _, insets ->
            val statusBars = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            binding.topBar.updateLayoutParams<androidx.constraintlayout.widget.ConstraintLayout.LayoutParams> {
                topMargin = topBarTopMargin + statusBars.top
            }
            insets
        }
        ViewCompat.requestApplyInsets(binding.contentContainer)
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::render)
            }
        }
    }

    private fun render(state: HomeUiState) {
        binding.tvHomeTitle.text = state.title
        binding.tvHomeHeadline.text = state.title
        binding.tvHomePreviewMessage.text = state.previewMessage
    }

    override fun onDestroyView() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.contentContainer, null)
        _binding = null
        super.onDestroyView()
    }
}
