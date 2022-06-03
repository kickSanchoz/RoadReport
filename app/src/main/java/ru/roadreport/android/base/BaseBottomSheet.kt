package ru.roadreport.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.roadreport.android.R

abstract class BaseBottomSheet<VB: ViewDataBinding> : BottomSheetDialogFragment() {
    private var mViewDataBinding: VB? = null
    val binding
        get() = mViewDataBinding!!

    abstract fun setLayoutId(): Int

    open fun setTheme(): Int = R.style.BottomSheetTheme

    override fun getTheme(): Int {
        return setTheme()
    }

    open fun setLayoutHeight(): Int {
        return (binding.root.resources.displayMetrics.heightPixels * 0.5).toInt()
    }


    open fun parseArgument() {}

    open fun setupActivityResults() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgument()
        setupActivityResults()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewDataBinding = DataBindingUtil.inflate(inflater, setLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureBottomSheet()
        setupViews()
        observeViews()
        observeData()
    }

    open fun configureBottomSheet() {
        dialog?.apply {
            window?.setWindowAnimations(R.style.DialogAnimation)

            val bottomSheet = findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            ).apply {
                layoutParams.height = setLayoutHeight()
            }

            val behavior = BottomSheetBehavior.from(bottomSheet).apply {
                skipCollapsed = true
                //Временное решение, как убрать half expanded иначе не знаю =)
                halfExpandedRatio = .00000001f
                state = BottomSheetBehavior.STATE_EXPANDED
                isFitToContents = true
//                expandedOffset =
//                    (binding.root.resources.displayMetrics.heightPixels * 0.4).toInt()
            }

            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {

                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            })
        }
    }

    open fun setupViews() {}

    open fun observeViews() {}

    open fun observeData() {}

    override fun onDestroyView() {
        super.onDestroyView()
        mViewDataBinding = null
    }
}