package com.fajri.bangkit_2024_bfaa.Navigation.TabLayout.single

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fajri.bangkit_2024_bfaa.R

class SingleFragment : Fragment() {
    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_single, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTabel: TextView = view.findViewById(R.id.section_label)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)

        tvTabel.text = getString(R.string.content_tab_text, index)
    }
}