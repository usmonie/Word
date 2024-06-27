package com.usmonie.compass.viewmodel

interface StateManager<in Event : ScreenEvent, State : ScreenState> {
	fun reduce(state: State, event: Event): State
}
