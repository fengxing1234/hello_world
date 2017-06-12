package cn.projects.com.projectsdemo.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.projects.com.projectsdemo.R
import kotlinx.android.synthetic.main.activity_kotlin.*


/**
 * Created by fengxing on 2017/5/19.
 */

class KotlinDemoActivity : AppCompatActivity() {

    /**
     * 伴生对象 java 使用@JvmStatic 注解 变成静态方法 或者成员
     * 在运行时是真实对象的实例成员，还可以实现接口：
     */
    companion object {
        val GITHUB_URL = "https://github.com/wuapnjie/PoiShuhui-Kotlin"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        init()
    }

    private fun init(){
        setSupportActionBar(tb_kotlin_tool_bar)
    }
}
