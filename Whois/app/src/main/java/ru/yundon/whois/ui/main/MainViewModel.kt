package ru.yundon.whois.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.yundon.whois.api.ResponseApiServer
import ru.yundon.whois.api.WhoisDataMain
import ru.yundon.whois.utils.Constants
import ru.yundon.whois.utils.FileSaveAndGet

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val liveDataWhois = MutableLiveData<MutableList<WhoisDataMain>>()
    private val responseApiServer = ResponseApiServer(getApplication())


    fun listWhoisDataForRecyclerView() {
        val listForRecycler = mutableListOf<WhoisDataMain>()

        val listString =
            FileSaveAndGet(getApplication()).getWhoisFile(Constants.FILE_NAME).split("|")
                .toMutableList()
        if (listString.size > 1) {
            listString.removeAt(listString.lastIndex)
            for (i in listString) {
                val item = i.split("~")
                val model = WhoisDataMain(item[0], item[1], item[2].toInt())
                listForRecycler.add(model)
            }
        }
        liveDataWhois.value = listForRecycler
    }

    suspend fun apiRequest(domainName: String): String {
        var uri: String
        try {
            uri = responseApiServer.getWhoisDataFromApi(domainName)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                uri = Constants.ERROR_CONNECTION
            }
        }
        return uri
    }
}