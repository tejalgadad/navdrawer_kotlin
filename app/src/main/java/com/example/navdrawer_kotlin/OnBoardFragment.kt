package com.example.navdrawer_kotlin

import android.os.Bundle
//import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class OnBoardFragment: Fragment() {

    var position : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = arguments?.getInt("POSITION")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutId = if (position == 1) {
            R.layout.fragment_onboard1
        } else if (position == 2) {
            R.layout.fragment_onboard2
        } else if (position == 3) {
            R.layout.fragment_onboard3
        }else R.layout.fragment_onboard1
        return layoutInflater.inflate(layoutId, container, false)
    }

}