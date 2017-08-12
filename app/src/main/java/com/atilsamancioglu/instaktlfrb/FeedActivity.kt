package com.atilsamancioglu.instaktlfrb

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_feed.*
import java.util.ArrayList
import java.util.HashMap

open class FeedActivity : AppCompatActivity() {


    internal var useremailsFromFB: ArrayList<String> = ArrayList<String>()
    internal var userimageFromFB: ArrayList<String> = ArrayList<String>()
    internal var usercommentFromFB: ArrayList<String> = ArrayList<String>()
    internal var firebaseDatabase: FirebaseDatabase? = null
    internal var myRef: DatabaseReference? = null
    internal var adapter: PostClass? = null

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_post, menu)

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_post) {

            val intent = Intent(applicationContext, UploadActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        useremailsFromFB = ArrayList<String>()
        usercommentFromFB = ArrayList<String>()
        userimageFromFB = ArrayList<String>()

        firebaseDatabase = FirebaseDatabase.getInstance()
        myRef = firebaseDatabase!!.getReference()


        adapter = PostClass(useremailsFromFB, userimageFromFB, usercommentFromFB, this)

        listView.adapter = adapter

        getDataFromFirebase()



    }


    protected fun getDataFromFirebase() {

        val newReference = firebaseDatabase!!.getReference("Posts")



        newReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                println("chiledren " + dataSnapshot.children)
                println("key" + dataSnapshot.key)
                println("value" + dataSnapshot.value)

                //System.out.println("children" + dataSnapshot.getChildren());
                //System.out.println("key" + dataSnapshot.getKey());
                //System.out.println("value" + dataSnapshot.getValue());

                adapter!!.clear()
                usercommentFromFB.clear()
                userimageFromFB.clear()
                usercommentFromFB.clear()

                for (ds in dataSnapshot.children) {

                    val hashMap = ds.value as HashMap<String, String>
                    if (hashMap.size > 0) {
                        println(hashMap)
                        val email = hashMap["useremail"]
                        if (email != null) {
                            useremailsFromFB.add(email)
                        }
                        val comment = hashMap["comment"]
                        if (comment!=null) {
                            usercommentFromFB.add(comment)
                        }
                        val image = hashMap["downloadurl"]
                        if (image != null) {
                            userimageFromFB.add(image)
                        }
                        adapter!!.notifyDataSetChanged()

                    }

                }

            }



            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

}
