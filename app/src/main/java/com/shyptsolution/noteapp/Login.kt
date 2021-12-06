package com.shyptsolution.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Login : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    private lateinit var googleAuth:GoogleSignInClient
     val Raunit=25

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
//            loginUser(email.text.toString(),password.text.toString())
            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.clientID))
                .requestProfile()
                .build()

            googleAuth=GoogleSignIn.getClient(this,gso)
        }

        // Configure Google Sign In

    }

//    private fun loginUser(email: String, password: String) {
//        var progress=findViewById<ProgressBar>(R.id.progressbar)
//        progress.visibility= View.VISIBLE
//        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
//            task->
//            progress.visibility= View.GONE
//            if(task.isSuccessful){
//               login()
//            }
//            else{
//                task.exception?.message?.let {
//                    toast(it)
//                }
//            }
//        }
//    }

//    override fun onStart() {
//        super.onStart()
//        mAuth.currentUser?.let {
//            login()
//        }
//    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun isUserSignedIn(): Boolean {

        val account = GoogleSignIn.getLastSignedInAccount(this)
        return account != null

    }
    private fun GoogleSignIn() {

        if (!isUserSignedIn()) {


            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, CONST_SIGN_IN)
        }

    }
}