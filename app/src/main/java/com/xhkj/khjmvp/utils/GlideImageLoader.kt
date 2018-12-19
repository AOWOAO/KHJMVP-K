package com.xhkj.app.utils

import android.content.Context
import android.widget.ImageView

import com.youth.banner.loader.ImageLoader

class GlideImageLoader : ImageLoader() {

    override fun displayImage(context: Context, path: Any, imageView: ImageView) {

        GlideUtils.load(imageView, path)

    }

}
