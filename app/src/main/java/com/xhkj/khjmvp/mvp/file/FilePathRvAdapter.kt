package com.xhkj.khjmvp.mvp.file


import android.support.v7.widget.RecyclerView

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper
import com.xhkj.app.DirListData
import com.xhkj.khjmvp.R


class FilePathRvAdapter(recyclerView: RecyclerView) : BGARecyclerViewAdapter<String>(recyclerView, R.layout.item_file_path) {


    override fun fillData(helper: BGAViewHolderHelper?, position: Int, model: String?) {
        helper!!.setText(R.id.item_path, model)

    }


}
