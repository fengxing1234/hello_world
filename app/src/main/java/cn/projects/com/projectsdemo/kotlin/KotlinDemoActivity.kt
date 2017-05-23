package cn.projects.com.projectsdemo.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import cn.projects.com.projectsdemo.R

/**
 * Created by fengxing on 2017/5/19.
 */

class KotlinDemoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        var quntity = 5
        val price : Double = 20.5
        val name : String  = "大米"
        print("单价：$price")
        print("数量：$quntity")
        print("产品：$name 总计: ${quntity * price}")
        Log.d("fengxing","fengxing")
    }
}
