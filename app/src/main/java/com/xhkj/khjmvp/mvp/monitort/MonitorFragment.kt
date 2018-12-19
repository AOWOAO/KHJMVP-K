package com.xhkj.khjmvp.mvp.monitort

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.litesuits.common.assist.Toastor
import com.qmuiteam.qmui.widget.QMUIProgressBar
import com.xhkj.khjmvp.R
import com.xhkj.khjmvp.activity.TestActivity
import com.xhkj.khjmvp.utils.RxTimerUtil
import kotlinx.android.synthetic.main.fragment_monitor.*


class MonitorFragment : MvpFragment<MonitorContract.View, MonitorContract.Presenter>(),
    MonitorContract.View, View.OnClickListener {

    override fun onClick(v: View?) {
        startActivity(Intent(mContext, TestActivity::class.java))
    }

    private var mContext: Context? = null

    override fun createPresenter(): MonitorPresenter {
        return MonitorPresenter()
    }

    override fun showToast(msg: String?) {
        Toastor(mContext).showToast(msg)
    }

    override fun updateHostInfo(info: HostInfo?) {
        hostName.text = "主机名: ${info?.hostname}"
        hostSystem.text = "系统: ${info?.system_name} ${info?.system_bit}"
        hostSystemVersion.text = "系统版本: ${info?.system_ver}"
        hostCPU.text = "CPU: ${info?.cpu_model_name}"
        hostCPUNum.text = "CPU核心数: ${info?.cpu_core_logical_true}核"
        hostPythonVersion.text = "Python版本: ${info?.python_ver}"
        hostRunTime.text = "运行时间: ${info?.boot_time}"
    }

    override fun updateCpuInfo(info: CpuInfo?) {
        cpuTitle.text = "CPU使用率\r\n${info?.core}核心"
        CpuProgressBar.progress = info?.percent!!.toFloat().toInt()
        CpuProgressBar.qmuiProgressBarTextGenerator =
                QMUIProgressBar.QMUIProgressBarTextGenerator { progressBar, value, maxValue ->

                    (100 * value / maxValue).toString() + "%"
                }

    }

    override fun updateMemoryInfo(info: MemoryDiskInfo?) {

        val total = info?.total_space!!.toLong() / 1024 / 1024 / 1024
        val used = info.used_space!!.toLong() / 1024 / 1024 / 1024
        val percent = (used.toFloat() / total.toFloat() * 100).toInt()

        memoryTitle.text = "内存使用率" + "\r\n" + "${used}GB/${total}GB"
        MemoryProgressBar.progress = percent
        MemoryProgressBar.qmuiProgressBarTextGenerator =
                QMUIProgressBar.QMUIProgressBarTextGenerator { progressBar, value, maxValue ->

                    (100 * value / maxValue).toString() + "%"
                }

    }

    override fun updateDiskInfo(info: MemoryDiskInfo?) {

        val total = info?.total_space!!.toLong() / 1024 / 1024 / 1024
        val used = info?.used_space!!.toLong() / 1024 / 1024 / 1024
        val percent = (used.toFloat() / total.toFloat() * 100).toInt()

        diskTitle.text = "磁盘使用率" + "\r\n" + "${used}GB/${total}GB"
        DiskProgressBar.progress = percent
        DiskProgressBar.qmuiProgressBarTextGenerator =
                QMUIProgressBar.QMUIProgressBarTextGenerator { progressBar, value, maxValue ->

                    (100 * value / maxValue).toString() + "%"
                }

    }

    override fun updateNetworkInfo(info: NetworkInfo?) {
        netUp.text = "上行速度: ${info?.up!!.toInt() / 1024}KB/s"
        netDown.text = "下行速度: ${info?.down!!.toInt() / 1024}KB/s"
        netUpAll.text = "总发送: ${info?.total_up!!.toLong() / 1024 / 1024}M"
        netDownAll.text = "总接收: ${info?.total_down!!.toLong() / 1024 / 1024}M"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        RxTimerUtil.cancel()
        mContext = null
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_monitor, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getInfo()

        CpuCard.setOnClickListener(this)
        MemoryCard.setOnClickListener(this)
        DiskCard.setOnClickListener(this)

        RxTimerUtil.interval((60 * 1000).toLong(), object : RxTimerUtil.IRxNext {

            override fun doNext(number: Long) {
                showToast("监控刷新")
                getInfo()
            }
        })
    }


    private fun getInfo() {

        presenter.getHostInfo()
        presenter.getCpuInfo()
        presenter.getMemoryInfo()
        presenter.getDiskInfo()
        presenter.getNetworkInfo()

    }


}
