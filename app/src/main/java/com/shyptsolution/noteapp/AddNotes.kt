package com.shyptsolution.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class AddNotes : AppCompatActivity() {
    var mAuth=FirebaseAuth.getInstance()
    var db=FirebaseFirestore.getInstance()
    lateinit var noteTi:EditText
    lateinit var noteD:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        noteD=findViewById<EditText>(R.id.notedes)
        noteTi=findViewById<EditText>(R.id.notetitle)
//        var Notes=findViewById<Button>(R.id.addnotes)
//        Notes.setOnClickListener {
//            addNotes()
//
//        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_notes_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item!=null){
            when(item.itemId){
                R.id.addnotes ->
                {
                    addNotes()
                }
                R.id.cancel ->{
                    startActivity(Intent(this,MainActivity::class.java))
                }
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun addNotes() {
        var currentUser = mAuth.currentUser?.email
//        Toast.makeText(this,currentUser,Toast.LENGTH_LONG).show()
        // Create a new user with a first, middle, and last name
        var time =
            (Calendar.HOUR.toString() + Calendar.MINUTE.toString() + Calendar.SECOND.toString() + Calendar.MILLISECOND.toString()).toString()
        var noteDes = noteD.text.toString()
        var noteTitle = noteTi.text.toString()
        if (noteDes.isEmpty() && noteTitle.isEmpty()) {
            if (noteDes.isEmpty()) {
                noteD.error = "Enter Description"
                noteD.requestFocus()
            } else {
                noteTi.error = "Enter Title"
                noteTi.requestFocus()
            }

        } else {
            if(noteTitle.isEmpty()){
                var deslen=noteDes.length.toInt()
                if(deslen<25){
                    noteTitle=noteDes.get(deslen).toString()
                }
                else{
                    noteTitle=noteDes.take(25)
                }
            }

            val user = hashMapOf(
                "noteId" to time,
                "noteDes" to noteDes,
                "noteName" to noteTitle,

                )

// Add a new document with a generated ID

            db.collection(currentUser.toString())
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d("he", "DocumentSnapshot added with ID: ${documentReference}")
//                    Toast.makeText(this,"${documentReference}",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
            MainActivity().adapter.notifyDataSetChanged()
            startActivity(Intent(this,MainActivity::class.java))
        }

    }


}