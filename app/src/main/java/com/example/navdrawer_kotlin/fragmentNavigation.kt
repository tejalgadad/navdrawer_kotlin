package com.example.navdrawer_kotlin
import androidx.fragment.app.Fragment

interface fragmentNavigation {
    fun navigateFrag(fragment: Fragment, addToStack:Boolean)
}