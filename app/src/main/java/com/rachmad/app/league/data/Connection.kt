package com.rachmad.app.league.data

enum class Connection(val Status: Int) {
    OK(200),
    ACCEPTED(202),
    ERROR(-1)
}