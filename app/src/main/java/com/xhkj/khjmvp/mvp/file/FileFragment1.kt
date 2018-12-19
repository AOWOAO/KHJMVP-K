package com.xhkj.khjmvp.mvp.file

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flyco.dialog.entity.DialogMenuItem
import com.flyco.dialog.listener.OnBtnClickL
import com.flyco.dialog.widget.NormalDialog
import com.flyco.dialog.widget.NormalListDialog
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.litesuits.android.log.Log
import com.litesuits.common.assist.Toastor
import com.litesuits.common.utils.VibrateUtil
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.xhkj.app.DirListData
import com.xhkj.khjmvp.R
import com.xhkj.khjmvp.activity.EditActivity
import com.xhkj.khjmvp.main.TabEntity
import kotlinx.android.synthetic.main.fragment_file1.*


class FileFragment1 : MvpFragment<FileContract.View, FileContract.Presenter>(), FileContract.View {


    private lateinit var mContext: Context
    private var dirs = ArrayList<String>()
    private var li = ArrayList<DirListData.Detail>()
    private var filePathRvAdapter: FilePathRvAdapter? = null
    private var fileRvAdapter: FileRvAdapter? = null
    private var path: String? = "/"

    override fun createPresenter(): FileContract.Presenter {
        return FilePresenter()
    }

    override fun showToast(msg: String?) {
        Toastor(mContext).showToast(msg)
    }

    override fun updateDirList(data: DirListData?) {

        dirs.clear()
        path = data!!.dir + "/"

        //showToast("log: $path")

        val p = path!!.split("/")

        for (dir in p) {
            if (!dir.isBlank()) dirs.add(dir)
        }
        filePathRvAdapter!!.data = dirs
        pathRecycle.smoothScrollToPosition(dirs.size)

        li = data.detail!!
        fileRvAdapter!!.data = li
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_file1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()
        fabCtrl()

        rootPath.setOnClickListener {
            dirs.clear()
            filePathRvAdapter!!.data = dirs

            presenter.getDirList("/")
        }

    }

    override fun onStart() {
        super.onStart()

        presenter.getDirList(path)
    }

    private fun initRecyclerView() {

        //filePathRecycle
        pathRecycle.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        filePathRvAdapter = FilePathRvAdapter(pathRecycle)
        pathRecycle.adapter = filePathRvAdapter

        filePathRvAdapter!!.setOnRVItemClickListener { _, _, position ->

            //处理path
            if (position + 1 < dirs.size) {
                var dir: String? = "/"
                for (i in 0 until position + 1) {
                    dir += "${dirs[i]}/"
                }
                presenter.getDirList(dir)
            }
        }


        //fileRecycle
        fileRecycle.layoutManager = LinearLayoutManager(mContext)
        fileRvAdapter = FileRvAdapter(fileRecycle)
        fileRecycle.adapter = fileRvAdapter


        fileRvAdapter!!.setOnRVItemClickListener { parent, itemView, position ->

            /*由于fab在Fragment中无法回调失去焦点的事件,手动回调*/
            fabMenu.closeMenu()
            //showDialog(position)
            openFile(position)

        }

        fileRvAdapter!!.setOnRVItemLongClickListener { _, _, position ->

            VibrateUtil.vibrate(mContext, 100)
            //showToast("longClick")
            showDialog(position)
            return@setOnRVItemLongClickListener true
        }


    }


    /*文件操作 打开 重命名 删除*/
    private fun showDialog(pos: Int?) {
        val mMenuItems = ArrayList<DialogMenuItem>()
        if (li[pos!!].df != 5) {
            mMenuItems.add(DialogMenuItem("打开", R.drawable.ic_dialog_open))
        } else {
            mMenuItems.add(DialogMenuItem("解压", R.drawable.ic_dialog_open))
        }
        mMenuItems.add(DialogMenuItem("重命名", R.drawable.ic_dialog_rename))
        mMenuItems.add(DialogMenuItem("复制", R.drawable.ic_dialog_copy))
        mMenuItems.add(DialogMenuItem("剪切", R.drawable.ic_dialog_cut))
        mMenuItems.add(DialogMenuItem("删除", R.drawable.ic_dialog_del))

        val dialog = NormalListDialog(mContext, mMenuItems)

        dialog.title("请选择")
            .titleBgColor(resources.getColor(R.color.colorPrimary))
            .show()

        dialog.setOnOperItemClickL { _, _, index, _ ->

            when (index) {
                0 -> openFile(pos)

                1 -> renameFile(pos)

                2 -> copyNcutFile(pos, "1")

                3 -> copyNcutFile(pos, "2")

                4 -> delFile(pos)
            }

            dialog.dismiss()

        }
    }

    private fun openFile(pos: Int?) {

        when {
            li[pos!!].df == 1 -> {//打开文件夹

                path += li[pos].name + "/"

                presenter.getDirList(path)

            }
            li[pos].df == 2 -> {//打开文本文件

                val bundle = Bundle()
                bundle.putString("title", li[pos].name)
                bundle.putString("path", path + li[pos].name)

                //TODO 打开EditActivity
                val intent = Intent(mContext, EditActivity::class.java)
                    intent.putExtras(bundle)
                    mContext.startActivity(intent)

            }
            li[pos].df == 5 -> presenter.openZip(path + li[pos].name, path!!) //打开zip

            else -> showToast("不支持打开此类型的文件")
        }

    }

    private fun renameFile(pos: Int?) {

        val oldName = li[pos!!].name

        val builder = QMUIDialog.EditTextDialogBuilder(mContext)

        builder.setTitle("重命名")
            .setInputType(InputType.TYPE_CLASS_TEXT)
            .addAction("取消") { dialog, index -> dialog.dismiss() }
            .addAction("确定") { dialog, index ->
                val name = builder.editText.text.toString()
                if (!name.isBlank()) {

                    presenter.renameFile(path!!, oldName!!, name)
                    dialog.dismiss()

                } else {
                    showToast("命名不能为空")
                }
            }
            .show()
        builder.editText.setText(oldName)
    }

    private fun delFile(pos: Int?) {

        val dialog = NormalDialog(mContext)
        dialog.title("删除")
            .content("是否删除: " + li[pos!!].name)
            .style(NormalDialog.STYLE_TWO)
            .show()

        dialog.setOnBtnClickL(
            OnBtnClickL {
                //取消按钮
                dialog.dismiss()
            },
            OnBtnClickL {
                //确认按钮

                presenter.delFile(path!!, li[pos].name!!)
                dialog.dismiss()

            })

    }

    private fun copyNcutFile(pos: Int?, cmd: String) {
        showToast("请选择粘帖目录")
        fabMenu.visibility = View.INVISIBLE

        val file = path + li[pos!!].name

        //showToast("file: $file")

        val mTitles: Array<String> = arrayOf("新建", "粘帖", "取消")
        val mIconUnSelectIds: IntArray =
            intArrayOf(R.drawable.ic_new_dir, R.drawable.ic_dialog_copy, R.drawable.ic_cencal)
        val mIconSelectIds: IntArray =
            intArrayOf(R.drawable.ic_new_dir, R.drawable.ic_dialog_copy, R.drawable.ic_cencal)

        val mTabEntities = ArrayList<CustomTabEntity>()
        for (i in mTitles.indices) {
            mTabEntities.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]))
        }

        tabLayout.setTabData(mTabEntities)
        tabLayout.visibility = View.VISIBLE
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {

            override fun onTabSelect(position: Int) {
                when (position) {
                    0 -> addNewDir()

                    1 -> {
                        presenter.moveFile(cmd, file, path!!)
                        tabLayout.visibility = View.GONE
                        fabMenu.visibility = View.VISIBLE
                        Log.i("------copy-----","操作的文件: $file  粘帖的路径: $path")
                    }

                    2 -> {
                        tabLayout.visibility = View.GONE
                        fabMenu.visibility = View.VISIBLE
                    }
                }
            }

            override fun onTabReselect(position: Int) {
                when (position) {
                    0 -> addNewDir()

                    1 -> {
                        presenter.moveFile(cmd, file, path!!)
                        tabLayout.visibility = View.GONE
                        fabMenu.visibility = View.VISIBLE
                        Log.i("------copy-----","操作的文件: $file  粘帖的路径: $path")
                    }

                    2 -> {
                        tabLayout.visibility = View.GONE
                        fabMenu.visibility = View.VISIBLE
                    }
                }
            }
        })

        //presenter.moveFile(cmd, file, "")
    }


    /*控制 新建目录 新建文件 上传文件*/
    private fun fabCtrl() {
        fabMenu.addOnMenuItemClickListener { miniFab, label, itemId ->
            VibrateUtil.vibrate(mContext, 100)

            when (itemId) {
                R.id.menu_new_dir -> addNewDir()

                R.id.menu_new_file -> addNewFile()

                R.id.menu_update -> updateFile()
            }

            Log.i("+++++++++=>", label!!.text)
        }
    }

    private fun addNewDir() {

        val builder = QMUIDialog.EditTextDialogBuilder(mContext)
        builder.setTitle("新建文件夹")
            .setPlaceholder("文件夹名")
            .setInputType(InputType.TYPE_CLASS_TEXT)
            .addAction("取消") { dialog, index -> dialog.dismiss() }
            .addAction("新建") { dialog, index ->

                val name = builder.editText.text.toString()

                if (!name.isBlank()) {

                    presenter.addNewDir(path!!, name)
                    dialog.dismiss()

                } else {
                    showToast("文件夹名不能为空")
                }

            }
            .show()


    }

    private fun addNewFile() {

        val builder = QMUIDialog.EditTextDialogBuilder(mContext)
        builder.setTitle("新建文件")
            .setPlaceholder("文件名")
            .setInputType(InputType.TYPE_CLASS_TEXT)
            .addAction("取消") { dialog, index -> dialog.dismiss() }
            .addAction("新建") { dialog, index ->

                val name = builder.editText.text.toString()

                if (!name.isBlank()) {

                    presenter.addNewFile(path!!, name)
                    dialog.dismiss()

                } else {
                    showToast("文件名不能为空")
                }

            }
            .show()

    }

    private fun updateFile() {

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"//所有类型文件
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, 1)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === Activity.RESULT_OK) {
            val uri = data!!.data
            if ("file".equals(uri.scheme, ignoreCase = true)) {//使用第三方应用打开
                val localPath = uri.path
                presenter.updateFile(path!!, localPath)
                //showToast(localPath)
                return
            }

        }
    }

}

