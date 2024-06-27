package com.usmonie.compass.viewmodel

interface EffectHandler<in Event : ScreenEvent, out Effect : ScreenEffect> {
	fun handle(event: Event): Effect?
}