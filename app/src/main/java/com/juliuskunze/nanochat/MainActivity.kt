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
    data class ChatMessage(val name: String = "", val text: String = "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Firebase.setAndroidContext(this)

        val ref = Firebase("https://topsphere.firebaseio.com/nanochat/")

        send_button.setOnClickListener {
            ref.push().setValue(ChatMessage(
                    name="Android User",
                    text = text_edit.text.toString()
            ))
            text_edit.setText("")
        }

        listView.adapter = object : FirebaseListAdapter<ChatMessage>(this, ChatMessage::class.java,
                android.R.layout.two_line_list_item, ref) {
            override fun populateView(v: View, model: ChatMessage, position: Int) {
                v.text1.text = model.name
                v.text2.text = model.text
            }
        }
    }

    val listView: ListView get() = findViewById(android.R.id.list) as ListView
    val View.text1: TextView get() = findViewById(android.R.id.text1) as TextView
    val View.text2: TextView get() = findViewById(android.R.id.text2) as TextView
}