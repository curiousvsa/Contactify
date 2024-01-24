package com.example.contactify

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.contactify.data.Data
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class DisplayActivity : AppCompatActivity() {

    private lateinit var selectedUserData:Data
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        initaliseViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initaliseViews() {
        if (intent.hasExtra("userData")) {
            selectedUserData = intent.getSerializableExtra("userData") as Data

            selectedUserData.let { data ->
                val userPic = findViewById<CircleImageView>(R.id.iv_userPic)
                Picasso.get()
                    .load(data.avatar)
                    .into(userPic)


                val userName = findViewById<TextView>(R.id.tv_userName)
                val userEmail = findViewById<TextView>(R.id.tv_userEmail)
                val getInTouch = findViewById<TextView>(R.id.getInTouch)
                userName.text = "${data.first_name} ${data.last_name}"
                userEmail.text = "${data.email}"

                getInTouch.setOnClickListener {
                    openEmailIntent(data)
                }
            }
        }
    }

    private fun openEmailIntent(data: Data) {
        data.let {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("plain/text")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(data.email))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Connect Invite")
            intent.putExtra(Intent.EXTRA_TEXT, "Hi,${data.first_name}")
            startActivity(Intent.createChooser(intent, ""))
        }

    }
}