package ru.yundon.whois.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import ru.yundon.whois.adapter.AdapterDomainName
import ru.yundon.whois.api.WhoisDataMain
import ru.yundon.whois.databinding.ActivityMainBinding
import ru.yundon.whois.ui.domainInformation.DomainInformation
import ru.yundon.whois.utils.Constants.ERROR_CONNECTION
import ru.yundon.whois.utils.Constants.ERROR_MESSAGE

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var whoisAdapter: AdapterDomainName? = null
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val action = intent.action
        val data = intent.dataString
        if (Intent.ACTION_VIEW == action && data != null) {
            binding.inputDomainName.setText(data)
        }
        clickButton()
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.apply {
            listWhoisDataForRecyclerView()
            mainViewModel.liveDataWhois.observe(this@MainActivity, Observer {
                setupRecyclerView(it)
            })
        }
    }

    private fun clickButton() = with(binding){

        buttonSearch.setOnClickListener {

            inputMethodService()

            val urlDomain = inputDomainName.text.toString()

            progressBarLoading.visibility = View.VISIBLE

            CoroutineScope(Dispatchers.IO).launch {

                val url = mainViewModel.apiRequest(urlDomain)

                withContext(Dispatchers.Main) {
                    inputDomainName.setText("")
                    progressBarLoading.visibility = View.GONE
                    when {
                        url.isEmpty() -> toast(ERROR_MESSAGE)
                        url == ERROR_CONNECTION -> {
                            toast(ERROR_CONNECTION)

                        }
                        else -> {
                            val intent = Intent(this@MainActivity, DomainInformation::class.java)
                            intent.putExtra("URL", url)
                            this@MainActivity.startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(list: List<WhoisDataMain>) {
        binding.domainUrlRecycler.layoutManager = LinearLayoutManager(this)

        whoisAdapter = AdapterDomainName(list, object : AdapterDomainName.ItemClickListener {
            override fun onItemClick(itemWhoisData: WhoisDataMain?) {
                val item = itemWhoisData?.domainName
                val intent = Intent(this@MainActivity, DomainInformation::class.java)
                intent.putExtra("URL", item)
                this@MainActivity.startActivity(intent)
            }
        })
        binding.domainUrlRecycler.adapter = whoisAdapter
    }

    private fun toast(text: String) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }

    private fun inputMethodService(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}