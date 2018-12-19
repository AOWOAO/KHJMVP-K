package com.xhkj.khjmvp.mvp.login

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

class LoginContract {

    interface View : MvpView {
        fun showToast(msg:String?)
        fun showLoading()
        fun hideLoading()
        fun loginSuccess(data: LoginBean)
    }



    interface Presenter : MvpPresenter<View> {
        fun login(user:String, pwd:String, host:String)

    }
}