package sunsun.xiaoli.jiarebang.utils

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Bundle
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.itboye.pondteam.utils.Const
import sunsun.xiaoli.jiarebang.R
import sunsun.xiaoli.jiarebang.beans.StoreListBean

/**
 * MapHelper
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

class MapHelper {

    var descriptor: BitmapDescriptor?=null
    var array: List<out StoreListBean.ListEntity>? = null
    fun setPoint(activity: Activity, baiduMap: BaiduMap, array: List<out StoreListBean.ListEntity>) {
        this.array=array
        descriptor= BitmapDescriptorFactory
                .fromBitmap(BitmapFactory
                        .decodeResource(activity.resources,
                                R.drawable.location))
        for (listEntity in array) {
            val l = LatLng(listEntity.lat, listEntity.lng)
            val u = MapStatusUpdateFactory.newLatLngZoom(l, Const.zoom)
            baiduMap.animateMapStatus(u)
            val bundle = Bundle()
            bundle.putSerializable("model", listEntity)
            val mMarkerOptions = MarkerOptions().position(l).icon(descriptor).title(listEntity.name).draggable(true).zIndex(18).extraInfo(bundle)
            baiduMap.addOverlay(mMarkerOptions)
        }
    }
}
