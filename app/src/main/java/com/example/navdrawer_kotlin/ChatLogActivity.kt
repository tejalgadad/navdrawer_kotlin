package com.example.navdrawer_kotlin

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.navdrawer_kotlin.R

import com.example.navdrawer_kotlin.model.User
import com.example.navdrawer_kotlin.model.ChatMessage

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatLogActivity : AppCompatActivity() {
    companion object {
        val TAG = "ChatLog"
    }
    val adapter = GroupAdapter<ViewHolder>()
    private lateinit var database : DatabaseReference
    private lateinit var databaseN : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        recyclerview_chat_log.adapter = adapter

        val user = intent.getParcelableExtra<User>(CommunityActivity.USER_KEY)

        if (user != null) {
            supportActionBar?.title = user.username
        }

        listenForMessages()

        send_button_chat_log.setOnClickListener {
            Log.d(TAG, "Attempt to send message....")
            performSendMessage()
        }
    }
    private fun listenForMessages() {
        val ref = FirebaseDatabase.getInstance("https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("messages")

        ref.addChildEventListener(object: ChildEventListener {
    override fun onChildAdded(p0: DataSnapshot, p1: String?) {
        val chatMessage = p0.getValue(ChatMessage::class.java)

        if (chatMessage != null) {
            Log.d(TAG, chatMessage.text)

            if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                adapter.add(ChatFromItem(chatMessage.text))
            } else {
                adapter.add(ChatToItem(chatMessage.text))
            }
        }

    }

    override fun onCancelled(p0: DatabaseError) {

    }

    override fun onChildChanged(p0: DataSnapshot, p1: String?) {

    }

    override fun onChildMoved(p0: DataSnapshot, p1: String?) {

    }

    override fun onChildRemoved(p0: DataSnapshot) {

    }

})

}

    private fun performSendMessage() {
        // how do we actually send a message to firebase...
        val text = edittext_chat_log.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(CommunityActivity.USER_KEY)

        database = FirebaseDatabase.getInstance(
            "https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app"
        ).getReference("TOID")
        var toId= ""
        database.child("value").get().addOnSuccessListener {
            if (it.value != null) {
                toId = it.value.toString()
            }
        }



            if (fromId == null) return

            val reference =
                FirebaseDatabase.getInstance("https://womansafety-336317-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .getReference("messages").push()

            val chatMessage =
                ChatMessage(reference.key!!, text, fromId, toId, System.currentTimeMillis() / 1000)
            reference.setValue(chatMessage)
                .addOnSuccessListener {
                    Log.d(TAG, "Saved our chat message: ${reference.key}")
                }

    }
    private fun setUpDummyData(){
        val adapter = GroupAdapter<ViewHolder>()
//
        adapter.add(ChatFromItem("FROM MESSSSSSSSAAGE"))
        adapter.add(ChatToItem("TO MESSAGE\nTOMESSAGE"))
        adapter.add(ChatFromItem("FROM MESSSSSSSSAAGE"))
        adapter.add(ChatToItem("TO MESSAGE\nTOMESSAGE"))
        adapter.add(ChatFromItem("FROM MESSSSSSSSAAGE"))
        adapter.add(ChatToItem("TO MESSAGE\nTOMESSAGE"))



        recyclerview_chat_log.adapter = adapter
    }
}

class ChatFromItem(val text: String): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textview_from_row.text = text

    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textview_to_row.text = text

    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}

