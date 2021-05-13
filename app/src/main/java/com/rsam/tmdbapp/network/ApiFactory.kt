package com.rsam.tmdbapp.network

interface ApiFactory {
    fun <T> buildApi(type: Class<T>): T
}
