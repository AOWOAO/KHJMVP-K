package com.xhkj.khjmvp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import com.google.gson.reflect.TypeToken
import com.litesuits.common.assist.Toastor
import com.litesuits.common.data.DataKeeper
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.xhkj.app.BaseBean
import com.xhkj.app.FileContentData
import com.xhkj.khjmvp.App
import com.xhkj.khjmvp.R
import com.xhkj.khjmvp.mvp.JsonCallback
import com.xhkj.khjmvp.utils.Preference
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    private val host: String? = Preference(App.instance).getString("host")
    private var name: String? = ""
    private var path: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val bundle = this.intent.extras

        name = bundle.getString("title")//get("title").toString()

        path = bundle.getString("path")

        topTitle.text = name

        editView.setText("正在打开,请稍后...")

        getContent()

        btn_save.setOnClickListener {

            save(editView.text.toString())
        }

        btn_cancel.setOnClickListener {

            finish()
        }

    }

    private fun getContent() {
        val type = object : TypeToken<BaseBean<FileContentData>>() {}.type
        OkGo.post<BaseBean<FileContentData>>(host + "admin/df/lst")
            .params("df", path)
            .params("token", Preference(App.instance).getString("token"))
            .execute(object: JsonCallback<BaseBean<FileContentData>>(type) {

                override fun onSuccess(response: Response<BaseBean<FileContentData>>?) {
                    val bean = response!!.body()
                    if (bean.data!!.content.isNullOrEmpty()){
                        Toastor(this@EditActivity).showToast("此文件内容为空")
                        editView.setText("此文件内容为空")
                        return
                    }
                    editView.setText(bean.data!!.content)

                }

                override fun onError(response: Response<BaseBean<FileContentData>>?) {
                    super.onError(response)
                    //Toastor(this@EditActivity).showToast("error: $path")
                    //editView.setText("打开文件失败")
                    Toastor(this@EditActivity).showToast("不支持打开此类型文件")
                    finish()
                }

            })
    }

    private fun save(content: String?) {
        val type = object : TypeToken<BaseBean<List<String>>>() {}.type
        OkGo.post<BaseBean<List<String>>>(host + "admin/file/save")
            .params("dir", path!!.replace(name!!, ""))
            .params("name", name)
            .params("content", content)
            .execute(object: JsonCallback<BaseBean<List<String>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<String>>>?) {
                    val bean = response!!.body()
                    Toastor(this@EditActivity).showToast(bean.message)
                }

                override fun onError(response: Response<BaseBean<List<String>>>?) {
                    super.onError(response)
                    Toastor(this@EditActivity).showToast("update error")
                }

            })
    }

    private var mExitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                Toastor(this).showToast("再按一次退出编辑")
                mExitTime = System.currentTimeMillis()

            } else {
                finish()
            }
            return true
        }
        return false
    }
}
