package com.shyptsolution.noteapp

import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.security.MessageDigest

fun Context.toast(message:String)=
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()

fun Context.login(){
    val intent= Intent(this,MainActivity::class.java).apply{
        flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}