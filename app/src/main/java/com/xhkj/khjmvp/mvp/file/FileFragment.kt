package com.xhkj.khjmvp.mvp.file

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.flyco.dialog.entity.DialogMenuItem
import com.flyco.dialog.listener.OnBtnClickL
import com.flyco.dialog.widget.NormalDialog
import com.flyco.dialog.widget.NormalListDialog
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.litesuits.android.log.Log
import com.litesuits.common.assist.Toastor
import com.litesuits.common.utils.VibrateUtil
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.xhkj.app.DirListData
import com.xhkj.khjmvp.R
import kotlinx.android.synthetic.main.fragment_file.*


class FileFragment : MvpFragment<FileContract.View, FileContract.Presenter>(), FileContract.View {


    private lateinit var mContext: Context
    private var li = ArrayList<DirListData.Detail>()
    private var fileRvAdapter: FileRvAdapter? = null
    private var path: String? = "/data/wwwroot/"

    override fun createPresenter(): FileContract.Presenter {
        return FilePresenter()
    }

    override fun showToast(msg: String?) {
        Toastor(mContext).showToast(msg)
    }

    override fun updateDirList(data: DirListData?) {

        showToast(path)
        if (data!!.type == 1) { //文件夹

            path = data.dir!!.replace(" ", "")

            pathEdt.setText(path)

            li = data.detail!!

            fileRvAdapter!!.data = li

        } else {//文件
            openFile(null)
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_file, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()
        fabCtrl()

        pathEdt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {

                //oldPath = path //记录手动输入前的路径

                path = pathEdt.text.toString().replace(" ", "")

                presenter.getDirList(path)

            }

            false
        }
    }

    override fun onStart() {
        super.onStart()

        presenter.getDirList(path)
    }

    private fun initRecyclerView() {

        fileRecycle.layoutManager = LinearLayoutManager(mContext)

        fileRvAdapter = FileRvAdapter(fileRecycle)

        fileRecycle.adapter = fileRvAdapter

        fileRvAdapter!!.setOnRVItemClickListener { parent, itemView, position ->

            /*由于fab在Fragment中无法回调失去焦点的事件,手动回调*/
            fabMenu.closeMenu()
            //showDialog(position)
            openFile(position)
        }

        fileRvAdapter!!.setOnRVItemLongClickListener { parent, itemView, position ->

            VibrateUtil.vibrate(mContext, 100)
            //showToast("longClick")
            showDialog(position)
            false
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
        mMenuItems.add(DialogMenuItem("剪切", R.drawable.ic_dialog_rename))
        mMenuItems.add(DialogMenuItem("删除", R.drawable.ic_dialog_del))

        val dialog = NormalListDialog(mContext, mMenuItems)

        dialog.title("请选择")
            .titleBgColor(resources.getColor(R.color.colorPrimary))
            .show()

        dialog.setOnOperItemClickL { _, _, index, _ ->

            when (index) {
                0 -> openFile(pos)

                1 -> renameFile(pos)

                2 -> delFile(pos)
            }

            dialog.dismiss()

        }
    }

    private fun openFile(pos: Int?) {

        if (pos != null) { //代表是通过点击列表来操作

            when {
                li[pos].df == 1 -> {//打开文件夹

                    path += li[pos].name + "/"

                    pathEdt.setText(path)

                    presenter.getDirList(path)

                }
                li[pos].df == 2 -> {//打开文本文件

                    val bundle = Bundle()
                    bundle.putString("title", li[pos].name)
                    bundle.putString("path", path + li[pos].name)
                    showToast("open textFile")

                    //TODO 打开EditActivity
                    /*val intent = Intent(mContext, EditActivity::class.java)
                        intent.putExtras(bundle)
                        mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())*/

                }
                li[pos].df == 5 -> presenter.openZip(path + li[pos].name, path!!) //打开zip

                else -> Toastor(mContext).showToast("不支持打开此类型的文件")
            }


        } else { //代表通过手动输入path来操作

            Toastor(mContext).showToast("无法通过绝对路径操作文件,请到文件目录通过点击操作")

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
                    Toastor(mContext).showToast("文件夹名不能为空")
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
                    Toastor(mContext).showToast("文件名不能为空")
                }

            }
            .show()

    }

    private fun updateFile() {

        //TODO updateFile
        /*val type = object : TypeToken<BaseBean<String>>() {}.type
        OkGo.post<BaseBean<String>>("http://test.phpweilai.cc/admin/file/upload")
                .params("dir", path)
                .params("create_dir", "")
                .execute(object: JsonCallback<BaseBean<String>>(type) {

                    override fun onSuccess(response: Response<BaseBean<String>>?) {
                        val bean = response!!.body()
                        Toastor(mContext).showToast(bean.message)
                    }

                })*/
    }


}

