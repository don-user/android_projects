package ru.yundon.whois.ui.domainInformation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.yundon.whois.databinding.ActivityDomainInformationBinding
import ru.yundon.whois.utils.Constants.TOOLBAR_NAME


class DomainInformation : AppCompatActivity() {

    private lateinit var binding: ActivityDomainInformationBinding
    lateinit var domainInformationViewModel: DomainInformationViewModel
    var domainName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDomainInformationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        domainName = intent.getStringExtra("URL")

        domainInformationViewModel = ViewModelProvider(this, ViewModelFactory(application, domainName))[DomainInformationViewModel::class.java]

        setToolbar()
        observeInformation(this)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun observeInformation (owner: DomainInformation){
        domainInformationViewModel.liveDataDomainName.observe(owner, Observer {
            binding.titeTextView.text = it
        })
        domainInformationViewModel.liveDataWhoisInformation.observe(owner, Observer {
            binding.textViewInformation.text = it
        })
    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "$TOOLBAR_NAME $domainName"
    }

}


