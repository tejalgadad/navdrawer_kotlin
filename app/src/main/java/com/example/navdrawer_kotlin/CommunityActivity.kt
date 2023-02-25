package com.example.navdrawer_kotlin

import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

//import com.google.firebase.firestore.auth.User
//import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.example.navdrawer_kotlin.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.navdrawer_kotlin.R

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_community2.*
import kotlinx.android.synthetic.main.activity_community2.view.*
import kotlinx.android.synthetic.main.community_users.view.*


class CommunityActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private var key:String =""
    private var num:Long =0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community2)
        supportActionBar?.title = "Select User"
//        val adapter = GroupAdapter<ViewHolder>()
//        adapter.add(UserItem())
//                    rv_2.adapter = adapter

        fetchUsers()

    }
    companion object {
        val USER_KEY = "USER_KEY"
    }
    private fun fetchUsers() {
        key = Firebase.auth.uid.toString()



            val ref =
                FirebaseDatabase.getInstance("https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .getReference("User")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(p0: DataSnapshot) {
                    val adapter = GroupAdapter<ViewHolder>()
//
                    p0.children.forEach {
                        Log.d("NewMessage", it.toString())
                        val user = it.getValue(User::class.java)
                        if (user != null) {
                            adapter.add(UserItem(user ))
//                            Log.i("database", user.toString())
                        }
                    }
                    adapter.setOnItemClickListener{item,view->
                        val userItem = item as UserItem
                        val intent = Intent(view.context, ChatLogActivity::class.java)
                        intent.putExtra(USER_KEY, userItem.user)

                        database = FirebaseDatabase.getInstance(
                            "https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app"
                        ).getReference("TOID")

                        database.child("value").setValue(userItem.user).addOnCompleteListener {

                        }.addOnFailureListener {
                        }


                        startActivity(intent)
                        finish()
                    }

                    rv_2.adapter = adapter
                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })

    }


}

class UserItem (val user: User):Item<ViewHolder>() {   //(val user: User)v
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.username_textview.text = user.username

//        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageview_new_message)
    }

    override fun getLayout(): Int {
        return R.layout.community_users
    }
}

// this is super tedious

//class CustomAdapter: RecyclerView.Adapter<ViewHolder> {
//  override fun onBindViewHolder(p0:, p1: Int) {
//    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//  }


