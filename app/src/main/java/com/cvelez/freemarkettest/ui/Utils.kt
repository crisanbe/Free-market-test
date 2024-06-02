package com.cvelez.freemarkettest.ui

object Utils {

    fun formatPrice(price:Long): String {
        val amountString = price.toString()
        val length = amountString.length
        val result = StringBuilder()

        var count = 0

        // Iterate over the amountString in reverse
        for (i in length - 1 downTo 0) {
            result.insert(0, amountString[i])
            count++
            // Add a period after every third digit
            if (count % 3 == 0 && i != 0) {
                result.insert(0, ".")
            }
        }

        return "$ ${result.toString()}"
    }
}