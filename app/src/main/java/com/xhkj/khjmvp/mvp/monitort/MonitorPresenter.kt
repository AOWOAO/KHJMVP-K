package com.xhkj.khjmvp.mvp.monitort


import com.google.gson.reflect.TypeToken
import com.hannesdorfmann.mosby3.mvp.MvpQueuingBasePresenter
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.HttpParams
import com.lzy.okgo.model.Response
import com.xhkj.app.BaseBean
import com.xhkj.khjmvp.App
import com.xhkj.khjmvp.mvp.JsonCallback
import com.xhkj.khjmvp.utils.Preference


class MonitorPresenter : MvpQueuingBasePresenter<MonitorContract.View>()
    , MonitorContract.Presenter {

    private val host = Preference(App.instance).getString("host")

    override fun getHostInfo() {
        val type = object : TypeToken<BaseBean<List<HostInfo>>>() {}.type
        val params = HttpParams()
        params.put("id", "")
        params.put("token", Preference(App.instance).getString("token"))
        OkGo.post<BaseBean<List<HostInfo>>>(host + "admin/env/hardwarelst")
            .params(params)
            .execute(object : JsonCallback<BaseBean<List<HostInfo>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<HostInfo>>>?) {
                    val bean = response?.body()

                    bean?.let {
                        if (it.status != 1) {
                            ifViewAttached { view ->
                                view.showToast(it.message)
                            }
                            return
                        }

                        ifViewAttached { view ->
                            view.updateHostInfo(it.data?.get(0))
                        }
                    }

                }

                override fun onError(response: Response<BaseBean<List<HostInfo>>>?) {
                    super.onError(response)
                    ifViewAttached { view ->
                        view.showToast("出错了")
                    }
                }


            })
    }

    override fun getCpuInfo() {

        val type = object : TypeToken<BaseBean<List<CpuInfo>>>() {}.type
        val params = HttpParams()
        params.put("id", "")
        params.put("token", Preference(App.instance).getString("token"))
        OkGo.post<BaseBean<List<CpuInfo>>>(host + "admin/env/cpulst")
            .params(params)
            .execute(object : JsonCallback<BaseBean<List<CpuInfo>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<CpuInfo>>>?) {
                    val bean = response?.body()

                    bean?.let {
                        if (it.status != 1) {
                            ifViewAttached { view ->
                                view.showToast(it.message)
                            }
                            return
                        }

                        ifViewAttached { view ->
                            view.updateCpuInfo(it.data?.get(0))
                        }
                    }

                }

                override fun onError(response: Response<BaseBean<List<CpuInfo>>>?) {
                    super.onError(response)
                    ifViewAttached { view ->
                        view.showToast("出错了")
                    }
                }


            })
    }

    override fun getMemoryInfo() {
        val type = object : TypeToken<BaseBean<List<MemoryDiskInfo>>>() {}.type
        val params = HttpParams()
        params.put("id", "")
        params.put("token", Preference(App.instance).getString("token"))
        OkGo.post<BaseBean<List<MemoryDiskInfo>>>(host + "admin/env/memlst")
            .params(params)
            .execute(object : JsonCallback<BaseBean<List<MemoryDiskInfo>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<MemoryDiskInfo>>>?) {
                    val bean = response?.body()

                    bean?.let {
                        if (it.status != 1) {
                            ifViewAttached { view ->
                                view.showToast(it.message)
                            }
                            return
                        }

                        ifViewAttached { view ->
                            view.updateMemoryInfo(it.data?.get(0))
                        }
                    }

                }

                override fun onError(response: Response<BaseBean<List<MemoryDiskInfo>>>?) {
                    super.onError(response)
                    ifViewAttached { view ->
                        view.showToast("出错了")
                    }
                }


            })
    }

    override fun getDiskInfo() {
        val type = object : TypeToken<BaseBean<List<MemoryDiskInfo>>>() {}.type
        val params = HttpParams()
        params.put("id", "")
        params.put("token", Preference(App.instance).getString("token"))
        OkGo.post<BaseBean<List<MemoryDiskInfo>>>(host + "admin/env/disklst")
            .params(params)
            .execute(object : JsonCallback<BaseBean<List<MemoryDiskInfo>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<MemoryDiskInfo>>>?) {
                    val bean = response?.body()

                    bean?.let {
                        if (it.status != 1) {
                            ifViewAttached { view ->
                                view.showToast(it.message)
                            }
                            return
                        }

                        ifViewAttached { view ->
                            view.updateDiskInfo(it.data?.get(0))
                        }
                    }

                }

                override fun onError(response: Response<BaseBean<List<MemoryDiskInfo>>>?) {
                    super.onError(response)
                    ifViewAttached { view ->
                        view.showToast("出错了")
                    }
                }


            })
    }

    override fun getNetworkInfo() {
        val type = object : TypeToken<BaseBean<List<NetworkInfo>>>() {}.type
        val params = HttpParams()
        params.put("id", "")
        params.put("token", Preference(App.instance).getString("token"))
        OkGo.post<BaseBean<List<NetworkInfo>>>(host + "admin/env/networklst")
            .params(params)
            .execute(object : JsonCallback<BaseBean<List<NetworkInfo>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<NetworkInfo>>>?) {
                    val bean = response?.body()

                    bean?.let {
                        if (it.status != 1) {
                            ifViewAttached { view ->
                                view.showToast(it.message)
                            }
                        }

                        ifViewAttached { view ->
                            view.updateNetworkInfo(it.data?.get(0))
                        }
                    }

                }

                override fun onError(response: Response<BaseBean<List<NetworkInfo>>>?) {
                    super.onError(response)
                    ifViewAttached { view ->
                        view.showToast("出错了")
                    }
                }


            })
    }


}