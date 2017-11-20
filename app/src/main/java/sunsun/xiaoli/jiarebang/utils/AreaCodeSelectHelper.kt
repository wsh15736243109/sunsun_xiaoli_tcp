package sunsun.xiaoli.jiarebang.utils

import android.app.Activity
import android.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import sunsun.xiaoli.jiarebang.R
import java.util.*

/**
 * AreaCodeSelectHelper
 *
 * Created by Mr.w on 2017/11/20.
 *
 *  版本      ${version}
 *
 *  修改时间
 *
 *  修改内容
 *
 */

class AreaCodeSelectHelper {
    var listItems: ArrayList<Map<String, Any>>?=null
    var codeArray: Array<out String>?=null

    fun showAreaCode(activity: Activity, view: Int, btn_country: TextView, iAreaCodeSelect: IAreaCodeSelect){
        var codeView= View.inflate(activity,view,null)
        var dialog = AlertDialog.Builder(activity).setView(codeView).create()
        dialog.show()
        val ed_place = codeView.findViewById(R.id.edit_place) as EditText
        val btn_cancel = codeView.findViewById(R.id.btn_cancel) as TextView
        var listView = codeView.findViewById(R.id.listView) as ListView
        codeArray = activity.getResources().getStringArray(R.array.country_code_list)
        // 创建一个List集合，List集合的元素是Map
        listItems = ArrayList<Map<String, Any>>()
        for (i1 in (codeArray as Array<out String>?)!!.indices) {
            val string = (codeArray as Array<out String>?)!![i1]
            val listItem = HashMap<String, Any>()
            listItem.put("country", string)
            listItems!!.add(listItem)
        }
        var country:String?=null
        listView.setAdapter(SimpleAdapter(activity, listItems, android.R.layout.simple_list_item_1, arrayOf("country"), intArrayOf(android.R.id.text1)))
        listView.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            country = listItems!!.get(position).get("country").toString()
            country = country!!.substring(country!!.indexOf("*") + 1, country!!.length)
            btn_country.setText(country)
            dialog.dismiss()
            iAreaCodeSelect.selectFinish(country!!)
        })
        ed_place.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                val content = ed_place.text.toString()
                listItems = beginSearch(content)
                listView.setAdapter(SimpleAdapter(activity, listItems, android.R.layout.simple_list_item_1, arrayOf("country"), intArrayOf(android.R.id.text1)))
            }
        })
        btn_cancel.setOnClickListener {
            dialog.dismiss()
//            val content = ed_place.text.toString()
//            listItems = beginSearch(content)
//            listView.setAdapter(SimpleAdapter(activity, listItems, android.R.layout.simple_list_item_1, arrayOf("country"), intArrayOf(android.R.id.text1)))
        }
    }

    private fun beginSearch(content: String): ArrayList<Map<String, Any>> {
        listItems = ArrayList<Map<String, Any>>()
        for (i in (codeArray as Array<out String>?)!!.indices) {
            if ((codeArray as Array<out String>?)!![i].contains(content)) {
                val listItem = HashMap<String, Any>()
                listItem.put("country", (codeArray as Array<out String>?)!![i])
                listItems!!.add(listItem)
            }
        }
        return listItems as ArrayList<Map<String, Any>>
    }

}