package com.shyptsolution.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ListView

class MainActivity: AppCompatActivity() {
    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter=NoteAdapter(this,DataService.listOfNotes)
        var noteView=findViewById<ListView>(R.id.listView)
        noteView.adapter=adapter



    }
}