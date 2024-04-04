package com.usmonie.word.features.onboarding.domain

interface UserRepository {

    fun loginViaGoogle(token: String)

}