package com.example.pollapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.pollapplication.MainActivity
import com.example.pollapplication.R
import com.example.pollapplication.fragments.CreatePollFragment
import kotlin.Int

class RecyclerviewAdapter(private val context: Context, transactionList: MutableList<String?>) :
    RecyclerView.Adapter<RecyclerviewAdapter.viewHolder>() {
    private var transactionList: MutableList<String?>
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

//            notifyItemRemoved(position)
            notifyItemChanged(position)

            CreatePollFragment.inputTextCount = CreatePollFragment.inputTextCount?.minus(1)
            MainActivity.mutableStringList = CreatePollFragment.inputTextCount?.let { MutableList<String?>(it) { null } }!!
            CreatePollFragment.addOptionText?.text = String.format(
                holder.itemView.context.getString(R.string.add_options),
                (5 - CreatePollFragment.inputTextCount!!).toString()
            )

            CreatePollFragment.addOptionButton?.background =
                holder.itemView.context.getDrawable(R.drawable.button_effect)

            CreatePollFragment.recyclerView?.adapter =
                CreatePollFragment.inputTextCount?.let { it1 -> RecyclerviewAdapter(context, MainActivity.mutableStringList) }
        })

        holder.editTextInput?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do something before text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This is called whenever text changes
                // Access the changed text via 's'
                val newText = s.toString()
                MainActivity.mutableStringList[position] = newText
                for (value in MainActivity.mutableStringList) Log.d("checkText", "onTextChanged: ${value.toString()}")

                Log.d("checkText", "---------------------------------------------------------------")
                // Perform actions with newText as needed
            }

            override fun afterTextChanged(s: Editable?) {
                // Do something after text changes
            }
        })


    }

    override fun getItemCount(): Int {
        return transactionList.size
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