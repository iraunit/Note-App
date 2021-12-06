package com.shyptsolution.noteapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class NoteAdapter(context: Context, listOfNotes:ArrayList<Note>):BaseAdapter(){
    var context=context
    var noteList=listOfNotes


    override fun getCount(): Int {
        return noteList.size
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var noteView:View
        noteView=LayoutInflater.from(context).inflate(R.layout.ticket,null)
        var note=noteView.findViewById<TextView>(R.id.name)
        var noteDes=noteView.findViewById<TextView>(R.id.description)
        note.setText(noteList[position].noteName)
        noteDes.setText(noteList[position].noteDes)


                return noteView
    }



}