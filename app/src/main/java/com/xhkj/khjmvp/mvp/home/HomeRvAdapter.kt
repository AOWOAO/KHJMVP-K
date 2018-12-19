package com.xhkj.khjmvp.mvp.home


import android.support.v7.widget.RecyclerView
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper
import com.xhkj.khjmvp.R


class HomeRvAdapter(recyclerView: RecyclerView) : BGARecyclerViewAdapter<DomainBean>(recyclerView, R.layout.item_website) {


    override fun fillData(helper: BGAViewHolderHelper, position: Int, model: DomainBean?) {
        helper.setText(R.id.item_host, "域名: " + model?.server_name)
            .setText(R.id.item_root, "根目录: " + model?.root)
            .setText(R.id.item_mark,  "备注: " + model?.remark)

    }


}
