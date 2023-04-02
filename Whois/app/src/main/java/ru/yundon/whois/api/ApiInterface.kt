package ru.yundon.whois.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("whoisserver/WhoisService?apiKey=at_bBBfpmmO4v60Ohvk1UCclWbDLyH9N&outputFormat=JSON")
    suspend fun whoisInformationList(@Query("domainName") domain: String?): Response<ResponseBody>
}