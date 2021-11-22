package com.example.programmingbuddies.ui.home.cards

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.programmingbuddies.R
import com.example.programmingbuddies.adapter.CardsAdapter
import com.example.programmingbuddies.databinding.ActivityAuthBinding
import com.example.programmingbuddies.models.User
import com.example.programmingbuddies.util.viewBinding
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsFragment : Fragment(), CardStackListener {


    private val adapter = CardsAdapter()
    private lateinit var layoutManager: CardStackLayoutManager
    private lateinit var stackView: CardStackView
    private val viewModel by viewModels<CardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("InCard", "onViewCreated: ")

        stackView = view.findViewById(R.id.stack_view)
        layoutManager = CardStackLayoutManager(activity).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }

        stackView.layoutManager = layoutManager
        stackView.adapter = adapter
        stackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }


        viewModel.usersList.observe(viewLifecycleOwner){
            Log.d("InCard", "fetchData: " + it)
            adapter.setUsers(it)
            Log.d("InCard", "fetchData: " + adapter.usersList)
        }

//        fetchData()

    }

    private fun fetchData() {
        var userList: MutableList<User> = mutableListOf(
                User(
                    name = "Hey1",
                    profileProgram = "printf('hello world 1')",
                    language = "javascript"
                ),
                User(
                    name = "Hey2",
                    profileProgram = "printf('hello world 2')",
                    language = "javascript"
                ),
                User(
                    name = "Hey3",
                    profileProgram = "printf('hello world 3')",
                    language = "javascript"
                ),
                User(
                    name = "Hey4",
                    profileProgram = "printf('hello world 4')",
                    language = "javascript"
                ),
                User(
                    name = "Hey5",
                    profileProgram = "printf('hello world 5')",
                    language = "javascript"
                )
        )
        Log.d("InCard", "fetchData: " + userList)
        adapter.setUsers(userList)
        Log.d("InCard", "fetchData: " + adapter.usersList)
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {
        val position = layoutManager.topPosition - 1
        viewModel.deleteUser()
    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardDisappeared(view: View?, position: Int) {

    }


}