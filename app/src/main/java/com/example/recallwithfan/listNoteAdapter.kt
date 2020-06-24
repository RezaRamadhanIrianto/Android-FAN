package com.example.recallwithfan


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.list_note_row.view.*


class ListNoteAdapter(private val listNote: ArrayList<Note>) : RecyclerView.Adapter<ListNoteAdapter.ListViewHolder>(){

    private var onItemClickCallback: OnItemClickCallback ?= null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_note_row, parent, false)
        return ListViewHolder(view)

    }

    override fun getItemCount(): Int = listNote.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listNote[position])
    }
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note : Note){
            Log.d("Show", note.toString())
            with(itemView){
                tTitle.text = note.title
                tDesc.text = note.desc

                itemView.setOnClickListener{onItemClickCallback?.onItemClicked(note)}
            }
        }
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: Note)
    }

}