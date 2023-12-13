package com.example.pollapplication

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View.GONE
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.example.pollapplication.fragments.CreatePollFragment
import com.example.pollapplication.fragments.CurrentPolls
import com.example.pollapplication.fragments.HistoryFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    var fragmentContainerView:FragmentContainerView ?= null
    var currentPollsLayout:LinearLayout ?= null
    var historyLayout:LinearLayout ?= null
    var currentPolls:ImageView ?= null
    var history:ImageView ?= null
    var addPoll:FloatingActionButton? = null

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentContainerView = findViewById(R.id.fragmentContainerView)
        currentPollsLayout = findViewById(R.id.current_polls_layout)
        historyLayout = findViewById(R.id.history_layout)
        currentPolls = findViewById(R.id.current_polls)
        history = findViewById(R.id.history)
        addPoll = findViewById(R.id.add_poll)

        history?.setColorFilter(getColor(R.color.gray))

        addPoll?.setOnTouchListener(OnTouchListener { view, motionEvent ->
            addPoll?.visibility = GONE
            currentPollsLayout?.visibility = GONE
            historyLayout?.visibility = GONE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, CreatePollFragment()).commit()
            false
        })

        currentPollsLayout?.setOnTouchListener(OnTouchListener { view, motionEvent ->
            currentPolls?.background = getDrawable(R.drawable.selected_item)
            history?.background = null
            currentPolls?.setColorFilter(getColor(R.color.black))
            history?.setColorFilter(getColor(R.color.gray))
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, CurrentPolls()).commit()
            false
        })

        historyLayout?.setOnTouchListener(OnTouchListener { view, motionEvent ->
            currentPolls?.background = null
            history?.background = getDrawable(R.drawable.selected_item)
            currentPolls?.setColorFilter(getColor(R.color.gray))
            history?.setColorFilter(getColor(R.color.black))
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, HistoryFragment()).commit()
            false
        })
    }
}