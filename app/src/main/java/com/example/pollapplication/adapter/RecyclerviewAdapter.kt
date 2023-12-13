package com.example.pollapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.pollapplication.R
import com.example.pollapplication.fragments.CreatePollFragment
import java.security.AccessController.getContext
import kotlin.Int

class RecyclerviewAdapter(private val context: Context, transactionList: Int) :
    RecyclerView.Adapter<RecyclerviewAdapter.viewHolder>() {
    private var transactionList: Int
    private val thisAdapter = this
    private val createPollFragment:CreatePollFragment = CreatePollFragment()


    init {
        this.transactionList = transactionList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.option_edit_text_view, parent, false)
        return viewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: viewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.cancelButton?.setOnClickListener(View.OnClickListener {

            notifyItemRemoved(position)
            CreatePollFragment.inputTextCount = CreatePollFragment.inputTextCount?.minus(1)
            CreatePollFragment.addOptionText?.text = String.format(
                holder.itemView.context.getString(R.string.add_options),
                (5 - CreatePollFragment.inputTextCount!!).toString()
            )

            CreatePollFragment.addOptionButton?.background =
                holder.itemView.context.getDrawable(R.drawable.button_effect)

            CreatePollFragment.recyclerView?.adapter =
                CreatePollFragment.inputTextCount?.let { it1 -> RecyclerviewAdapter(context, it1) }
        })
    }

    override fun getItemCount(): Int {
        return transactionList
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var cancelButton:ImageView ?= null
        var editTextInput:EditText ?= null

        init {
            itemView.setOnClickListener(this)
            cancelButton = itemView.findViewById(R.id.cancel_button)
            editTextInput = itemView.findViewById(R.id.input_options)
        }

        override fun onClick(view: View) {}
    }
}