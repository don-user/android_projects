package ru.yundon.whois.api

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.yundon.whois.data.SaveFileWhoisDataInformation
import ru.yundon.whois.utils.Constants

class ResponseApiServer(private val context: Context) {

    private val saveFileWhoisDataInformation = SaveFileWhoisDataInformation(context)

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun getWhoisDataFromApi(url: String?): String {

        var jsonObject = JSONObject()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiInterface::class.java)
        val response = service.whoisInformationList(url)
        withContext(Dispatchers.Main) {
            try {
                jsonObject = JSONObject(response.body()!!.string())
            } catch (e: Exception) {
                Toast.makeText(context, Constants.ERROR_KEY, Toast.LENGTH_LONG).show()
            }
        }
        return checkAndSaveJsonObject(jsonObject)
    }

    private fun checkAndSaveJsonObject(jsonObject: JSONObject): String {
        var domainName = ""
        try {
            if (jsonObject.getJSONObject("WhoisRecord")
                    .getString("dataError") == Constants.DATA_ERROR_MESSAGE
            ) {
                domainName = ""
            }
        } catch (e: Exception) {
            try {
                domainName = jsonObject.getJSONObject("WhoisRecord").getString("domainName")
                saveFileWhoisDataInformation.saveWhoisDataList(jsonObject)
                saveFileWhoisDataInformation.saveResultForRecyclerView(jsonObject)
            } catch (e: Exception) {
                domainName = ""
            }
        }
        return domainName
    }
}
