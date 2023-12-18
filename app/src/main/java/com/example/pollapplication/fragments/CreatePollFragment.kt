package com.example.pollapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.pollapplication.AppDatabase
import com.example.pollapplication.MainActivity
import com.example.pollapplication.Question
import com.example.pollapplication.R
import com.example.pollapplication.adapter.RecyclerviewAdapter
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

open class CreatePollFragment : Fragment() {
    var editTextCount:Int ?= null
    companion object{
        @SuppressLint("StaticFieldLeak")
        var addOptionButton: LinearLayout ?= null
        @JvmField
        var inputTextCount:Int ?= 0
        @SuppressLint("StaticFieldLeak")
        var addOptionText:TextView ?= null
        @JvmField
        var recyclerView: RecyclerView? = null
        @SuppressLint("StaticFieldLeak")
        var recyclerviewAdapter: RecyclerviewAdapter? = null
        @SuppressLint("StaticFieldLeak")
        var createButton: TextView ?= null
        @SuppressLint("StaticFieldLeak")
        var questionEditText: TextView ?= null
    }

    @SuppressLint("UseCompatLoadingForDrawables", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_create_poll, container, false)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        recyclerView = view.findViewById(R.id.add_option_recycler)
        addOptionButton = view.findViewById(R.id.add_option_layout)
        addOptionText = view.findViewById<TextView>(R.id.add_option_text)
        createButton = view.findViewById(R.id.create_button)
        questionEditText = view.findViewById(R.id.question_edit_text)

        addOptionText?.text = String.format(getString(R.string.add_options),"5")

        addOptionButton?.setOnClickListener(View.OnClickListener { _ ->
            if (inputTextCount!! < 5) {
                if (inputTextCount == 4) addOptionButton?.background = resources.getDrawable(R.drawable.selected_item)
                inputTextCount = inputTextCount?.plus(1)
                MainActivity.mutableStringList = inputTextCount?.let { MutableList<String?>(it) { null } }!!
                addOptionText?.text = String.format(getString(R.string.add_options),(5 - inputTextCount!!).toString())
                inputTextCount?.let { recyclerViewChanges(it) }
            }
        })

        createButton?.setOnClickListener(View.OnClickListener { _ ->

            val db = context?.let {
                Room.databaseBuilder(
                    it,
                    AppDatabase::class.java, "dataw"
                ).build()
            }
            try {
                GlobalScope.launch {
                    val userDao = db?.questionDao()
                    val users: List<Question>? = userDao?.getAll()
                    val gson = Gson()
                    val tempList= MainActivity.mutableStringList.remove(null)
                    Log.d("checkSize", "onCreateView: " + MainActivity.mutableStringList.size)
                    val question = questionEditText?.text.toString()
                    if (MainActivity.mutableStringList.filterNotNull().size > 1 && question.isNotEmpty()) {
                        val option = gson.toJson(MainActivity.mutableStringList)
                        if (users != null) {
                            if (users.isNotEmpty()) {
                                userDao.insertAll(Question(users[users.size - 1].uid + 1,question,option,false,0))
                                MainActivity.mutableStringList.clear()
                                MainActivity.mutableStringList = MutableList<String?>(0) { null }
                            }
                        } else {
                            userDao?.insertAll(Question(0,question,option,false,0))
                            MainActivity.mutableStringList.clear()
                            MainActivity.mutableStringList = MutableList<String?>(0) { null }
                        }
                        showToast("Poll created successfully!")
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragmentContainerView, CurrentPolls())
                            ?.commit()
                    } else {
                        showToast("please enter a valid question and at least two option")
                    }
                }

            }catch (ex:Exception){
                Log.d("checkChanges", "onCreate: " + ex.message)
            }
        })

        return view
    }

    fun showToast(st:String) {
        GlobalScope.launch(Dispatchers.Main) {
            Toast.makeText(activity?.applicationContext, st, Toast.LENGTH_LONG).show()
        }
    }

    fun recyclerViewChanges(editTextCount:Int) {
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerviewAdapter = context?.let { RecyclerviewAdapter(it,MainActivity.mutableStringList) }
        recyclerView?.adapter = recyclerviewAdapter
    }
}