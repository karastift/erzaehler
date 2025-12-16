package com.karastift.erzaehler

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform