package ru.roadreport.android.ui.draft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.roadreport.android.R

class DraftFragment : Fragment() {

    companion object {
        fun newInstance() = DraftFragment()
    }

    private lateinit var viewModel: DraftViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_draft, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DraftViewModel::class.java)
        // TODO: Use the ViewModel
    }

}