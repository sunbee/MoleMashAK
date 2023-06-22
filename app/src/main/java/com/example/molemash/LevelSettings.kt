package com.example.molemash

import android.content.Context

class LevelSettings(private val context: Context) {
    public var currentLevel: Int = 1
    private val settingsMap = mapOf(
        1 to mapOf("moles" to 5, "speed" to 500),
        2 to mapOf("moles" to 10, "speed" to 1000),
        3 to mapOf("moles" to 15, "speed" to 1500)
    )

    fun getMolesCount(level: Int): Int {
        return settingsMap[level]?.get("moles") ?: 0
    }

    fun getMoleSpeed(level: Int): Int {
        return settingsMap[level]?.get("speed") ?: 0
    }
}
