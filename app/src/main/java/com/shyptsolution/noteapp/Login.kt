package com.shyptsolution.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth= FirebaseAuth.getInstance()
        var registerbutt=findViewById<Button>(R.id.register)
        registerbutt.setOnClickListener {
            startActivity(Intent(this,register::class.java))
        }
        var login =findViewById<Button>(R.id.login)
        login.setOnClickListener {
            val email=findViewById<EditText>(R.id.emailid)
            var password=findViewById<EditText>(R.id.password)
            if(email.text.toString().isEmpty()){
                email.error="Email Required"
                email.requestFocus()
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
                email.error="Email Not Valid"
                email.requestFocus()
            }
            if(password.text.toString().isEmpty() || password.text.toString().length<8){
                password.error="8 Character Password Required"
                password.requestFocus()
            }
            loginUser(email.text.toString(),password.text.toString())

        }
    }

    private fun loginUser(email: String, password: String) {
        var progress=findViewById<ProgressBar>(R.id.progressbar)
        progress.visibility= View.VISIBLE
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
            task->
            progress.visibility= View.GONE
            if(task.isSuccessful){
               login()
            }
            else{
                task.exception?.message?.let {
                    toast(it)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth.currentUser?.let {
            login()
        }
    }
}