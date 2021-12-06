package com.shyptsolution.noteapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class NoteAdapter(context: Context, listOfNotes:ArrayList<Note>):BaseAdapter(){
    var context=context

    var noteList=listOfNotes
    var  db= FirebaseFirestore.getInstance()
    var mAuth= FirebaseAuth.getInstance()
    var userEmail=mAuth.currentUser?.email

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
        var delete=noteView.findViewById<ImageView>(R.id.delete)
        var id:String?=null
        delete.setOnClickListener {

            db.collection(userEmail.toString())
                .get()
                .addOnSuccessListener { result ->

                    for (document in result) {

                        var title:String=document.getString("noteName").toString()
                        var descri=document.getString("noteDes").toString()
                        var noteid=document.getString("noteId")?.toInt()
                        if(title==noteList[position].noteName && descri==noteList[position].noteDes && noteid==noteList[position].noteId){
//                            Toast.makeText(context,title+descri,Toast.LENGTH_LONG).show()
                            id=document.id
                            Toast.makeText(context,id.toString(),Toast.LENGTH_LONG).show()
                            MainActivity().Delete(id.toString())
                            MainActivity().adapter.notifyDataSetChanged()
                            break
                        }



                    }


                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_SHORT).show()
                }



        }

                return noteView
    }



}