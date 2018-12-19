package com.xhkj.khjmvp.mvp.monitort

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

class MonitorContract {

    interface View : MvpView {
        fun showToast(msg:String?)
        fun updateHostInfo(info: HostInfo?)
        fun updateCpuInfo(info: CpuInfo?)
        fun updateMemoryInfo(info: MemoryDiskInfo?)
        fun updateDiskInfo(info: MemoryDiskInfo?)
        fun updateNetworkInfo(info: NetworkInfo?)

    }


    interface Presenter : MvpPresenter<View> {
        fun getHostInfo()
        fun getCpuInfo()
        fun getMemoryInfo()
        fun getDiskInfo()
        fun getNetworkInfo()
    }
}