package com.pareminder.common

interface ScreenState {

    fun lading()
    fun completed()
    fun error(errorCode:State=State.ERROR,message: String)

}

enum class State{
    ERROR,
    NO_CONNECTIVITI
}