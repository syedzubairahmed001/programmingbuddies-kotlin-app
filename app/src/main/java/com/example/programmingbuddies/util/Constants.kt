package com.example.programmingbuddies.util

import com.example.programmingbuddies.R
import com.example.programmingbuddies.models.OnBoardingItem


enum class ErrorType {
    NO_INTERNET, UNKNOWN
}

val onBoardingList = listOf(
    OnBoardingItem(
        id = 0,
        anim = R.raw.welcome,
        title = "Welcome",
        subtitle = "We hope you have a great time"
    ),
    OnBoardingItem(
        id = 1,
        anim = R.raw.code,
        title = "Find partners",//TODO - Or Buddies???
        subtitle = "A chance to connect with other programmers, to team up with and learn together, and a lot more"
    ),
    OnBoardingItem(
        id = 2,
        anim = R.raw.mentor,
        title = "Get mentored",
        subtitle = "Get a chance to get mentored by experienced developers"
    ),
    OnBoardingItem(
        id = 3,
        anim = R.raw.blogs,
        title = "UpSkill Yourselves",
        subtitle = "Get access to various blogs, articles and roadmaps to upskill yourselves"
    )
)
