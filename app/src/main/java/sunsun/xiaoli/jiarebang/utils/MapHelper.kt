package sunsun.xiaoli.jiarebang.utils

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Bundle
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.itboye.pondteam.utils.Const
import com.itboye.pondteam.utils.EmptyUtil.getSp
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

    var descriptor: BitmapDescriptor? = null
    var array: List<out StoreListBean.ListEntity>? = null
    fun setPoint(activity: Activity, baiduMap: BaiduMap, array: List<out StoreListBean.ListEntity>) {
        baiduMap.clear()
        this.array = array
        descriptor = BitmapDescriptorFactory
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

    fun setMyLocation(activity: Activity, baiduMap: BaiduMap) {
        baiduMap.clear()
        descriptor = BitmapDescriptorFactory
                .fromBitmap(BitmapFactory
                        .decodeResource(activity.resources,
                                R.drawable.location))
        if (getSp(Const.LOCATION_LAT).equals("") && getSp(Const.LOCATION_LNG).equals("")) {
            return
        }
        var lat: Double = getSp(Const.LOCATION_LAT).toDouble()
        var lng: Double = getSp(Const.LOCATION_LNG).toDouble()
        var ll = LatLng(lat, lng)
        var builder = MapStatus.Builder()
        //设置缩放中心点；缩放比例；
        builder.target(ll).zoom(18.0f)
        //给地图设置状态
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
        //构建MarkerOption，用于在地图上添加Marker
        val option = MarkerOptions()
                .position(ll)
                .icon(descriptor)
        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option)
    }


}
