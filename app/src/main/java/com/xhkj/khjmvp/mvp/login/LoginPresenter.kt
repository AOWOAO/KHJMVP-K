package com.xhkj.khjmvp.mvp.login

import com.google.gson.reflect.TypeToken
import com.hannesdorfmann.mosby3.mvp.MvpQueuingBasePresenter
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request
import com.xhkj.app.BaseBean
import com.xhkj.khjmvp.mvp.JsonCallback

class LoginPresenter : MvpQueuingBasePresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun login(user: String, pwd: String, host: String) {

        val type = object : TypeToken<BaseBean<LoginBean>>() {}.type
        OkGo.post<BaseBean<LoginBean>>(host + "login")
            .params("name", user)
            .params("password", pwd)
            .execute(object : JsonCallback<BaseBean<LoginBean>>(type) {

                override fun onStart(request: Request<BaseBean<LoginBean>, out Request<Any, Request<*, *>>>?) {
                    super.onStart(request)
                    ifViewAttached { view ->
                        view.showLoading()
                    }
                }

                override fun onSuccess(response: Response<BaseBean<LoginBean>>?) {
                    val bean = response?.body()

                    ifViewAttached { view ->
                        view.showToast(bean?.message)
                    }

                    if (bean?.status == 1) {
                        ifViewAttached { view ->
                            view.loginSuccess(bean.data!!)
                        }
                    }

                }

                override fun onError(response: Response<BaseBean<LoginBean>>?) {
                    super.onError(response)
                    ifViewAttached { view ->
                        view.showToast("登录失败")
                    }
                }

                override fun onFinish() {
                    super.onFinish()
                    ifViewAttached { view ->
                        view.hideLoading()
                    }
                }

            })

    }


}