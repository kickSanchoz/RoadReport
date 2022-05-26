package ru.one2work.android.customer.base

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

    abstract fun setLayoutID(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mViewBinding = DataBindingUtil.inflate(inflater, setLayoutID(), container, false)
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

    open fun parseArguments() {}

    override fun onDestroyView() {
        super.onDestroyView()
        mViewBinding = null
    }

    protected fun setupAppBarSearch(appBar: AppBarSmallBinding,
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