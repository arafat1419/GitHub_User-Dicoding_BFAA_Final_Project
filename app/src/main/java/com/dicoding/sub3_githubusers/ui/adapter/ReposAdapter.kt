package com.dicoding.sub3_githubusers.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.sub3_githubusers.data.model.Repos
import com.dicoding.sub3_githubusers.databinding.ItemRowReposBinding

class ReposAdapter : RecyclerView.Adapter<ReposAdapter.ReposViewHolder>() {
    private val list = ArrayList<Repos>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(repos: ArrayList<Repos>) {
        list.clear()
        list.addAll(repos)
        notifyDataSetChanged()
    }

    inner class ReposViewHolder(private val binding: ItemRowReposBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repos: Repos) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(repos)
            }

            binding.apply {
                binding.txtName.text = repos.name
                binding.txtDescription.text = repos.description
                binding.txtLanguange.text = repos.language
                binding.txtCreated.text = repos.createdAt?.take(4) ?: repos.createdAt
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ReposViewHolder {
        val view = ItemRowReposBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ReposViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Repos)
    }
}