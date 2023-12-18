package com.example.pollapplication

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase.deleteDatabase
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.room.Room
import com.example.pollapplication.fragments.CreatePollFragment
import com.example.pollapplication.fragments.CurrentPolls
import com.example.pollapplication.fragments.HistoryFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var fragmentContainerView:FragmentContainerView ?= null
    @SuppressLint("StaticFieldLeak")
    companion object {
        @SuppressLint("StaticFieldLeak")
        var history:ImageView ?= null
        @SuppressLint("StaticFieldLeak")
        var currentPolls:ImageView ?= null
        @JvmField
        var mutableStringList = MutableList<String?>(5) { null }
        var addPoll:FloatingActionButton? = null
        @SuppressLint("StaticFieldLeak")
        var currentPollsLayout:LinearLayout ?= null
        @SuppressLint("StaticFieldLeak")
        var historyLayout:LinearLayout ?= null

        fun showNavButtons(){
            addPoll?.visibility =  View.VISIBLE
            currentPollsLayout?.visibility =  View.VISIBLE
            historyLayout?.visibility =  View.VISIBLE
        }

    }

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

        addPoll?.setOnTouchListener(OnTouchListener { _, _ ->
            addPoll?.visibility = GONE
            currentPollsLayout?.visibility = GONE
            historyLayout?.visibility = GONE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, CreatePollFragment())
                .addToBackStack("")
                .commit()
            true
        })

        currentPollsLayout?.setOnTouchListener(OnTouchListener { _, _ ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, CurrentPolls())
                .addToBackStack("")
                .commit()
            true
        })

        historyLayout?.setOnTouchListener(OnTouchListener { _, _ ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, HistoryFragment())
                .addToBackStack("")
                .commit()
            true
        })
        applicationContext.deleteDatabase("dataw")
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "dataw"
        ).build()
        try {
            GlobalScope.launch {
                val userDao = db.questionDao()
                val users: List<Question> = userDao.getAll()
                if (users.isNotEmpty()) {
                    userDao.insertAll(Question(users[users.size - 1].uid + 1,"a","s",true,1))
                } else {
                    userDao.insertAll(Question(0,"a","s,a,a,c",false,1))
                }
            }
        }catch (ex:Exception){
            Log.d("checkChanges", "onCreate: " + ex.message)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack()
        Log.d("adbs", "onBackPressed: " + 1)
    }
}