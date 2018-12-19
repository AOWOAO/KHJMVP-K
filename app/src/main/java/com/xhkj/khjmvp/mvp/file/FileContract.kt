package com.xhkj.khjmvp.mvp.file

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.xhkj.app.DirListData

class FileContract {

    interface View : MvpView {
        fun showToast(msg:String?)
        fun updateDirList(data: DirListData?)

    }


    interface Presenter : MvpPresenter<View> {
        fun getDirList(path: String?)
        fun openZip(filePath: String, unZipPath: String)
        fun delFile(path: String, name: String)
        fun renameFile(path: String, name: String, newName: String)
        fun addNewFile(path: String, name: String)
        fun addNewDir(path: String, name: String)
        fun updateFile(path: String, loaclPath: String)
        fun moveFile(cmd: String = "1", path: String, newPath: String)
    }
}