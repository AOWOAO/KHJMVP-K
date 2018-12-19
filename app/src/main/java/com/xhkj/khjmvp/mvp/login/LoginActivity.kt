package com.xhkj.khjmvp.mvp.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.litesuits.common.assist.Toastor
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.xhkj.app.utils.GlideUtils
import com.xhkj.khjmvp.App
import com.xhkj.khjmvp.main.MainActivity
import com.xhkj.khjmvp.R
import com.xhkj.khjmvp.utils.Preference

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : MvpActivity<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {

    private lateinit var loadDialog: QMUITipDialog

    override fun showLoading() {
        loadDialog = QMUITipDialog.Builder(this)
            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
            .setTipWord("正在登录")
            .create()
        loadDialog.show()
    }

    override fun hideLoading() {
        loadDialog.dismiss()
    }

    override fun showToast(msg: String?) {
        Toastor(this).showToast(msg)
    }

    override fun loginSuccess(data: LoginBean?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    override fun createPresenter(): LoginPresenter {
        return LoginPresenter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }


    private fun initView() {

        GlideUtils.load(logo, R.mipmap.ic_launcher_round)

        edt_password.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_delete.visibility = View.INVISIBLE
                if (!TextUtils.isEmpty(s.toString())) {
                    btn_delete.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


        btn_delete.setOnClickListener {

            edt_password.setText("")
        }

        btn_login.setOnClickListener {
            login()
        }

        edt_host.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login()
            }

            false
        }
    }

    private fun TextView.checkBlank(msg: String): String? {
        val text = this.text.toString()
        if (text.isBlank()) {
            Toastor(this.context).showToast(msg)
            return null
        }
        return text.replace(" ", "")
    }

    private fun login() {

        val user = edt_username.checkBlank("用户名不能为空") ?: return
        val pwd = edt_password.checkBlank("密码不能为空") ?: return
        val host = edt_host.checkBlank("主机地址不能为空") ?: return

        presenter.login(user, pwd, host)

    }

}
