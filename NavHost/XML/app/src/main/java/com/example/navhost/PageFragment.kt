package com.example.navhost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs


class PageFragment : Fragment(R.layout.fragment_one){
    private val args: PageFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val tvPage = view.findViewById<TextView>(R.id.tvPage)
        val btn = view.findViewById<Button>(R.id.btn)

        tvPage.text = args.pageText
        btn.text = args.ButtonText

        btn.setOnClickListener{
            if (args.nextActionId != -1){
                findNavController().navigate(args.nextActionId)
            }
        }
    }
}