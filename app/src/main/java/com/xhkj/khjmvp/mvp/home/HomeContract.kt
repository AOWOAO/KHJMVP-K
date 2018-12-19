package com.xhkj.khjmvp.mvp.home

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

class HomeContract {

    interface View : MvpView {
        fun showToast(msg:String)
        fun updateList(data: List<DomainBean>)
    }



    interface Presenter : MvpPresenter<View> {
        fun getDomain()
        fun addDomain(host: String, root: String, remark: String, config: String)
        fun delDomain(host: String)
        fun updateDomain(host: String)

    }
}