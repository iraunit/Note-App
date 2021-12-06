package com.shyptsolution.noteapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.service.autofill.Dataset
import android.text.Layout
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class MainActivity: AppCompatActivity() {
     var adapter: NoteAdapter=NoteAdapter(this,DataService.listOfNotes)
    var  db=FirebaseFirestore.getInstance()
    var mAuth=FirebaseAuth.getInstance()
    var userEmail=mAuth.currentUser?.email
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        adapter=NoteAdapter(this,DataService.listOfNotes)
        var noteView=findViewById<ListView>(R.id.listView)
        noteView.adapter=adapter
        EventChangeListener()



    }

     fun Delete(id:String) {

        db.collection(userEmail.toString()).document(id)
            .delete()
            .addOnSuccessListener { Log.d("Deleted", "DocumentSnapshot successfully deleted!")
                MainActivity().adapter.notifyDataSetChanged()
//            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
           }
            .addOnFailureListener { e -> Log.w("Delete Error", "Error deleting document", e) }
    }

    private fun EventChangeListener() {


    db.collection("Users").addSnapshotListener(object : EventListener<QuerySnapshot>{
        override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
            if(error!=null){
                Toast.makeText(this@MainActivity,"inside onevent",Toast.LENGTH_SHORT).show()
                Log.e("FireStore Error",error.message.toString())
                return
            }
//            for(dc:DocumentChange in value?.documentChanges!!){
//                Toast.makeText(this@MainActivity,"Inside document change",Toast.LENGTH_SHORT).show()
//                if(dc.type==DocumentChange.Type.ADDED) {
////                   userArrayList.add(dc.document.toObject(Note::class.java))
//                }
//                Toast.makeText(this@MainActivity,"hel",Toast.LENGTH_SHORT).show()
//            }
    var notetitle:String="noteName"
            var dessss:String="noteDes"
            var ID:String="noteId"

            db.collection(userEmail.toString())
                .get()
                .addOnSuccessListener { result ->

                    for (document in result) {

                        var title:String=document.getString(notetitle).toString()
                        var descri=document.getString(dessss).toString()
                        var noteid=document.getString(ID)?.toInt()
                        if(title!=null && descri!=null){
                            if(noteid!=null){
                                if(title!="" || descri!=""){
                                    if(searching(noteid)){
                                        DataService.listOfNotes.add(Note(noteid.toInt(),title,descri))

                                    }
                                    }


                            }
                        }



                    }
                    adapter.notifyDataSetChanged()

                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this@MainActivity,"Check Internet Connection",Toast.LENGTH_SHORT).show()
                }
//            adapter.notifyDataSetChanged()
        }

    })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater=menuInflater
//        val searchview=menu?.findItem(R.id.app_bar_search)?.actionView as SearchView
//        var searchmenu=getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        searchview.setSearchableInfo(searchmenu.getSearchableInfo(componentName))
//        searchview.setOnQueryTextListener(  object:SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                Toast.makeText(applicationContext,query,Toast.LENGTH_LONG).show()
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//        })

//        menu=findViewById<Menu>(R.menu.main_menu)
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item!=null){
            when(item.itemId){
                R.id.addNote-> {
                    var intent = Intent(this, AddNotes::class.java)
                    this.startActivity(intent)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun searching(id: Int):Boolean{
        for (list in DataService.listOfNotes){
            if(list.noteId==id){
                return false
            }
        }
        return true
    }

}