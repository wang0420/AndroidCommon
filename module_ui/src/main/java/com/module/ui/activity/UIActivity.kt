package com.module.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.basemodule.ARouterManager
import com.basemodule.net.NetActivity
import com.basemodule.net.NewNetActivity
import com.module.ui.R
import com.module.ui.activity.guide.user.GuideMainActivity
import com.module.ui.adapter.MainAdapter
import com.module.ui.bean.UIItem
import com.module.ui.util.DividerItemDecoration
import com.module.ui.widget.drop_down.DropDownActivity
import kotlinx.android.synthetic.main.activity_ui.*


@Route(path = ARouterManager.UIActivity)
class UIActivity : AppCompatActivity() {
    // private  lateinit var  mRecyclerView: RecyclerView
    // var list = ArrayList<UIItem>()
    private var mAdapter: MainAdapter? = null

/**companion object 修饰为伴生对象,伴生对象在类中只能存在一个，
 * 类似于java中的静态方法 Java 中使用类访问静态成员，静态方法。*/
    companion object {
    private  val MY_TAG = "DemoManager"

    var saleType: Int = 1
    val name = "can only be inner"
        fun start(num: String):String {
            return "123$MY_TAG"
        }
    }

    //数据源
    private fun uiItemData(): List<UIItem> {
        return listOf(UIItem("圆形进度条", CircleProgressActivity::class.java),
                UIItem("UI-Widget使用", WidgetExampleActivity::class.java),
                UIItem("尺子刻度", RulerViewActivity::class.java),
                UIItem("图片预览", PhotoGalleryActivity::class.java),
                UIItem("横向滚动View", HorizontalScrollViewActivity::class.java),
                UIItem("支付宝城市服务", RecycleViewTabActivity::class.java),
                UIItem("高斯模糊圖片", MoHuImageActivity::class.java),
                UIItem("步骤条Step", StepActivity::class.java),
                UIItem("视频播放", VideoPlayActivity::class.java),
                UIItem("视频播放2", MainVideoActivity::class.java),
                UIItem("通讯录", IndexActivity::class.java),
                UIItem("NET", NetActivity::class.java),
                UIItem("NewNET", NewNetActivity::class.java),
                UIItem("tabIndex", TabIndexActivity::class.java),
                UIItem("BottomSheetDialog", BottomSheetActivity::class.java),
                UIItem("浮沉引导页效果", GuideMainActivity::class.java),
                UIItem("FlexboxLayout-Adapter", TestActivity::class.java),
                UIItem("PermissionActivity", PermissionActivity::class.java),

                UIItem("SnapHelper-用于辅助RecyclerView在滚动结束时将Item对齐到某个位置。特别是列表横向滑动时LinearSnapHelper 类的目的是将某个View停留在正中间", SnapHelperActivity::class.java),

                UIItem("一个支持四个方向循环滚动的自定义控件", MarqueeLayoutActivity::class.java),
                UIItem("下拉菜单", DropDownActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui)
        //mRecyclerView = findViewById(R.id.rv_list)/
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_list.layoutManager = linearLayoutManager
        rv_list.addItemDecoration(DividerItemDecoration())
        rv_list.itemAnimator = DefaultItemAnimator()    // 设置item动画
        mAdapter = MainAdapter(this)
        rv_list.adapter = mAdapter
        for (item in uiItemData()) {
            mAdapter!!.addItem(item)
        }

        setListener()

    }


    private fun setListener() {
        mAdapter!!.setOnItemClickListener(object : MainAdapter.OnItemClickListener {
            override fun onItemClick(index: Int) {
                val intent = Intent(this@UIActivity, mAdapter!!.getItem(index).activity)
                startActivity(intent)
            }
        })
    }
}
