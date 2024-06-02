package com.cvelez.freemarkettest

object Constants {
    private const val SITE_ID = "MCO"
    const val BASE_URL = "https://api.mercadolibre.com/"
    const val SEARCH_URL = "${BASE_URL}sites/$SITE_ID/search"
    const val DETAILS_URL = "${BASE_URL}items/{id}"
}