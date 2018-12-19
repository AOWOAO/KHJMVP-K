package com.xhkj.khjmvp.mvp.file


import android.support.v7.widget.RecyclerView

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper
import com.xhkj.app.DirListData
import com.xhkj.khjmvp.R


class FileRvAdapter(recyclerView: RecyclerView) : BGARecyclerViewAdapter<DirListData.Detail>(recyclerView, R.layout.item_file) {


    override fun fillData(helper: BGAViewHolderHelper?, position: Int, model: DirListData.Detail?) {
        helper!!.setText(R.id.item_tile, model!!.name).setText(R.id.item_tag, model.fz)

        when(model.df) {
            1 -> helper.setImageResource(R.id.item_image, R.drawable.ic_item_dir)

            5-> helper.setImageResource(R.id.item_image, R.drawable.ic_item_zip)

            else -> helper.setImageResource(R.id.item_image, R.drawable.ic_item_file)
        }

    }


}
