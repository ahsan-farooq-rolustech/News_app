package com.example.seriousnewsapp.utils

object AppConstants
{
    val countrySpinnerList= mutableListOf<String>()
    const val BASE_URL="https://newsapi.org"
    const val API_KEY="3a9c5458249541c795c94dd76ae419cb"
    const val ERROR_ENTER_KEYWORD="Enter Keyword"

    init
    {
        populateCountriesList()

    }

    private fun populateCountriesList()
    {
        countrySpinnerList.add("ae")
        countrySpinnerList.add("ar")
        countrySpinnerList.add("at")
        countrySpinnerList.add("au")
        countrySpinnerList.add("be")
        countrySpinnerList.add("bg")
        countrySpinnerList.add("br")
        countrySpinnerList.add("ca")
        countrySpinnerList.add("ch")
        countrySpinnerList.add("cn")
        countrySpinnerList.add("co")
        countrySpinnerList.add("cu")
        countrySpinnerList.add("cz")
        countrySpinnerList.add("de")
        countrySpinnerList.add("eg")
        countrySpinnerList.add("fr")
        countrySpinnerList.add("gb")
        countrySpinnerList.add("gr")
        countrySpinnerList.add("hk")
        countrySpinnerList.add("hu")
        countrySpinnerList.add("id")
        countrySpinnerList.add("ie")
        countrySpinnerList.add("il")
        countrySpinnerList.add("in")
        countrySpinnerList.add("it")
        countrySpinnerList.add("jp")
        countrySpinnerList.add("kr")
        countrySpinnerList.add("lt")
        countrySpinnerList.add("lv")
        countrySpinnerList.add("ma")
        countrySpinnerList.add("mx")
        countrySpinnerList.add("my")
        countrySpinnerList.add("ng")
        countrySpinnerList.add("nl")
        countrySpinnerList.add("no")
        countrySpinnerList.add("nz")
        countrySpinnerList.add("ph")
        countrySpinnerList.add("pl")
        countrySpinnerList.add("pt")
        countrySpinnerList.add("ro")
        countrySpinnerList.add("rs")
        countrySpinnerList.add("ru")
        countrySpinnerList.add("sa")
        countrySpinnerList.add("se")
        countrySpinnerList.add("sg")
        countrySpinnerList.add("si")
        countrySpinnerList.add("sk")
        countrySpinnerList.add("th")
        countrySpinnerList.add("tr")
        countrySpinnerList.add("tw")
        countrySpinnerList.add("ua")
        countrySpinnerList.add("us")
        countrySpinnerList.add("ve")
        countrySpinnerList.add("za")
    }
}

object CategoryConstants
{
    const val CATEGORY_BUSINESS="business"
    const val CATEGORY_ENTERTAINMENT="entertainment"
    const val CATEGORY_GENERAL="general"
    const val CATEGORY_HEALTH="health"
    const val CATEGORY_SCIENCE="science"
    const val CATEGORY_SPORTS="sports"
    const val CATEGORY_TECHNOLOGY="technology"

}