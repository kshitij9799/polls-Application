package com.example.pollapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pollapplication.R
import com.example.pollapplication.adapter.RecyclerviewAdapter
import java.lang.String
import kotlin.Int
import kotlin.let

open class CreatePollFragment : Fragment() {
    var editTextCount:Int ?= null
    companion object{
        @SuppressLint("StaticFieldLeak")
        var addOptionButton: LinearLayout ?= null
        @JvmField
        var inputTextCount:Int ?= 0
        @JvmField
        var InputList: List<String> = listOf<String>()
        @SuppressLint("StaticFieldLeak")
        var addOptionText:TextView ?= null
        @JvmField
        var recyclerView: RecyclerView? = null
        @SuppressLint("StaticFieldLeak")
        var recyclerviewAdapter: RecyclerviewAdapter? = null
    }

    @SuppressLint("UseCompatLoadingForDrawables", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_create_poll, container, false)
        recyclerView = view.findViewById(R.id.add_option_recycler)
        addOptionButton = view.findViewById(R.id.add_option_layout)
        addOptionText = view.findViewById<TextView>(R.id.add_option_text)
        addOptionText?.text = String.format(getString(R.string.add_options),"5")
        addOptionButton?.setOnClickListener(View.OnClickListener { view ->
            if (inputTextCount!! < 5) {
                if (inputTextCount == 4) addOptionButton?.background = resources.getDrawable(R.drawable.selected_item)
                inputTextCount = inputTextCount?.plus(1)
                addOptionText?.text = String.format(getString(R.string.add_options),(5 - inputTextCount!!).toString())
                inputTextCount?.let { recyclerViewChanges(it) }
            }
        })
        return view
    }

    fun recyclerViewChanges(editTextCount:Int) {
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerviewAdapter = context?.let { RecyclerviewAdapter(it,editTextCount) }
        recyclerView?.adapter = recyclerviewAdapter
    }
}