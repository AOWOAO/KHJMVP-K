package com.xhkj.khjmvp

import android.app.Application
import com.lzy.okgo.OkGo
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import java.util.logging.Level


class App : Application() {

    companion object {
        var instance: App by NotNullSingleValueVar<App>()
    }

    private fun initOkgo() {
        val builder = OkHttpClient.Builder()//构建OkHttpClient.Builder

        val loggingInterceptor = HttpLoggingInterceptor("OkGo")
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO)
        builder.addInterceptor(loggingInterceptor)

        /*//全局的读取超时时间
        builder.readTimeout(30, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(30, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(30, TimeUnit.MILLISECONDS);*/

        OkGo.getInstance().init(this)                       //必须调用初始化
            .setOkHttpClient(builder.build()).retryCount = 3                              //全局统一超时重连次数，默认为三次，不需要可以设置为0
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initOkgo()
    }
}