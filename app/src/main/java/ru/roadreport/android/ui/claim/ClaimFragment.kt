package ru.roadreport.android.ui.claim

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.roadreport.android.R

class ClaimFragment : Fragment() {

    companion object {
        fun newInstance() = ClaimFragment()
    }

    private lateinit var viewModel: ClaimViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_claim, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClaimViewModel::class.java)
        // TODO: Use the ViewModel
    }

}