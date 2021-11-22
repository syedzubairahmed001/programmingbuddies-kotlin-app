package com.example.programmingbuddies.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.programmingbuddies.R
import com.example.programmingbuddies.databinding.ItemCardBinding
import com.example.programmingbuddies.models.User

class CardsAdapter : RecyclerView.Adapter<CardsAdapter.ProfileViewHolder>() {

    class ProfileViewHolder(val binding: ItemCardBinding):RecyclerView.ViewHolder(binding.root) {

    }

    var usersList: List<User>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardsAdapter.ProfileViewHolder {
        return ProfileViewHolder(
            ItemCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    fun setUsers(users:List<User>){
        usersList = users
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CardsAdapter.ProfileViewHolder, position: Int) {
        val user = usersList?.get(position)
        holder.binding.apply {
//            profileImageCard = TODO- Do this
            Log.d("InCard", "onBindViewHolder: "+user)
            profileNameCard.text = user?.name
            languageCard.text = user?.language
            profileProgramCard.text = user?.profileProgram
        }
    }

    override fun getItemCount(): Int {
        return usersList?.size ?: 0
    }


}