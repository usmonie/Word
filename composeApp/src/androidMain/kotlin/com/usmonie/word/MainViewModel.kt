package com.usmonie.word

import androidx.lifecycle.ViewModel
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainState(SubscriptionStatus.Purchased(null)))
    val state = _state.asStateFlow()
}
