package com.shyptsolution.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AddNotes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

    }
    fun butAdd(view: View){
        finish()
    }


}

