package ru.yundon.whois.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yundon.whois.api.WhoisDataMain
import ru.yundon.whois.databinding.ItemDomainBinding

class AdapterDomainName(
    private val mainWhoisName: List<WhoisDataMain>,
    private val onItemClickListener: ItemClickListener
) : RecyclerView.Adapter<AdapterDomainName.MainWhoisNameViewHolder>() {

    private lateinit var binding: ItemDomainBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainWhoisNameViewHolder {
        binding = ItemDomainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewBinding = binding.root
        viewBinding.setOnClickListener {
            onItemClickListener.onItemClick(it.tag as WhoisDataMain)
        }
        return MainWhoisNameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainWhoisNameViewHolder, position: Int) {
        val itemViewHolder: WhoisDataMain = mainWhoisName[position]
        holder.bind(itemViewHolder)
        holder.itemView.tag = itemViewHolder

    }

    override fun getItemCount(): Int {
        return mainWhoisName.size
    }

    class MainWhoisNameViewHolder(var viewBinding: ItemDomainBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(mainWhoisName: WhoisDataMain) {
            viewBinding.textUrlDomain.text = mainWhoisName.domainName
            viewBinding.textGreatData.text = mainWhoisName.updatedDate

        }
    }

    interface ItemClickListener {
        fun onItemClick(itemWhoisData: WhoisDataMain?)
    }


}