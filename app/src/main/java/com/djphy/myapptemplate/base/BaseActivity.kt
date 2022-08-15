package com.djphy.myapptemplate.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.djphy.myapptemplate.R

open class BaseActivity : AppCompatActivity() {

    protected fun addFragment(fragment: BaseFragment) {
        addFragment(fragment, false)
    }

    protected fun addFragment(fragment: BaseFragment, addToStack: Boolean) {
        if (isFinishing) {
            return
        }
        if (isDestroyed) {
            return
        }
        val isActivityInForeground = lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
        if (isActivityInForeground.not()) {
            return
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        if (addToStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }
}