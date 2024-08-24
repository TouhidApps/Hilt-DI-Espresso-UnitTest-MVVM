package com.touhidapps.currency.utils

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.touhidapps.currency.R
import com.touhidapps.currency.ui.CurrencyListFragment


fun addFragment(activity: FragmentActivity, fragment: Fragment) {

    activity.supportFragmentManager.beginTransaction()
        .setCustomAnimations(
            R.anim.fragment_enter,
            R.anim.fragment_exit,
            R.anim.fragment_pop_enter,
            R.anim.fragment_pop_exit
        )
        .add(R.id.containerView , fragment)
        .addToBackStack("${fragment.javaClass.canonicalName}")
        .commit()

}