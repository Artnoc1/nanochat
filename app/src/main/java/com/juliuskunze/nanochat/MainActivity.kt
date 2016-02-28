package com.juliuskunze.nanochat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.firebase.client.Firebase
import com.firebase.ui.FirebaseListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Firebase.setAndroidContext(this)

        val ref = Firebase("https://topsphere.firebaseio.com/")

        send_button.setOnClickListener {
            ref.push().setValue(hashMapOf(
                    "name" to "Android User",
                    "text" to text_edit.text.toString()
            ))
            text_edit.setText("")
        }

        val listView = findViewById(android.R.id.list) as ListView
        listView.adapter = object : FirebaseListAdapter<ChatMessage>(this, ChatMessage::class.java,
                android.R.layout.two_line_list_item, ref) {
            override fun populateView(v: View, model: ChatMessage, position: Int) {
                (v.findViewById(android.R.id.text1) as TextView).text = model.name
                (v.findViewById(android.R.id.text2) as TextView).text = model.text
            }
        }
    }

    data class ChatMessage(val name: String = "", val text: String = "")
}