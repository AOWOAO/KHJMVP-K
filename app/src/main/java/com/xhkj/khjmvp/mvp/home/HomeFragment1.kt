package com.xhkj.khjmvp.mvp.home

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flyco.dialog.entity.DialogMenuItem
import com.flyco.dialog.widget.NormalListDialog
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.litesuits.common.assist.Toastor
import com.litesuits.common.utils.VibrateUtil
import com.xhkj.khjmvp.R
import kotlinx.android.synthetic.main.fragment_home1.*


class HomeFragment1 : MvpFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

    private var mContext: Context? = null
    private lateinit var homeRvAdapter: HomeRvAdapter


    override fun createPresenter(): HomeContract.Presenter {
        return HomePresenter()
    }

    override fun showToast(msg: String) {
        Toastor(mContext).showToast(msg)
    }

    override fun updateList(data: List<DomainBean>) {
        homeRvAdapter.data = data
        header_num.text = "${data.size}个"
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

    }

    override fun onDetach() {
        mContext = null
        super.onDetach()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        presenter.getDomain()

    }

    private fun initView() {

        homeRecycler.layoutManager = LinearLayoutManager(mContext)
        homeRvAdapter = HomeRvAdapter(homeRecycler)
        homeRecycler.adapter = homeRvAdapter
        homeRvAdapter!!.setOnRVItemClickListener { parent, itemView, position ->

        }

        homeRvAdapter!!.setOnRVItemLongClickListener { parent, itemView, position ->

            VibrateUtil.vibrate(mContext, 100)
            showDialog(position)
            return@setOnRVItemLongClickListener true
        }
    }

    private fun showDialog(pos: Int?) {
        val mMenuItems = ArrayList<DialogMenuItem>()

        mMenuItems.add(DialogMenuItem("更新", R.drawable.ic_dialog_rename))
        mMenuItems.add(DialogMenuItem("删除", R.drawable.ic_dialog_del))

        val dialog = NormalListDialog(mContext, mMenuItems)

        dialog.title("请选择")
            .titleBgColor(resources.getColor(R.color.colorPrimary))
            .show()

        dialog.setOnOperItemClickL { _, _, index, _ ->

            when (index) {
                0 -> presenter.updateDomain(homeRvAdapter!!.data[pos!!].server_name!!)

                1 -> presenter.delDomain(homeRvAdapter!!.data[pos!!].file_name!!)

            }

            dialog.dismiss()

        }
    }

}


