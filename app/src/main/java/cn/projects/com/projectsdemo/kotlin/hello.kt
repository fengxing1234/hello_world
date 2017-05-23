package cn.projects.com.projectsdemo.kotlin

/**
 * Created by fengxing on 2017/5/19.
 */
fun main(args : Array<String>){
    var quntity = 5
    val price : Double = 20.5
    val name : String  = "大米"
    print("单价：$price")
    print("数量：$quntity")
    print("产品：$name 总计: ${quntity * price}")
}