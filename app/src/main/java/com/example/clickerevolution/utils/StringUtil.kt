package com.example.clickerevolution.utils

object StringUtil {

    fun addCommaEveryThreeDigits(input: Int): String {
        val reversed = input.toString().reversed()
        val result = StringBuilder()

        for (i in reversed.indices) {
            if (i > 0 && i % 3 == 0) {
                result.append(",")
            }
            result.append(reversed[i])
        }

        return result.reverse().toString()
    }

}