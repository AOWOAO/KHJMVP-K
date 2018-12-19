package com.xhkj.khjmvp.mvp.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.litesuits.common.assist.Toastor
import com.xhkj.khjmvp.R


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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        presenter.getDomain()

    }

    private fun initView() {


    }


}


