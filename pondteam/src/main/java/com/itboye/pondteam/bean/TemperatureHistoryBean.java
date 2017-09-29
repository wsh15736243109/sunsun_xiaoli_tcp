package com.itboye.pondteam.bean;

import android.graphics.PointF;

/**
 * Created by Administrator on 2017/4/12.
 */
public class TemperatureHistoryBean implements Comparable<TemperatureHistoryBean> {
    private String his_date;
    private String did;
    private String avg_temp;
    private String avg_ph;

    public String getAvg_ph() {
        return avg_ph;
    }

    public void setAvg_ph(String avg_ph) {
        this.avg_ph = avg_ph;
    }

    boolean simulated = false;

    public boolean isSimulated() {
        return simulated;
    }

    public void setSimulated(boolean simulated) {
        this.simulated = simulated;
    }

    private PointF pointF;

    public PointF getPointF() {
        return pointF;
    }

    public void setPointF(PointF pointF) {
        this.pointF = pointF;
    }

    public String getHis_date() {
        return his_date;
    }

    public void setHis_date(String his_date) {
        this.his_date = his_date;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getAvg_temp() {
        return avg_temp;
    }

    public void setAvg_temp(String avg_temp) {
        this.avg_temp = avg_temp;
    }

    @Override
    public int compareTo(TemperatureHistoryBean arg0) {
//        if(this.avg_ph.compareTo(arg0.avg_ph) == 0) {
        //此方法用于排序，根据时间大小
        return Integer.valueOf(his_date).compareTo(Integer.valueOf(arg0.his_date));
//        }else{
//            return this.avg_ph.compareTo(arg0.avg_ph);
//        }
    }
}

