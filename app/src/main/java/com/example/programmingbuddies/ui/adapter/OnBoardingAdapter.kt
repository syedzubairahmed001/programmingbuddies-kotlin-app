package com.example.programmingbuddies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.programmingbuddies.databinding.ActivityOnBoardingBinding
import com.example.programmingbuddies.databinding.OnBoardingAnimLayoutBinding
import com.example.programmingbuddies.models.OnBoardingItem

class OnBoardingAdapter(private val onBoardingList: List<OnBoardingItem>) :
    RecyclerView.Adapter<OnBoardingAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val binding: OnBoardingAnimLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: OnBoardingItem) {
            binding.lottieOnBoardingItem.setAnimation(data.anim)
            binding.lottieOnBoardingItem.playAnimation()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            OnBoardingAnimLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val onboarding = onBoardingList[position]
        holder.bind(onboarding)
    }

    override fun getItemCount(): Int {
        return onBoardingList.size
    }

}