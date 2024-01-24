package com.example.contactify.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactify.DisplayActivity
import com.example.contactify.R
import com.example.contactify.data.Data
import com.squareup.picasso.Picasso


class UsersListAdapter(val activity: Activity) :
    RecyclerView.Adapter<UsersListAdapter.MyViewHolder>() {

    private var UsersList: ArrayList<Data>? = null


    fun setUsersList(usersList: ArrayList<Data>?) {
        this.UsersList = usersList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(UsersList?.get(position)!!, activity)
    }

    override fun getItemCount(): Int {
        if (UsersList == null) return 0
        else return UsersList?.size!!
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val userId = view.findViewById<TextView>(R.id.tv_userId)
        val displayPic = view.findViewById<ImageView>(R.id.iv_displayPic)
        val outerContainer = view.findViewById<CardView>(R.id.cv_outerContainer)


        fun bind(data: Data, activity: Activity) {
            data.let {
                val userName = "${data.first_name} ${data.last_name}"
                tvName.text = userName
                userId.text = data.email.toString()

                Picasso.get()
                    .load(data.avatar)
                    .into(displayPic)

                outerContainer.setOnClickListener {
                    Log.d("POSITION", data.toString())
                    val displayActivityIntent = Intent(activity, DisplayActivity::class.java)
                    displayActivityIntent.putExtra("userData", data)
                    activity.startActivity(displayActivityIntent)
                }
            }

        }
    }
}