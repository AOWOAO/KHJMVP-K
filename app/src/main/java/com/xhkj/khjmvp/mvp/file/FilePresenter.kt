package com.xhkj.khjmvp.mvp.file

import com.google.gson.reflect.TypeToken
import com.hannesdorfmann.mosby3.mvp.MvpQueuingBasePresenter
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.HttpParams
import com.lzy.okgo.model.Response
import com.xhkj.app.BaseBean
import com.xhkj.app.DirListData
import com.xhkj.khjmvp.App
import com.xhkj.khjmvp.mvp.JsonCallback
import com.xhkj.khjmvp.utils.Preference
import java.io.File

class FilePresenter : MvpQueuingBasePresenter<FileContract.View>(), FileContract.Presenter {


    private val host = Preference(App.instance).getString("host")

    override fun getDirList(path: String?) {
        val type = object : TypeToken<BaseBean<DirListData>>() {}.type
        val params = HttpParams()
        params.put("df", path)
        params.put("token", Preference(App.instance).getString("token"))
        OkGo.post<BaseBean<DirListData>>(host + "admin/df/lst")
            .params(params)
            .execute(object : JsonCallback<BaseBean<DirListData>>(type) {
                override fun onSuccess(response: Response<BaseBean<DirListData>>?) {
                    val bean = response!!.body()

                    ifViewAttached { view ->
                        view.updateDirList(bean.data)
                    }

                    if (bean.status != 1) getDirList("/")

                }

                override fun onError(response: Response<BaseBean<DirListData>>?) {
                    super.onError(response)
                    ifViewAttached { view ->
                        view.showToast("获取文件目录失败")
                        getDirList("/")
                    }
                }

            })
    }

    override fun openZip(filePath: String, unZipPath: String) {
        val type = object : TypeToken<BaseBean<List<String>>>() {}.type
        val params = HttpParams()
        params.put("zip_file", filePath)
        params.put("dist_dir", unZipPath)
        params.put("token", Preference(App.instance).getString("token"))
        OkGo.post<BaseBean<List<String>>>(host + "admin/zip/extract")
            .params(params)
            .execute(object : JsonCallback<BaseBean<List<String>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<String>>>?) {
                    val bean = response!!.body()
                    ifViewAttached { view ->
                        view.showToast(bean.message)
                    }

                    getDirList(unZipPath)

                }

                override fun onError(response: Response<BaseBean<List<String>>>?) {
                    super.onError(response)
                    ifViewAttached { view ->
                        view.showToast("unZip error")
                    }
                }

            })
    }

    override fun delFile(path: String, name: String) {
        val type = object : TypeToken<BaseBean<List<String>>>() {}.type
        val params = HttpParams()
        params.put("dir", path)
        params.put("name", name)
        params.put("token", Preference(App.instance).getString("token"))
        OkGo.post<BaseBean<List<String>>>(host + "admin/df/del")
            .params(params)
            .execute(object : JsonCallback<BaseBean<List<String>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<String>>>?) {
                    val bean = response!!.body()
                    ifViewAttached { view ->
                        view.showToast(bean.message)
                    }

                    if (bean.status == 1) getDirList(path)

                }

                override fun onError(response: Response<BaseBean<List<String>>>?) {
                    super.onError(response)
                    ifViewAttached { view ->
                        view.showToast("del error")
                    }
                }

            })
    }

    override fun renameFile(path: String, name: String, newName: String) {
        val type = object : TypeToken<BaseBean<List<String>>>() {}.type
        val params = HttpParams()
        params.put("dir", path)
        params.put("name", name)
        params.put("newname", newName)
        params.put("token", Preference(App.instance).getString("token"))
        OkGo.post<BaseBean<List<String>>>(host + "admin/df/rename")
            .params(params)
            .execute(object : JsonCallback<BaseBean<List<String>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<String>>>?) {
                    val bean = response!!.body()
                    ifViewAttached { view ->
                        view.showToast(bean.message)
                    }

                    if (bean.status == 1) getDirList(path)

                }

                override fun onError(response: Response<BaseBean<List<String>>>?) {
                    super.onError(response)
                    ifViewAttached { view ->
                        view.showToast("rename error")
                    }
                }

            })
    }

    override fun addNewFile(path: String, name: String) {
        val type = object : TypeToken<BaseBean<List<String>>>() {}.type
        val params = HttpParams()
        params.put("dir", path)
        params.put("file", name)
        params.put("token", Preference(App.instance).getString("token"))
        OkGo.post<BaseBean<List<String>>>(host + "admin/file/add")
            .params(params)
            .execute(object : JsonCallback<BaseBean<List<String>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<String>>>?) {
                    val bean = response!!.body()
                    ifViewAttached { view ->
                        view.showToast(bean.message)
                    }

                    if (bean.status == 1) getDirList(path)

                }

                override fun onError(response: Response<BaseBean<List<String>>>?) {
                    super.onError(response)
                    ifViewAttached { view ->
                        view.showToast("file error")
                    }
                }

            })
    }

    override fun addNewDir(path: String, name: String) {
        val type = object : TypeToken<BaseBean<List<String>>>() {}.type
        val params = HttpParams()
        params.put("dir", path)
        params.put("create_dir", name)
        params.put("token", Preference(App.instance).getString("token"))
        OkGo.post<BaseBean<List<String>>>(host + "admin/dir/add")
            .params(params)
            .execute(object : JsonCallback<BaseBean<List<String>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<String>>>?) {
                    val bean = response!!.body()
                    ifViewAttached { view ->
                        view.showToast(bean.message)
                    }

                    if (bean.status == 1) getDirList(path)

                }

                override fun onError(response: Response<BaseBean<List<String>>>?) {
                    super.onError(response)
                    ifViewAttached { view ->
                        view.showToast("dir error")
                    }
                }

            })
    }

    override fun updateFile(path: String, localPath: String) {
        val type = object : TypeToken<BaseBean<List<String>>>() {}.type
        val params = HttpParams()
        params.put("dir", path)
        params.put("file", File(localPath))
        params.put("token", Preference(App.instance).getString("token"))
        OkGo.post<BaseBean<List<String>>>(host + "admin/file/upload")
            .params(params)
            .execute(object : JsonCallback<BaseBean<List<String>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<String>>>?) {
                    val bean = response!!.body()
                    ifViewAttached { view ->
                        view.showToast(bean.message)
                    }

                    getDirList(path)

                }

                override fun onError(response: Response<BaseBean<List<String>>>?) {
                    super.onError(response)
                    ifViewAttached { view ->
                        view.showToast("updateFile error")
                    }
                }

            })
    }

    override fun moveFile(cmd: String, path: String, newPath: String) {
        val type = object : TypeToken<BaseBean<List<String>>>() {}.type
        val params = HttpParams()
        params.put("cmd", cmd)
        params.put("source", path)
        params.put("dest", newPath)
        params.put("token", Preference(App.instance).getString("token"))
        OkGo.post<BaseBean<List<String>>>(host + "admin/df/movecopy")
            .params(params)
            .execute(object : JsonCallback<BaseBean<List<String>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<String>>>?) {
                    val bean = response!!.body()
                    ifViewAttached { view ->
                        view.showToast(bean.message)
                    }

                    if (bean.status == 1) getDirList(newPath)

                }

                override fun onError(response: Response<BaseBean<List<String>>>?) {
                    super.onError(response)
                    ifViewAttached { view ->
                        view.showToast("moveFile error")
                    }
                }

            })
    }

}