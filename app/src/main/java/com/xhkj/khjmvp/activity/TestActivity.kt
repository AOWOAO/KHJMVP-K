package com.xhkj.khjmvp.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.github.mikephil.charting.data.Entry
import com.xhkj.khjmvp.App
import com.xhkj.khjmvp.R
import com.xhkj.khjmvp.base.ChartBean
import com.xhkj.khjmvp.utils.Preference
import com.xhkj.khjmvp.widget.CustomDatePicker
import kotlinx.android.synthetic.main.activity_test.*
import java.text.SimpleDateFormat
import java.util.*


class TestActivity : AppCompatActivity() {

    private val host = Preference(App.instance).getString("host")
    private val entries = ArrayList<Entry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        initView()
        initDatePicker()


    }


    private fun initView() {

        val dataList = ArrayList<ChartBean>()
        dataList.add(ChartBean("1531027621", 0.22f))
        dataList.add(ChartBean("1531047632", 0.85f))
        dataList.add(ChartBean("1531077644", 0.33f))
        dataList.add(ChartBean("1531097655", 0.57f))
        dataList.add(ChartBean("1531127666", 0.18f))
        dataList.add(ChartBean("1531027621", 0.22f))
        dataList.add(ChartBean("1531047632", 0.85f))
        dataList.add(ChartBean("1531077644", 0.33f))
        dataList.add(ChartBean("1531097655", 0.57f))
        dataList.add(ChartBean("1531127666", 1f))

        val lineChartManager = LineChartManager(lineChart)
        lineChartManager.showLineChart(dataList, "CPU", ContextCompat.getColor(this, R.color.colorPrimary))

        val drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue)
        lineChartManager.setChartFillDrawable(drawable)
        lineChartManager.setMarkerView(this)

        btn_back.setOnClickListener {
            this.finish()
        }

    }

    private fun initDatePicker() {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
        val now = sdf.format(Date())
        currentTime.text = now

        val customDatePicker = CustomDatePicker(this, CustomDatePicker.ResultHandler {
            currentTime.text = it
        }, "2010-01-01 00:00", now)

        customDatePicker.showSpecificTime(true) // 显示时和分
        customDatePicker.setIsLoop(true) // 允许循环滚动

        currentTime.setOnClickListener {
            customDatePicker.show(currentTime.text.toString())
        }
    }


  /**显示图标
     * @param title
     * 标题
     * @param lineData
     * 数据
     * */
   /* private fun showChart(title:String, lineData: LineData) {
        // 设置数据
        lineChart.data = lineData

        // 设置markerView
        //lineChart.marker =

        // 是否在折线图上添加边框
        lineChart.setDrawBorders(true)
        val description = Description()
        description.text = title
        description.textSize = 16f
        description.textColor = ContextCompat.getColor(this, R.color.qmui_config_color_75_pure_black)

        lineChart.description = description
        // 无数据时显示的文本
        lineChart.setNoDataText("暂无数据")
        // 是否显示表格颜色
        lineChart.setDrawGridBackground(true)
        // 表格颜色(透明)
        lineChart.setGridBackgroundColor(ContextCompat.getColor(this, R.color.qmui_s_transparent))
        // 是否绘制表格边框颜色
        lineChart.setDrawBorders(true)
        // 是否响应触摸
        lineChart.setTouchEnabled(true)
        // 是否可拖拽
        lineChart.isDragEnabled = true
        //是否可缩放
        lineChart.setScaleEnabled(true)
        lineChart.setPinchZoom(true)

        // 设置背景颜色
        lineChart.setBackgroundColor(ContextCompat.getColor(this, R.color.qmui_config_color_white))

        // 图例对象
        val legend = Legend()
        legend.position = Legend.LegendPosition.BELOW_CHART_CENTER
        // 图例样式 (CIRCLE圆形;LINE线性;SQUARE是方块)
        legend.form = Legend.LegendForm.SQUARE
        // 图例大小
        legend.formSize = 8f
        // 图例字体颜色
        legend.textColor = ContextCompat.getColor(this, R.color.colorLight)
        legend.textSize = 12f
        //图例显示和隐藏
        legend.isEnabled = true
        // 隐藏右侧Y轴(只在左侧的Y轴显示刻度)
        lineChart.axisRight.isEnabled = true

        val xAxis = lineChart.xAxis
        // 显示X轴上的刻度值
        xAxis.setDrawLabels(true)
        // 设置X轴的数据显示在报表的下方
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        // 轴线
        xAxis.setDrawAxisLine(false)
        // 设置不从X轴发出纵向直线
        xAxis.setDrawGridLines(false)
        // 执行的动画,x轴(动画持续时间)
        lineChart.animateX(2500)
        lineChart.notifyDataSetChanged()
    }*/

    /**曲线赋值与设置
     * @param xDataList
     * @param yDataList
     * @param label 表示一条折线的介绍
     * @return linData
     */
    /*private fun setLineData(xDataList: List<String>, yDataList: List<Entry>, label:String):LineData {
        val lineDataSets = ArrayList<LineDataSet>()

        val yDataSet = LineDataSet(yDataList,label)

        lineDataSets.add(yDataSet)

        val lineData = LineData()
        lineData.addDataSet(lineDataSets)
        return lineData
    }*/



    /*private fun getCpuInfo() {
        val type = object : TypeToken<BaseBean<List<CpuInfo>>>() {}.type
        OkGo.post<BaseBean<List<CpuInfo>>>(host + "admin/env/cpulst")
            .params("id", "10")
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
                    dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER//设置曲线图:


                    val xAxis = lineChart.xAxis
                    xAxis.position = XAxis.XAxisPosition.BOTTOM//设置x轴的显示位置
                    xAxis.setLabelCount(5, true)//X轴显示整数


                    //3.chart设置数据
                    val lineData = LineData(dataSet)
                    lineChart.data = lineData
                    lineChart.invalidate() // refresh

                    lineChart.animateY(2000)//动画效果，MPAndroidChart中还有很多动画效果可以挖掘

                }

                override fun onError(response: Response<BaseBean<List<CpuInfo>>>?) {
                    super.onError(response)
                    Log.i("xdxczx")
                }

            })
    }

    private fun initUI() {
        lineChart.description.isEnabled = false
        lineChart.setDrawBorders(true)
        //1.设置x轴和y轴的点
        //val entries = ArrayList<Entry>()


        entries.add(Entry(round(1.0f), Random().nextInt(100).toFloat()))
        entries.add(Entry(round(2.0f), Random().nextInt(100).toFloat()))
        entries.add(Entry(round(3.0f), Random().nextInt(100).toFloat()))
        entries.add(Entry(round(4.0f), Random().nextInt(100).toFloat()))
        entries.add(Entry(round(5.0f), Random().nextInt(100).toFloat()))

        getCpuInfo()

    }*/
}

