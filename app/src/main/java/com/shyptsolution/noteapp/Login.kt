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
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider



class Login : AppCompatActivity() {
    companion object {
        const val CONST_SIGN_IN =25
    }
    private lateinit var mAuth:FirebaseAuth
    private lateinit var googleAuth:GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth= FirebaseAuth.getInstance()
        var registerbutt=findViewById<Button>(R.id.register)
        registerbutt.setOnClickListener {
            startActivity(Intent(this,register::class.java))
        }
        var login =findViewById<Button>(R.id.logingoogle)
        var loginbutt=findViewById<Button>(R.id.login)
        loginbutt.setOnClickListener {
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
        login.setOnClickListener {
            GoogleSignIn()
        }
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.clientID))
            .requestProfile()
            .build()

        googleAuth=GoogleSignIn.getClient(this,gso)
        // Configure Google Sign In

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

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = mAuth.currentUser
//                    Toast.makeText(this,"Signed IN",Toast.LENGTH_LONG).show()
                   startActivity(Intent(this,MainActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    startActivity(Intent(this,register::class.java))
                }
            }
    }

    private fun isUserSignedIn(): Boolean {

        val account = GoogleSignIn.getLastSignedInAccount(this)
//        Toast.makeText(this,"Is UserSigned IN ${account!=null}",Toast.LENGTH_LONG).show()

        return account != null

    }
    private fun GoogleSignIn() {

        if (!isUserSignedIn()) {
//            Toast.makeText(this,"Signed IN Process",Toast.LENGTH_LONG).show()

            val signInIntent = googleAuth.signInIntent
            startActivityForResult(signInIntent, CONST_SIGN_IN)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== CONST_SIGN_IN){
            val task=GoogleSignIn.getSignedInAccountFromIntent(data)
//            Toast.makeText(this,"request code= const sign in",Toast.LENGTH_LONG).show()

            try {
                val account=task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken)
//                Toast.makeText(this,"Inside TRY",Toast.LENGTH_LONG).show()

            }
            catch (e:ApiException){
//                Toast.makeText(this,"Exception caught ${e}",Toast.LENGTH_LONG).show()
                Toast.makeText(this,"Exception caught ${e}",Toast.LENGTH_LONG).show()
                Log.d("EXception","Exception Caught",e)
            }

        }
    }


}