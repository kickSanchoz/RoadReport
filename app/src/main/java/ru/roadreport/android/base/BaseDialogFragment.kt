package ru.roadreport.android.base

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import ru.roadreport.android.R

abstract class BaseDialogFragment<VB: ViewDataBinding> : DialogFragment() {
    private var mViewDataBinding: VB? = null

    val binding
        get() = mViewDataBinding!!

    abstract fun getLayoutId(): Int

    override fun getTheme(): Int = R.style.DialogFragmentTheme

    open fun getWindowAnimation(): Int {
        return R.style.DialogAnimation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgument()
        setupActivityResults()
    }

    open fun parseArgument() {}

    open fun setupActivityResults() {}



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }



    override fun onStart() {
        super.onStart()
        configureDialogFragment()
        setupViews()
        observeViews()
        observeData()
    }

    open fun getDialogWidth(): Int  {
        return ViewGroup.LayoutParams.MATCH_PARENT
    }

    open fun getDialogHeight(): Int  {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }

    open fun getDialogGravity(): Int {
        return Gravity.BOTTOM
    }

    open fun configureDialogFragment() {
        dialog?.apply {
            window?.setWindowAnimations(getWindowAnimation())
            //TODO если высота лейаута WRAP_CONTENT, при 0 статус бар становится черным и не работает, при любой другой все отрабатывет
            window?.attributes?.y = 1
            window?.setLayout(
                getDialogWidth(),
                getDialogHeight()
            )
            window?.setGravity(getDialogGravity())
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