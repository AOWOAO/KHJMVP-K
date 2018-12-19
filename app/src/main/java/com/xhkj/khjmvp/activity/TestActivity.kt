package com.xhkj.khjmvp.activity


import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.xhkj.app.BaseBean
import com.xhkj.khjmvp.App
import com.xhkj.khjmvp.R
import com.xhkj.khjmvp.mvp.JsonCallback
import com.xhkj.khjmvp.mvp.monitort.CpuInfo
import com.xhkj.khjmvp.utils.Preference
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*
import kotlin.math.round

class TestActivity : AppCompatActivity() {

    private val host = Preference(App.instance).getString("host")
    private val entries = ArrayList<Entry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        initUI()
    }

    private fun getCpuInfo() {
        val type = object : TypeToken<BaseBean<List<CpuInfo>>>() {}.type
        OkGo.post<BaseBean<List<CpuInfo>>>(host + "admin/env/cpulst")
            .params("id", "70")
            .params("token", Preference(App.instance).getString("token"))
            .execute(object : JsonCallback<BaseBean<List<CpuInfo>>>(type) {

                override fun onSuccess(response: Response<BaseBean<List<CpuInfo>>>?) {
                    val bean = response?.body()
                    entries.add(Entry(round(1.0f), bean?.data?.get(0)?.percent!!.toFloat()))
                    entries.add(Entry(round(2.0f), bean.data?.get(1)?.percent!!.toFloat()))
                    entries.add(Entry(round(3.0f), bean.data?.get(2)?.percent!!.toFloat()))
                    entries.add(Entry(round(4.0f), bean.data?.get(3)?.percent!!.toFloat()))
                    entries.add(Entry(round(5.0f), bean.data?.get(4)?.percent!!.toFloat()))

                    val dataSet = LineDataSet(entries, "CPU使用率") // add entries to dataset
                    dataSet.color = Color.parseColor("#149AB7")//线条颜色
                    dataSet.setCircleColor(Color.parseColor("#149AB7"))//圆点颜色
                    //dataSet.color = Color.parseColor("#ff5500")//线条颜色
                    //dataSet.setCircleColor(Color.parseColor("#ff5500"))//圆点颜色
                    dataSet.lineWidth = 1f//线条宽度


                    val xAxis = LineChart.xAxis
                    xAxis.position = XAxis.XAxisPosition.BOTTOM//设置x轴的显示位置
                    xAxis.setLabelCount(5, true)//X轴显示整数


                    //3.chart设置数据
                    val lineData = LineData(dataSet)
                    LineChart.data = lineData
                    LineChart.invalidate() // refresh

                    LineChart.animateY(2000)//动画效果，MPAndroidChart中还有很多动画效果可以挖掘

                }

                override fun onError(response: Response<BaseBean<List<CpuInfo>>>?) {
                    super.onError(response)

                }

            })
    }

    private fun initUI() {
        LineChart.description.isEnabled = false
        LineChart.setDrawBorders(true)
        //1.设置x轴和y轴的点
        //val entries = ArrayList<Entry>()


        /*entries.add(Entry(round(1.0f), Random().nextInt(100).toFloat()))
        entries.add(Entry(round(2.0f), Random().nextInt(100).toFloat()))
        entries.add(Entry(round(3.0f), Random().nextInt(100).toFloat()))
        entries.add(Entry(round(4.0f), Random().nextInt(100).toFloat()))
        entries.add(Entry(round(5.0f), Random().nextInt(100).toFloat()))*/

        getCpuInfo()


    }
}
