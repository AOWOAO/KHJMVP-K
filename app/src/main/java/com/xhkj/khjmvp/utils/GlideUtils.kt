package com.xhkj.app.utils

import android.view.View
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.xhkj.khjmvp.R


object GlideUtils {

    private val options = RequestOptions()
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_loading)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)


    fun load(itemView: View?, url: Any?) {
        Glide.with(itemView!!.context)
                .load(url)
                .apply(options)
                .into((itemView as ImageView?)!!)
    }
}
