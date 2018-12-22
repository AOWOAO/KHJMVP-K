package com.xhkj.khjmvp.mvp.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.litesuits.common.assist.Toastor
import com.xhkj.app.utils.GlideImageLoader
import com.xhkj.khjmvp.R
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : MvpFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

    private var mContext: Context? = null


    override fun createPresenter(): HomeContract.Presenter {
        return HomePresenter()
    }

    override fun showToast(msg: String) {
        Toastor(mContext).showToast(msg)
    }

    override fun updateList(data: List<DomainBean>) {

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

    }

    override fun onDetach() {
        mContext = null
        super.onDetach()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        presenter.getDomain()

    }

    private fun initBanner() {

        val images = ArrayList<String>()
        images.add("http://p4.so.qhmsg.com/bdr/_240_/t01a50d86f455c0bf19.png")
        images.add("http://p4.so.qhmsg.com/bdr/_240_/t014bb329d2cc017a89.jpg")
        images.add("http://p1.so.qhimgs1.com/bdr/_240_/t01011e9ee975932e4d.jpg")

        val adImg = ArrayList<String>()
        adImg.add("http://p5.so.qhimgs1.com/bdr/_240_/t01a5809d40660059bb.jpg")
        adImg.add("http://p0.so.qhimgs1.com/bdr/_240_/t01c8372050bae51880.jpg")
        adImg.add("http://p1.so.qhimgs1.com/bdr/_240_/t01825716b3e8d97d81.jpg")

        val adTitles = ArrayList<String>()
        adTitles.add("素材均来自网络")
        adTitles.add("版权归原作者所有")
        adTitles.add("如有侵权请联系:110")


        banner.setImageLoader(GlideImageLoader())
        banner.setImages(images)
        banner.setDelayTime(3000)
        banner.setBannerAnimation(Transformer.ZoomOut)
        banner.start()

        AdBanner.setImageLoader(GlideImageLoader())
        AdBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
        AdBanner.setImages(adImg)
        AdBanner.setBannerTitles(adTitles)
        AdBanner.setDelayTime(3000)
        AdBanner.start()

    }

    private fun initBtn() {

        btn_product.setOnClickListener {
            showToast("产品")
        }

        btn_khj.setOnClickListener {
            showToast("快环境")
        }

        btn_about.setOnClickListener {
            showToast("关于")
        }
    }

    private fun initView() {

        initBanner()
        initBtn()

    }


}



