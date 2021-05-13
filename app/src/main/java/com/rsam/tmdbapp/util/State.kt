package com.rsam.tmdbapp.util

sealed class State {
    class Success<T>(val response: T) : State()
    class Failure(val error: Int) : State()
}