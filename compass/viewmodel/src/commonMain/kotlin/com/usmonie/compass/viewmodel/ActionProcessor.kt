package com.usmonie.compass.viewmodel

interface ActionProcessor<in Action : ScreenAction, in State : ScreenState, out Event : ScreenEvent> {
	suspend fun process(action: Action, state: State): Event
}