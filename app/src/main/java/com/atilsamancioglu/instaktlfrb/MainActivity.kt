package com.atilsamancioglu.instaktlfrb

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mAuth = FirebaseAuth.getInstance()

        mAuthListener = FirebaseAuth.AuthStateListener { }


    }


    fun signIn(view: View) {


        mAuth!!.signInWithEmailAndPassword(usernameText.text.toString(), passwordText.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(applicationContext, FeedActivity::class.java)
                        startActivity(intent)
                    }
                }.addOnFailureListener(this) { e -> Toast.makeText(applicationContext, e.localizedMessage.toString(), Toast.LENGTH_LONG).show() }


    }

    fun signUp(view: View) {


        mAuth!!.createUserWithEmailAndPassword(usernameText.text.toString(), passwordText.getText().toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "User Created", Toast.LENGTH_LONG).show()
                        val intent = Intent(applicationContext, FeedActivity::class.java)
                        startActivity(intent)
                    }
                }.addOnFailureListener(this) { e ->
            if (e != null) {
                Toast.makeText(applicationContext, e.localizedMessage.toString(), Toast.LENGTH_LONG).show()
            }
        }


    }


}
