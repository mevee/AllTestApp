package com.pareminder.common

sealed class ScreenState(private val state: State=State.INIT) {

    class Lading():ScreenState(State.LOADING)
    class Completed():ScreenState(State.COMPLETED)
    class Error():ScreenState(State.ERROR)

}

enum class State{
    INIT,
    LOADING,
    COMPLETED,
    ERROR
}