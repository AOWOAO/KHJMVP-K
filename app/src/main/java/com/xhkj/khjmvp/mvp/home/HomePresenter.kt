package com.xhkj.khjmvp.mvp.home

import com.google.gson.reflect.TypeToken
import com.hannesdorfmann.mosby3.mvp.MvpQueuingBasePresenter
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.HttpParams
import com.lzy.okgo.model.Response
import com.xhkj.app.BaseBean
import com.xhkj.khjmvp.App
import com.xhkj.khjmvp.mvp.JsonCallback
import com.xhkj.khjmvp.utils.Preference

class HomePresenter : MvpQueuingBasePresenter<HomeContract.View>(), HomeContract.Presenter {

    private val host = "http://test.phpweilai.cc/"

    override fun getDomain() {
        val type = object : TypeToken<BaseBean<List<DomainBean>?>>() {}.type
        OkGo.post<BaseBean<List<DomainBean>>>(host + "admin/vhost/lst")
            .params("token", Preference(App.instance).getString("token"))
            .execute(object: JsonCallback<BaseBean<List<DomainBean>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<DomainBean>>>?) {
                    val bean = response?.body()

                    ifViewAttached { view ->
                        if (bean!!.status == 1) {
                            view.updateList(bean.data!!)
                        } else {
                            view.showToast(bean.message!!)
                        }
                    }

                }

                override fun onError(response: Response<BaseBean<List<DomainBean>>>?) {
                    super.onError(response)
                    ifViewAttached {
                            view ->  view.showToast("出错了")
                    }
                }


            })
    }

    override fun addDomain(host: String, root: String, remark: String, config: String) {
        var params = HttpParams()
        params.put("server_name", host)
        params.put("root", root)
        params.put("remark", remark)
        params.put("config_file_name", config)
        params.put("token", Preference(App.instance).getString("token"))
        val type = object : TypeToken<BaseBean<List<String>>>() {}.type
        OkGo.post<BaseBean<List<String>>>(host + "admin/vhost/addsave")
            .params(params)
            .execute(object: JsonCallback<BaseBean<List<String>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<String>>>?) {
                    val bean = response!!.body()

                    ifViewAttached { view ->
                        if (bean.status == 1) {

                        } else {

                        }
                    }

                }

                override fun onError(response: Response<BaseBean<List<String>>>?) {
                    super.onError(response)
                    ifViewAttached {
                            view ->  view.showToast("出错了")
                    }
                }


            })
    }

    override fun delDomain(host: String) {
        val type = object : TypeToken<BaseBean<List<String>>>() {}.type
        OkGo.post<BaseBean<List<String>>>(host + "admin/vhost/del")
            .params("file_name", host)
            .params("token", Preference(App.instance).getString("token"))
            .execute(object: JsonCallback<BaseBean<List<String>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<String>>>?) {
                    val bean = response!!.body()

                    ifViewAttached { view ->
                        if (bean.status == 1) {

                        } else {

                        }
                    }

                }

                override fun onError(response: Response<BaseBean<List<String>>>?) {
                    super.onError(response)
                    ifViewAttached {
                            view ->  view.showToast("出错了")
                    }
                }


            })
    }

    override fun updateDomain(host: String) {
        val type = object : TypeToken<BaseBean<List<String>>>() {}.type
        OkGo.post<BaseBean<List<String>>>(host + "admin/vhost/update")
            .params("name", host)
            .params("token", Preference(App.instance).getString("token"))
            .execute(object: JsonCallback<BaseBean<List<String>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<String>>>?) {
                    val bean = response!!.body()

                    ifViewAttached { view ->
                        if (bean.status == 1) {

                        } else {

                        }
                    }

                }

                override fun onError(response: Response<BaseBean<List<String>>>?) {
                    super.onError(response)
                    ifViewAttached {
                            view ->  view.showToast("出错了")
                    }
                }


            })
    }

}