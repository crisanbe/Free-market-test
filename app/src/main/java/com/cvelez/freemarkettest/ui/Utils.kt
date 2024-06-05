package com.cvelez.freemarkettest.ui

import java.text.DecimalFormat

object Utils {
    fun formatPrice(price: Long): String {
        val formatter = DecimalFormat("#,###")
        return "$ ${formatter.format(price)}"
    }
}