package ru.yundon.whois.ui.domainInformation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.yundon.whois.utils.Constants
import ru.yundon.whois.utils.FileSaveAndGet

class DomainInformationViewModel(application: Application, private val domainName: String?) :
    AndroidViewModel(application) {

    val liveDataWhoisInformation = MutableLiveData<String>()
    val liveDataDomainName = MutableLiveData<String>()

    init {
        textInformation()
    }

    private fun textInformation() {
        liveDataWhoisInformation.value = FileSaveAndGet(getApplication()).getWhoisFile(domainName)
        liveDataDomainName.value = "${Constants.DOMAIN_NAME}: $domainName"
    }
}