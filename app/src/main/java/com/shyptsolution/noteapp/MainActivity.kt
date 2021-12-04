package com.shyptsolution.noteapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Adapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import java.util.*

class MainActivity: AppCompatActivity() {
    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter=NoteAdapter(this,DataService.listOfNotes)
        var noteView=findViewById<ListView>(R.id.listView)
        noteView.adapter=adapter



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
                    var intent = Intent(this, Login::class.java)
                    this.startActivity(intent)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }



//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.main_menu, menu)
//        val sv=menu!!.findItem(R.id.app_bar_search).actionView as SearchView
//        val sm=getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
//        return true
//    }
}