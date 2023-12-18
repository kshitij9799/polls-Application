package com.example.pollapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.pollapplication.AppDatabase
import com.example.pollapplication.MainActivity.Companion.currentPolls
import com.example.pollapplication.MainActivity.Companion.history
import com.example.pollapplication.MainActivity.Companion.showNavButtons
import com.example.pollapplication.Question
import com.example.pollapplication.R
import com.example.pollapplication.adapter.CardRecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [CurrentPolls.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrentPolls : Fragment() {
    var recyclerView : RecyclerView ?= null
    var cardRecyclerViewAdapter : CardRecyclerView ?= null
    var noCardTextView : TextView ?= null
    var questions: List<Question>? = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_current_polls, container, false)
        noCardTextView = view.findViewById(R.id.null_current_poll_text)
        recyclerView = view.findViewById(R.id.card_recycler_view)
        currentPolls?.background = context?.let { getDrawable(it, R.drawable.selected_item) }
        history?.background = null
        val content = activity?.applicationContext
        currentPolls?.setColorFilter(ContextCompat.getColor(content!!, R.color.black))
        history?.setColorFilter(ContextCompat.getColor(content!!, R.color.gray))
        showNavButtons()
        val db = context?.let {
            Room.databaseBuilder(
                it,
                AppDatabase::class.java, "dataw"
            ).build()
        }
        GlobalScope.launch {
            val userDao = db?.questionDao()
            questions = userDao?.getAll()
        }
            if (questions!!.isNotEmpty()) noCardTextView?.visibility = View.GONE
            recyclerView?.setHasFixedSize(true)
            recyclerView?.layoutManager = LinearLayoutManager(activity)
            cardRecyclerViewAdapter = context?.let { CardRecyclerView(it,questions) }
            recyclerView?.adapter = cardRecyclerViewAdapter

        // Inflate the layout for this fragment
        return view
    }

    companion object {
    }
}