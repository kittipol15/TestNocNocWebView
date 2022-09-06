package com.example.testwebview

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        renderWeb(edtURL.text.toString())

        btnGo.setOnClickListener {
            renderWeb(edtURL.text.toString())
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        edtURL.setOnKeyListener { _, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                btnGo.performClick()
                true
            } else {
                false
            }
        }
    }

    private fun renderWeb(url: String) {
        replaceFragment(
            WebFragment.newInstance(
                url,
                true
            ), false
        )
    }

    fun AppCompatActivity.replaceFragment(
        fragment: androidx.fragment.app.Fragment,
        isAddToBackStack: Boolean,
        tag: String? = null
    ) {
        val transaction = supportFragmentManager.beginTransaction()
        if (isAddToBackStack) {
            transaction.replace(R.id.fragmentContainer, fragment)
                .setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null).commit()
        } else {
            transaction.replace(R.id.fragmentContainer, fragment, tag).commit()
        }
    }
}
