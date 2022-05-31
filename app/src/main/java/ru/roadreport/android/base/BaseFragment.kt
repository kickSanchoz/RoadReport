package ru.roadreport.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import ru.roadreport.android.databinding.AppBarSmallBinding
import ru.roadreport.android.utils.doOnApplyWindowInsets


abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    private var mViewBinding: VB? = null
    val binding get() = mViewBinding!!

    abstract fun setLayoutId(): Int

    open fun parseArguments() {}

    open fun setupActivityResults() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
        setupActivityResults()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mViewBinding = DataBindingUtil.inflate(inflater, setLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViews()
        observeData()
    }

    open fun setupViews() {}

    open fun observeViews() {}

    open fun observeData() {}

    override fun onDestroyView() {
        super.onDestroyView()
        actionOnDestroyView()
        mViewBinding = null
    }

    open fun actionOnDestroyView() {}

    protected fun setupAppBar(appBar: AppBarSmallBinding,
                                    label: String,
                                    @DrawableRes btnSrc: Int? = null) {
        appBar.tvTitle.text = label
        appBar.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        btnSrc?.let {
            appBar.btnImgSrc = ResourcesCompat.getDrawable(resources, btnSrc, null)
        }
        appBar.root.doOnApplyWindowInsets { view, insets, padding ->
            view.updatePadding(
                top = padding.top + insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            )
            insets
        }
    }
}