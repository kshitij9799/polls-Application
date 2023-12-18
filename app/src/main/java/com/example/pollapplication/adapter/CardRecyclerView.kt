package com.example.pollapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pollapplication.Question
import com.example.pollapplication.R
import java.util.*


class CardRecyclerView(private val context: Context, transactionList: List<Question>?) :
    RecyclerView.Adapter<CardRecyclerView.viewHolder>() {
    private var transactionList:List<Question>?


    init {
        this.transactionList = transactionList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.data_show_view, parent, false)

        return viewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: viewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.questionCardText?.text = transactionList?.get(position)?.question
        val temp = transactionList?.get(position)?.answer
        temp?.split(",")?.forEachIndexed { index, item ->
            when (index) {
                1 -> holder.optionFirst?.visibility = View.VISIBLE
                2 -> holder.optionSecond?.visibility = View.VISIBLE
                3 -> holder.optionThird?.visibility = View.VISIBLE
                4 -> holder.optionFourth?.visibility = View.VISIBLE
                5 -> holder.optionFifth?.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return transactionList?.size!!
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var questionCardText: TextView? = null
        var optionFirst: LinearLayout? = null
        var optionSecond: LinearLayout? = null
        var optionThird: LinearLayout? = null
        var optionFourth: LinearLayout? = null
        var optionFifth: LinearLayout? = null

        init {
            itemView.setOnClickListener(this)
            questionCardText = itemView.findViewById(R.id.question_card_text)
            optionFirst = itemView.findViewById(R.id.option_first)
            optionSecond = itemView.findViewById(R.id.option_second)
            optionThird = itemView.findViewById(R.id.option_third)
            optionFourth = itemView.findViewById(R.id.option_fourth)
            optionFifth = itemView.findViewById(R.id.option_fifth)
        }

        override fun onClick(view: View) {}
    }
}