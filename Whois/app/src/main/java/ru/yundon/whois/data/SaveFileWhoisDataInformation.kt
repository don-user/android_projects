package ru.yundon.whois.data

import android.content.Context
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import ru.yundon.whois.R
import ru.yundon.whois.utils.Constants
import ru.yundon.whois.utils.FileSaveAndGet

class SaveFileWhoisDataInformation(private val context: Context) {

    fun saveWhoisDataList(jsonObject: JSONObject) {

        var listMainWhoisName: Map<String, String>
        val item = jsonObject.getJSONObject("WhoisRecord")
        try {
            try {
                listMainWhoisName = mapOf(
                    "Domain Name" to item.getString("domainName"),
                    "Domain Name Extension" to item.getString("domainNameExt"),
                    "Estimated Domain Age" to item.getInt("estimatedDomainAge").toString(),
                    "Registrar Name" to item.getString("registrarName"),
                    "Registrar IANAID" to item.getString("registrarIANAID"),
                    "Updated Date" to item.getJSONObject("audit").getString("updatedDate"),
                    "Created Date" to item.getJSONObject("registryData").getString("createdDate"),
                    "Expires Date" to item.getJSONObject("registryData").getString("expiresDate"),
                    "Organization" to item.getJSONObject("registryData").getJSONObject("registrant")
                        .getString("organization"),
                    "Parsed Domain Name" to item.getJSONObject("registryData")
                        .getString("domainName"),
                    "Host Names" to item.getJSONObject("registryData").getJSONObject("nameServers")
                        .getString("hostNames"),
                    "Epp Status" to item.getJSONObject("registryData").getString("status"),
                    "WhoisServer" to item.getJSONObject("registryData").getString("whoisServer")
                )
            } catch (e: JSONException) {
                listMainWhoisName = mapOf(
                    "Domain Name" to item.getString("domainName"),
                    "Domain Name Extension" to item.getString("domainNameExt"),
                    "Estimated Domain Age" to item.getInt("estimatedDomainAge").toString(),
                    "Registrar Name" to item.getString("registrarName"),
                    "Registrar IANAID" to item.getString("registrarIANAID"),
                    "Updated Date" to item.getString("updatedDate"),
                    "Created Date" to item.getString("createdDate"),
                    "Expires Date" to item.getString("expiresDate"),
                    "Organization" to item.getJSONObject("registrant").getString("organization"),
                    "Parsed Domain Name" to item.getJSONObject("registryData")
                        .getString("domainName"),
                    "Host Names" to item.getJSONObject("registryData").getJSONObject("nameServers")
                        .getString("hostNames"),
                    "Epp Status" to item.getString("status"),
                    "WhoisServer" to item.getJSONObject("registryData").getString("whoisServer")
                )
            }
        } catch (e: Exception) {
            listMainWhoisName = mapOf(
                "Domain Name" to item.getString("domainName"),
                "Domain Name Extension" to item.getString("domainNameExt"),
                "Estimated Domain Age" to item.getInt("estimatedDomainAge").toString(),
                "Registrar Name" to item.getString("registrarName"),
                "Registrar IANAID" to item.getString("registrarIANAID"),
                "Updated Date" to item.getJSONObject("audit").getString("updatedDate"),
                "Created Date" to item.getJSONObject("registryData").getString("createdDate"),
                "Expires Date" to item.getJSONObject("registryData").getString("expiresDate"),
                "Parsed Domain Name" to item.getJSONObject("registryData").getString("domainName"),
                "Host Names" to item.getJSONObject("registryData").getJSONObject("nameServers")
                    .getString("hostNames"),
                "Epp Status" to item.getJSONObject("registryData").getString("status"),
                "WhoisServer" to item.getJSONObject("registryData").getString("whoisServer")
            )
        }
        var jsonString = ""
        listMainWhoisName.forEach { jsonString += "${it.key}: ${it.value} \n" }

        FileSaveAndGet(context).saveWhoisFile(item.getString("domainName"), jsonString)
    }

    fun saveResultForRecyclerView(jsonObject: JSONObject) {

        val item = jsonObject.getJSONObject("WhoisRecord")
        val fileGetAndSave = FileSaveAndGet(context)
        val stringWhoisDataMain = "${item.getString("domainName")}~${
            item.getJSONObject("audit").getString("updatedDate")
        }~${R.drawable.baseline_search}|"

        val stringFile = fileGetAndSave.getWhoisFile(Constants.FILE_NAME)
        if (stringFile.contains(item.getString("domainName"))) {
            Log.d("LOG SaveFile", "domainName существует")
        } else {
            fileGetAndSave.saveWhoisFile(Constants.FILE_NAME, stringWhoisDataMain)
        }
    }
}