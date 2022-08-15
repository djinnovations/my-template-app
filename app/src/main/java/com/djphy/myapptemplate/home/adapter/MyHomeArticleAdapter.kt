package com.djphy.myapptemplate.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.djphy.myapptemplate.databinding.ItemUserLaunchTimesBinding
import com.djphy.myapptemplate.home.model.UserAndLaunches

class MyHomeArticleAdapter(val mSelectionListener: SelectionListener?= null) : RecyclerView.Adapter<MyHomeArticleAdapter.ItemViewHolder>() {

    interface SelectionListener {
        fun onItemClick(position: Int, item: UserAndLaunches)
    }

    private var mDataList = arrayListOf<UserAndLaunches>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemLayoutBinding = ItemUserLaunchTimesBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemLayoutBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mDataList[position])
    }

    inner class ItemViewHolder(
        private val itemBinding: ItemUserLaunchTimesBinding
    ) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        fun bind(item: UserAndLaunches) {
            with(itemBinding){
                tvTitle.text = item.user.name
                tvDesc.text = item.user.rocket
                tvDate.text = item.user.timestamp.toString().substring(0 until 10)
                val flicker = item.launches?.links?.flickr_images ?: emptyList()
                if (flicker.isNotEmpty()) {
                    flicker.get(0)?.also {
                        ivView.load(it) {
                            crossfade(true)
                            transformations(CircleCropTransformation())
                        }
                    }
                }
                root.setOnClickListener(this@ItemViewHolder)
            }
        }

        override fun onClick(view: View) {
            val position = absoluteAdapterPosition
            mSelectionListener?.onItemClick(position, mDataList[position])
        }
    }

    override fun getItemCount(): Int = mDataList.size

    fun submitList(dataList: List<UserAndLaunches>){
        mDataList.apply {
            clear()
            addAll(dataList)
        }
        notifyDataSetChanged()
    }
}