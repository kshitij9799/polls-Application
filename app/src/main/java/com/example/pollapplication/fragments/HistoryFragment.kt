package com.example.pollapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.pollapplication.AppDatabase
import com.example.pollapplication.MainActivity
import com.example.pollapplication.MainActivity.Companion.currentPolls
import com.example.pollapplication.MainActivity.Companion.history
import com.example.pollapplication.MainActivity.Companion.showNavButtons
import com.example.pollapplication.Question
import com.example.pollapplication.R
import com.example.pollapplication.adapter.CardRecyclerView
import kotlinx.coroutines.*

class HistoryFragment : Fragment() {

    var recyclerView : RecyclerView?= null
    var cardRecyclerViewAdapter : CardRecyclerView ?= null
    var noCardTextView : TextView?= null
    var users: List<Question>? = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_history, container, false)
        history?.background = context?.let { getDrawable(it,R.drawable.selected_item) }
        currentPolls?.background = null
        val content = activity?.applicationContext
        noCardTextView = view.findViewById(R.id.no_history_text)
        recyclerView = view.findViewById(R.id.card_history_view)
        currentPolls?.setColorFilter(getColor(content!!,R.color.gray))
        history?.setColorFilter(getColor(content!!,R.color.black))

        // Inflate the layout for this fragment
        showNavButtons()
        val db = context?.let {
            Room.databaseBuilder(
                it,
                AppDatabase::class.java, "dataw"
            ).build()
        }
        GlobalScope.launch {
            val userDao = db?.questionDao()
            users = userDao?.getAll()
            if (users?.size != 0)noCardTextView?.visibility = View.GONE

            try {
                recyclerView?.setHasFixedSize(true)
                recyclerView?.layoutManager = LinearLayoutManager(activity)
                cardRecyclerViewAdapter = context?.let { CardRecyclerView(it,users) }
                recyclerView?.adapter = cardRecyclerViewAdapter
            } catch (E:Exception) {
                Log.d("logcheck", "onCreateView: " + E.message)
            }
        }
        return view
    }
}