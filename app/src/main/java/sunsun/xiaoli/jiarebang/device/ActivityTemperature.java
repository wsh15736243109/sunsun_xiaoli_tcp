package sunsun.xiaoli.jiarebang.device;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.TemperatureHistoryBean;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.custom.HistoryWaterTemperatureView;
import sunsun.xiaoli.jiarebang.utils.ScreenUtil;

import static com.itboye.pondteam.utils.CaculateDays.IsToday2;
import static com.itboye.pondteam.utils.CaculateDays.cacaulateHours;
import static com.itboye.pondteam.utils.CaculateDays.cacaulateTotalDays;
import static com.itboye.pondteam.utils.CaculateDays.formatTimesYMD2MD;
import static com.itboye.pondteam.utils.CaculateDays.formatTimesYMDH2H;
import static com.itboye.pondteam.utils.NumberUtils.parse2AnyFloat;
import static com.itboye.pondteam.utils.NumberUtils.parse2Float;
import static com.itboye.pondteam.volley.TimesUtils.localToUTC;
import static com.itboye.pondteam.volley.TimesUtils.utc2Local;

/**
 * Created by Mr.w on 2017/3/4.
 */

public class ActivityTemperature extends BaseActivity implements Observer {
    ImageView img_back;
    HistoryWaterTemperatureView temperatureView;
    LinearLayout viewContainer;
    TextView txt_day, txt_week, txt_month, txt_title;
    TextView txt_type;
    String did;
    UserPresenter userPresenter;
    int dataType = 1;
    int topValue, bottomValue;
    TextView txt_ph_max, txt_ph_min, txt_ph_avg;
    TextView txt_ph_max_time, txt_ph_min_time;
    TextView txt_ph_max_value, txt_ph_min_value, txt_ph_avg_value;
    private ArrayList<TemperatureHistoryBean> temperatureHistoryBeanArrayList = new ArrayList<>();
    boolean isPh = false;
    private String pattern = "yyyyMMddHH";
    private String theLastDate;
    private String theIndexDate;
    private int count;
    private int totalDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        txt_title.setText(getIntent().getStringExtra("title"));
        did = getIntent().getStringExtra("did");
        try {
            topValue = (int) Double.parseDouble(getIntent().getStringExtra("topValue"));
        } catch (Exception e) {
            topValue = 0;
        }
        try {
            bottomValue = (int) Double.parseDouble(getIntent().getStringExtra("bottomValue"));
        } catch (Exception e) {
            bottomValue = 0;
        }
        showProgressDialog(getString(R.string.requesting), true);
        isPh = getIntent().getBooleanExtra("isPh", false);
        userPresenter = new UserPresenter(this);
        dataType = 1;
//        txt_type.setText(getString(R.string.trend_24hours));
        if (did != null) {
            setCurrentItem(dataType);
        }

    }

    float max, min, avg;
    int maxPosition, minPosition;
    ArrayList<TemperatureHistoryBean> arCopy = new ArrayList<>();

    /**
     * 提出虚假数据，求出真实数据的最大值最小值平均值
     *
     * @param ara
     * @return
     */
    public int getMax(ArrayList<TemperatureHistoryBean> ara) {
        if (!isPh) {
            arCopy = new ArrayList<>();
            ArrayList<TemperatureHistoryBean> arrayCopry = ara;
            for (TemperatureHistoryBean temperatureHistoryBean : arrayCopry) {
                if (temperatureHistoryBean.isSimulated() == false) {
                    arCopy.add(temperatureHistoryBean);
                }
            }
            if (arCopy.size() > 0) {
                max = Float.parseFloat(arCopy.get(0).getAvg_temp()) / 10;
                min = Float.parseFloat(arCopy.get(0).getAvg_temp()) / 10;
                float total = 0;
                for (int i = 0; i < arCopy.size(); i++) {
                    if (max <= Float.parseFloat(arCopy.get(i).getAvg_temp()) / 10) {
                        max = Float.parseFloat(arCopy.get(i).getAvg_temp()) / 10;
                        maxPosition = i;
                    }
                    if (min >= Float.parseFloat(arCopy.get(i).getAvg_temp()) / 10) {
                        min = Float.parseFloat(arCopy.get(i).getAvg_temp()) / 10;
                        minPosition = i;
                    }
                    total += Float.parseFloat(arCopy.get(i).getAvg_temp());
                }
                avg = total / arCopy.size();
            }
            max = max == 0 ? 35 : max;
        } else {
            arCopy = new ArrayList<>();
            ArrayList<TemperatureHistoryBean> arrayCopry = ara;
            for (TemperatureHistoryBean temperatureHistoryBean : arrayCopry) {
                if (temperatureHistoryBean.isSimulated() == false) {
                    arCopy.add(temperatureHistoryBean);
                }
            }
            if (arCopy.size() > 0) {
                max = Float.parseFloat(arCopy.get(0).getAvg_ph()) / 100;
                min = Float.parseFloat(arCopy.get(0).getAvg_ph()) / 100;
                float total = 0;
                for (int i = 0; i < arCopy.size(); i++) {
                    if (max <= Float.parseFloat(arCopy.get(i).getAvg_ph()) / 100) {
                        max = Float.parseFloat(arCopy.get(i).getAvg_ph()) / 100;
                        maxPosition = i;
                    }
                    if (min >= Float.parseFloat(arCopy.get(i).getAvg_ph()) / 100) {
                        min = Float.parseFloat(arCopy.get(i).getAvg_ph()) / 100;
                        minPosition = i;
                    }
                    total += Float.parseFloat(arCopy.get(i).getAvg_ph()) / 100;
                }
                avg = total / arCopy.size();
            }
            max = max == 0 ? 14 : max;
        }
        return (int) max;
    }

    @Override
    public void onClick(View v) {
        dataType = -1;
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_day:
                showProgressDialog(getString(R.string.requesting), true);
                dataType = 1;
                break;
            case R.id.txt_week:
                showProgressDialog(getString(R.string.requesting), true);
                dataType = 2;
                break;
            case R.id.txt_month:
                showProgressDialog(getString(R.string.requesting), true);
                dataType = 3;
                break;
        }
        if (dataType != -1) {
            setCurrentItem(dataType);
        }
    }

    public void setCurrentItem(int index) {
        temperatureView = new HistoryWaterTemperatureView(this);
        temperatureView.setIsUpdate = true;
        setUnselected();
        switch (index) {
            case 1:
                txt_day.setBackgroundColor(getResources().getColor(R.color.shenlv));
                txt_week.setBackgroundColor(getResources().getColor(R.color.gray_a1));
                txt_month.setBackgroundColor(getResources().getColor(R.color.gray_a1));
                if (isPh) {
                    txt_type.setText(getString(R.string.trendofph_hourof24));
                } else {
                    txt_type.setText(getString(R.string.trendoftemperature_hourof24));

                }
                break;
            case 2:
                txt_day.setBackgroundColor(getResources().getColor(R.color.gray_a1));
                txt_week.setBackgroundColor(getResources().getColor(R.color.shenlv));
                txt_month.setBackgroundColor(getResources().getColor(R.color.gray_a1));
                if (isPh) {
                    txt_type.setText(getString(R.string.trendofph_lastweek));
                } else {
                    txt_type.setText(getString(R.string.trendoftemperature_lastweek));
                }
                break;
            case 3:
                txt_day.setBackgroundColor(getResources().getColor(R.color.gray_a1));
                txt_week.setBackgroundColor(getResources().getColor(R.color.gray_a1));
                txt_month.setBackgroundColor(getResources().getColor(R.color.shenlv));
                if (isPh) {
                    txt_type.setText(getString(R.string.trendofph_last30days));
                } else {
                    txt_type.setText(getString(R.string.trendoftemperature_last30days));
                }

                break;
        }
        userPresenter.getHistoryTemper(did, dataType + "", isPh);
    }

    private void setUnselected() {
        txt_day.setBackgroundColor(getResources().getColor(R.color.gray_a1));
        txt_week.setBackgroundColor(getResources().getColor(R.color.gray_a1));
        txt_month.setBackgroundColor(getResources().getColor(R.color.gray_a1));
    }


    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        try {
            closeProgressDialog();
        } catch (Exception e) {

        }
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == UserPresenter.getHistoryTemper_success) {
                temperatureHistoryBeanArrayList = (ArrayList<TemperatureHistoryBean>) entity.getData();
                Collections.reverse(temperatureHistoryBeanArrayList);
                viewContainer.removeAllViews();
                //补充数据
                supplementData();
                temperatureView.setAtivity(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(180 * temperatureHistoryBeanArrayList.size(), ScreenUtil.getPhoneSize(this)[1] / 2);
                viewContainer.addView(temperatureView, layoutParams);
                //qiu
                getMax(temperatureHistoryBeanArrayList);
                temperatureView.setIsPh(isPh);
                temperatureView.setDataType(dataType);
                temperatureView.setMaxY((int) max);//最大Y值
                temperatureView.setWarnTemperature(topValue, bottomValue);//异常水温最大值,异常水温最小值
                temperatureView.setArray(temperatureHistoryBeanArrayList);//水温集合
                setOtherData(dataType);
            } else if (entity.getEventType() == UserPresenter.getHistoryTemper_fail) {
                MAlert.alert(getString(R.string.getDetail_fail));
                temperatureView.setIsUpdate = false;
            }
        }
    }

    private void supplementData() {
        if (temperatureHistoryBeanArrayList != null) {
            if (temperatureHistoryBeanArrayList.size() > 0) {
                switch (dataType) {
                    case 1:
                        //获取数据最后一条数据,找出最晚时间
                        int utcHoursOrDay = Integer.parseInt(temperatureHistoryBeanArrayList.get(temperatureHistoryBeanArrayList.size() - 1).getHis_date().substring(temperatureHistoryBeanArrayList.get(temperatureHistoryBeanArrayList.size() - 1).getHis_date().length() - 2, temperatureHistoryBeanArrayList.get(temperatureHistoryBeanArrayList.size() - 1).getHis_date().length()));
                        //结束时间的完整格式
                        theLastDate = (temperatureHistoryBeanArrayList.get(temperatureHistoryBeanArrayList.size() - 1).getHis_date());
                        //开始时间的完整格式
                        theIndexDate = (temperatureHistoryBeanArrayList.get(0).getHis_date());
                        //开始时间的小时部分
                        int indexHour = Integer.parseInt(theIndexDate.substring(theIndexDate.length() - 2, theIndexDate.length()));
                        //结束时间的小时部分
                        int hoursOrDay = Integer.parseInt(theLastDate.substring(theLastDate.length() - 2, theLastDate.length()));
                        //最后一个日期的完整格式（yyyyMMddHH）
                        String year = theIndexDate.substring(0, theIndexDate.length() - 2);
                        int lastDate = Integer.parseInt(theLastDate);
                        int indexDate = Integer.parseInt(theIndexDate);
                        for (int i = 0; i < temperatureHistoryBeanArrayList.size(); i++) {
                            try {
                                int totalHours = cacaulateHours(temperatureHistoryBeanArrayList.get(i + 1).getHis_date(), temperatureHistoryBeanArrayList.get(i).getHis_date());//算出总共差的小时
                                int count = 0;
                                indexDate++;
                                while (count < totalHours - 1) {
                                    count++;
                                    try {
                                        indexDate = Integer.parseInt(new SimpleDateFormat("yyyyMMddHH").format(new SimpleDateFormat("yyyyMMddHH").parse(indexDate + "")));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    TemperatureHistoryBean bean = new TemperatureHistoryBean();
                                    if (isPh) {
                                        bean.setAvg_ph("0");
                                    } else {
                                        bean.setAvg_temp("0");
                                    }
                                    bean.setDid("0");
                                    bean.setSimulated(true);//是否为模拟数据
                                    bean.setHis_date(indexDate + "");
                                    temperatureHistoryBeanArrayList.add(i, bean);
                                    System.out.println(indexDate + "添加进去的时间" + totalHours);
                                    i += 1;
                                    indexDate++;
                                }
                            } catch (Exception e) {

                            }
                        }
                        Collections.sort(temperatureHistoryBeanArrayList);
                        //数据补充至24小时(新规则，先补充与当前相差的小时 当时小时-数据最后一条的小时)
                        int currentHour = Integer.parseInt(localToUTC(new SimpleDateFormat("yyyyMMddHH").format(System.currentTimeMillis()), pattern, pattern));
                        String currentTime = localToUTC(new SimpleDateFormat("yyyyMMddHH").format(System.currentTimeMillis()), pattern, pattern);
                        int totalHours = cacaulateHours(currentTime, theLastDate);//算出总共差的小时
                        if (totalHours >= 24) {
                            totalHours = 24;
                        }
                        int diff = totalHours;
                        count = 0;
                        int tempoldHour = utcHoursOrDay;
                        while (count < diff) {
                            tempoldHour += 1;
                            TemperatureHistoryBean bean = new TemperatureHistoryBean();
                            if (isPh) {
                                bean.setAvg_ph("0");
                            } else {
                                bean.setAvg_temp("0");
                            }
                            bean.setDid("0");
                            bean.setSimulated(true);//是否为模拟数据
                            bean.setHis_date(year + tempoldHour);
                            temperatureHistoryBeanArrayList.add(bean);
                            count++;
                        }
                        if (temperatureHistoryBeanArrayList.size() < 24) {
                            while (temperatureHistoryBeanArrayList.size() < 24) {
                                indexHour = indexHour - 1;
                                TemperatureHistoryBean bean = new TemperatureHistoryBean();
                                if (isPh) {
                                    bean.setAvg_ph("0");
                                } else {
                                    bean.setAvg_temp("0");
                                }
                                bean.setDid("0");
                                bean.setSimulated(true);//是否为模拟数据
                                bean.setHis_date(year + indexHour);

                                System.out.println(year + indexHour + "头部添加进去的时间" + indexHour);
                                temperatureHistoryBeanArrayList.add(0, bean);
                            }
                        } else {

                        }

                        break;
                    case 2:
                        //其中相差了天数的，补齐
                        String theLastDate2 = utc2Local(temperatureHistoryBeanArrayList.get(temperatureHistoryBeanArrayList.size() - 1).getHis_date(), "yyyyMMdd", "yyyyMMdd");
                        String theIndexDate2 = utc2Local(temperatureHistoryBeanArrayList.get(0).getHis_date(), "yyyyMMdd", "yyyyMMdd");
                        lastDate = Integer.parseInt(theLastDate2);
                        indexDate = Integer.parseInt(theIndexDate2);
                        for (int i = 0; i < temperatureHistoryBeanArrayList.size(); i++) {
                            try {
                                int totalDays = cacaulateTotalDays(temperatureHistoryBeanArrayList.get(i + 1).getHis_date(), temperatureHistoryBeanArrayList.get(i).getHis_date());//算出总共差的天数
                                count = 0;
                                indexDate++;
                                while (count < totalDays - 1) {
                                    count++;
                                    try {
                                        indexDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("yyyyMMdd").parse(indexDate + "")));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    TemperatureHistoryBean bean = new TemperatureHistoryBean();
                                    if (isPh) {
                                        bean.setAvg_ph("0");
                                    } else {
                                        bean.setAvg_temp("0");
                                    }
                                    bean.setDid("0");
                                    bean.setSimulated(true);//是否为模拟数据
                                    bean.setHis_date(indexDate + "");
                                    temperatureHistoryBeanArrayList.add(i, bean);
                                    System.out.println(indexDate + "添加进去的时间" + totalDays);
                                    i += 1;
                                    indexDate++;
                                }
                            } catch (Exception e) {

                            }
                        }
                        Collections.sort(temperatureHistoryBeanArrayList);
                        int indexDay2 = Integer.parseInt(theIndexDate2.substring(theIndexDate2.length() - 2, theIndexDate2.length()));
                        int hoursOrDay2 = Integer.parseInt(theLastDate2.substring(theLastDate2.length() - 2, theLastDate2.length()));
                        String year2 = theLastDate2.substring(0, theLastDate2.length() - 2);
                        //数据补充至7天(新规则，先补充最新的数据)
                        int currentDay = Integer.parseInt(new SimpleDateFormat("dd").format(System.currentTimeMillis()));
                        String currentTime2 = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
                        totalDays = cacaulateTotalDays(currentTime2, theLastDate2);//算出总共差的天数
                        if (totalDays >= 7) {
                            totalDays = 7;
                        }
                        int diffDay = totalDays;
                        int count2 = 0;
                        int copyhoursOrDay2 = hoursOrDay2;
                        while (count2 < diffDay) {
                            copyhoursOrDay2++;
                            TemperatureHistoryBean bean = new TemperatureHistoryBean();
                            if (isPh) {
                                bean.setAvg_ph("0");
                            } else {
                                bean.setAvg_temp("0");
                            }
                            bean.setDid("0");
                            bean.setSimulated(true);//是否为模拟数据
                            bean.setHis_date(year2 + copyhoursOrDay2);
                            temperatureHistoryBeanArrayList.add(bean);
                            count2++;
                        }
                        if (temperatureHistoryBeanArrayList.size() < 7) {
                            while (temperatureHistoryBeanArrayList.size() < 7) {
                                indexDay2 = indexDay2 - 1;
                                TemperatureHistoryBean bean = new TemperatureHistoryBean();
                                if (isPh) {
                                    bean.setAvg_ph("0");
                                } else {
                                    bean.setAvg_temp("0");
                                }
                                bean.setSimulated(true);//是否为模拟数据
                                bean.setDid("0");
                                bean.setHis_date(year2 + indexDay2);
                                temperatureHistoryBeanArrayList.add(0, bean);
                            }
                        } else {

                        }
                        break;
                    case 3:
                        String theLastDate3 = utc2Local(temperatureHistoryBeanArrayList.get(temperatureHistoryBeanArrayList.size() - 1).getHis_date(), "yyyyMMdd", "yyyyMMdd");
                        String theIndexDate3 = utc2Local(temperatureHistoryBeanArrayList.get(0).getHis_date(), "yyyyMMdd", "yyyyMMdd");
                        indexDate = Integer.parseInt(theIndexDate3);
                        for (int i = 0; i < temperatureHistoryBeanArrayList.size(); i++) {
                            try {


                                int totalDays = cacaulateTotalDays(temperatureHistoryBeanArrayList.get(i + 1).getHis_date(), temperatureHistoryBeanArrayList.get(i).getHis_date());//算出总共差的天数
                                count = 0;
                                indexDate++;
                                while (count < totalDays - 1) {
                                    count++;
                                    try {
                                        indexDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("yyyyMMdd").parse(indexDate + "")));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    TemperatureHistoryBean bean = new TemperatureHistoryBean();
                                    if (isPh) {
                                        bean.setAvg_ph("0");
                                    } else {
                                        bean.setAvg_temp("0");
                                    }
                                    bean.setDid("0");
                                    bean.setSimulated(true);//是否为模拟数据
                                    bean.setHis_date(indexDate + "");
                                    temperatureHistoryBeanArrayList.add(i, bean);
                                    System.out.println(indexDate + "添加进去的时间" + totalDays);
                                    i += 1;
                                    indexDate++;
                                }
                            } catch (Exception e) {

                            }
                        }
                        Collections.sort(temperatureHistoryBeanArrayList);
                        int indexDay3 = Integer.parseInt(theIndexDate3.substring(theIndexDate3.length() - 2, theIndexDate3.length()));
                        int hoursOrDay3 = Integer.parseInt(theLastDate3.substring(theLastDate3.length() - 2, theLastDate3.length()));
                        String year3 = theLastDate3.substring(0, theLastDate3.length() - 2);
                        //数据补充至7天(新规则，先补充最新的数据)
                        int currentDay3 = Integer.parseInt(new SimpleDateFormat("dd").format(System.currentTimeMillis()));
                        String currentTime3 = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
                        int totalDays3 = cacaulateTotalDays(currentTime3, theLastDate3);//算出总共差的小时
                        if (totalDays3 >= 30) {
                            totalDays3 = 30;
                        }
                        int diffDay3 = totalDays3;
                        int count3 = 0;
                        int copyhoursOrDay3 = hoursOrDay3;
                        while (count3 < diffDay3) {
                            copyhoursOrDay3++;
                            TemperatureHistoryBean bean = new TemperatureHistoryBean();
                            if (isPh) {
                                bean.setAvg_ph("0");
                            } else {
                                bean.setAvg_temp("0");
                            }
                            bean.setDid("0");
                            bean.setSimulated(true);//是否为模拟数据
                            bean.setHis_date(year3 + copyhoursOrDay3);
                            temperatureHistoryBeanArrayList.add(bean);
                            count3++;
                        }
                        if (temperatureHistoryBeanArrayList.size() < 30) {
                            while (temperatureHistoryBeanArrayList.size() < 30) {
                                indexDay3 = indexDay3 - 1;
                                TemperatureHistoryBean bean = new TemperatureHistoryBean();
                                if (isPh) {
                                    bean.setAvg_ph("0");
                                } else {
                                    bean.setAvg_temp("0");
                                }
                                bean.setSimulated(true);//是否为模拟数据
                                bean.setDid("0");
                                bean.setHis_date(year3 + indexDay3);
                                temperatureHistoryBeanArrayList.add(0, bean);
                            }
                        } else {

                        }
                        break;
                }
            } else {
                //任何数据都没有
                switch (dataType) {
                    case 1:
                        //数据补充至24小时(新规则，先补充与当前相差的小时 当时小时-数据最后一条的小时)
                        int currentHour = Integer.parseInt(new SimpleDateFormat("HH").format(System.currentTimeMillis())) - 8;
                        String currentTime = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
                        int totalHours = 24;
                        int diff = totalHours;
                        int count = 0;
                        while (count < diff) {
                            TemperatureHistoryBean bean = new TemperatureHistoryBean();
                            if (isPh) {
                                bean.setAvg_ph("0");
                            } else {
                                bean.setAvg_temp("0");
                            }
                            bean.setDid("0");
                            bean.setSimulated(true);//是否为模拟数据
                            bean.setHis_date((currentTime + currentHour));
                            temperatureHistoryBeanArrayList.add(bean);
                            currentHour -= 1;
                            count++;
                        }
                        break;
                    case 2:
                        //数据补充至7天(新规则，先补充最新的数据)
                        int currentDay = Integer.parseInt(new SimpleDateFormat("dd").format(System.currentTimeMillis()));
                        String yearAndMonth = new SimpleDateFormat("yyyyMM").format(System.currentTimeMillis());
                        int totalDays = 7;
                        int diffDay = totalDays;
                        int count2 = 0;
                        while (count2 < diffDay) {
                            TemperatureHistoryBean bean = new TemperatureHistoryBean();
                            if (isPh) {
                                bean.setAvg_ph("0");
                            } else {
                                bean.setAvg_temp("0");
                            }
                            bean.setDid("0");
                            bean.setSimulated(true);//是否为模拟数据
                            bean.setHis_date(yearAndMonth + currentDay);
                            temperatureHistoryBeanArrayList.add(bean);
                            currentDay--;
                            count2++;
                        }
                        break;
                    case 3:
                        //数据补充至7天(新规则，先补充最新的数据)
                        String yearAndMonth3 = new SimpleDateFormat("yyyyMM").format(System.currentTimeMillis());
                        int currentDay3 = Integer.parseInt(new SimpleDateFormat("dd").format(System.currentTimeMillis()));
                        int totalDays3 = 30;
                        int diffDay3 = totalDays3;
                        int count3 = 0;
                        while (count3 < diffDay3) {
                            TemperatureHistoryBean bean = new TemperatureHistoryBean();
                            if (isPh) {
                                bean.setAvg_ph("0");
                            } else {
                                bean.setAvg_temp("0");
                            }
                            bean.setDid("0");
                            bean.setSimulated(true);//是否为模拟数据
                            bean.setHis_date(yearAndMonth3 + currentDay3);
                            temperatureHistoryBeanArrayList.add(bean);
                            currentDay3--;
                            count3++;
                        }
                        break;
                }
                Collections.reverse(temperatureHistoryBeanArrayList);//数据翻转
            }
        }
    }

    private void setOtherData(int dataType) {
        Drawable drawable_yellow = getResources().getDrawable(R.drawable.wendu_yellow);
        Drawable drawable_green = getResources().getDrawable(R.drawable.jiarebang);
        Drawable drawable_blue = getResources().getDrawable(R.drawable.wendu_blue);
        /// 这一步必须要做,否则不会显示.
        drawable_yellow.setBounds(0, 0, drawable_yellow.getMinimumWidth(), drawable_yellow.getMinimumHeight());
        drawable_green.setBounds(0, 0, drawable_green.getMinimumWidth(), drawable_green.getMinimumHeight());
        drawable_blue.setBounds(0, 0, drawable_blue.getMinimumWidth(), drawable_blue.getMinimumHeight());
        boolean isTodayPhMax = false;
        boolean isTodayPhMin = false;
        boolean isTodayWendu = false;
        if (arCopy.size() > 0) {
            try {
                isTodayPhMax = IsToday2(arCopy.get(maxPosition).getHis_date());
                isTodayPhMin = IsToday2(arCopy.get(minPosition).getHis_date());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            switch (dataType) {
                case 1:
                    txt_ph_max_time.setText(formatTimesYMDH2H(arCopy.get(maxPosition).getHis_date()));
                    txt_ph_min_time.setText(formatTimesYMDH2H(arCopy.get(minPosition).getHis_date()));
                    break;
                case 2:
                case 3:
                    txt_ph_max_time.setText(formatTimesYMD2MD(arCopy.get(maxPosition).getHis_date()));
                    txt_ph_min_time.setText(formatTimesYMD2MD(arCopy.get(minPosition).getHis_date()));
                    break;
            }
            if (isPh) {
                if (dataType == 1) {
                    if (isTodayPhMax) {
                        txt_ph_max.setText(getString(R.string.maxValueofph_today));
                    } else {
                        txt_ph_max.setText(getString(R.string.maxValueOfPh_yesterday));
                    }
                    if (isTodayPhMin) {
                        txt_ph_min.setText(getString(R.string.minValueOfph_today));
                    } else {
                        txt_ph_min.setText(getString(R.string.minValueOfPh_yesterday));
                    }
                } else {
                    txt_ph_max.setText(getString(R.string.maxValuePh_history));
                    txt_ph_min.setText(getString(R.string.minValuePh_history));
                }
//                txt_ph_max.setText((dataType == 1 ? (isTodayPhMax ? "今天" : "昨天") : "历史") + "pH最高数值");
//                txt_ph_min.setText((dataType == 1 ? (isTodayPhMin ? "今天" : "昨天") : "历史") + "pH最低数值");
                txt_ph_avg.setText(getString(R.string.avgValuePh_history));
                txt_ph_max.setCompoundDrawables(drawable_yellow, null, null, null);
                txt_ph_min.setCompoundDrawables(drawable_blue, null, null, null);
                txt_ph_avg.setCompoundDrawables(drawable_green, null, null, null);
                txt_ph_max_value.setText(parse2Float(max + "", false));
                txt_ph_min_value.setText(parse2Float(min + "", false));
                txt_ph_avg_value.setText(parse2AnyFloat(avg + "", 1));
            } else {
                txt_ph_max.setCompoundDrawables(drawable_yellow, null, null, null);
                txt_ph_min.setCompoundDrawables(drawable_blue, null, null, null);
                txt_ph_avg.setCompoundDrawables(drawable_green, null, null, null);
                if (dataType == 1) {
                    if (isTodayPhMax) {
                        txt_ph_max.setText(getString(R.string.maxValueofTempterature_today));
                    } else {
                        txt_ph_max.setText(getString(R.string.maxValueofTemperature_yesterday));
                    }
                    if (isTodayPhMin) {
                        txt_ph_min.setText(getString(R.string.minValueofTemperature_today));
                    } else {
                        txt_ph_min.setText(getString(R.string.minValueofTemperature_yesterday));
                    }
                    txt_ph_avg.setText(getString(R.string.avgValueTemperature_24hours));
                } else {
                    txt_ph_max.setText(getString(R.string.maxValueofTemperature_history));
                    txt_ph_min.setText(getString(R.string.minValueofTemperature_history));
                    txt_ph_avg.setText(getString(R.string.avgValueTemperature_history));
                }
//                txt_ph_max.setText((dataType == 1 ? (isTodayPhMax ? "今天" : "昨天") : "历史") + "温度最高数值");
//                txt_ph_min.setText((dataType == 1 ? (isTodayPhMin ? "今天" : "昨天") : "历史") + "温度最低数值");
//                txt_ph_avg.setText((dataType == 1 ? ("24小时") : "历史") + "温度平均数值");
                txt_ph_max_value.setText(parse2Float(max + "", false) + "℃");
                txt_ph_min_value.setText(parse2Float(min + "", false) + "℃");
                txt_ph_avg_value.setText(parse2Float(avg + "", true) + "℃");
            }
        } else {
            if (isPh) {
                if (dataType == 1) {
                    txt_ph_max.setText(getString(R.string.maxValueofph_today));
                    txt_ph_min.setText(getString(R.string.minValueOfph_today));
                    txt_ph_avg.setText(getString(R.string.avgValuePh_24Hours));
                } else {
                    txt_ph_max.setText(getString(R.string.maxValuePh_history));
                    txt_ph_min.setText(getString(R.string.minValuePh_history));
                    txt_ph_avg.setText(getString(R.string.avgValuePh_history));
                }
//                txt_ph_max.setText((dataType == 1 ? "今天" : "历史") + "pH最高数值");
//                txt_ph_min.setText((dataType == 1 ? "今天" : "历史") + "pH最低数值");
//                txt_ph_avg.setText((dataType == 1 ? "24小时" : "历史") + "pH平均数值");
                txt_ph_max.setCompoundDrawables(drawable_yellow, null, null, null);
                txt_ph_min.setCompoundDrawables(drawable_blue, null, null, null);
                txt_ph_avg.setCompoundDrawables(drawable_green, null, null, null);
                txt_ph_max_value.setText(parse2Float(0 + "", false));
                txt_ph_min_value.setText(parse2Float(0 + "", false));
                txt_ph_avg_value.setText(parse2AnyFloat(0 + "", 1));
            } else {
                if (dataType == 1) {
                    txt_ph_max.setText(getString(R.string.maxValueofTempterature_today));
                    txt_ph_min.setText(getString(R.string.minValueofTemperature_today));
                    txt_ph_avg.setText(getString(R.string.avgValueTemperature_24hours));
                } else {
                    txt_ph_max.setText(getString(R.string.maxValueofTemperature_history));
                    txt_ph_min.setText(getString(R.string.minValueofTemperature_history));
                    txt_ph_avg.setText(getString(R.string.avgValueTemperature_history));
                }
                txt_ph_max.setCompoundDrawables(drawable_yellow, null, null, null);
                txt_ph_min.setCompoundDrawables(drawable_blue, null, null, null);
                txt_ph_avg.setCompoundDrawables(drawable_green, null, null, null);
//                txt_ph_max.setText((dataType == 1 ? "今天" : "历史") + "温度最高数值");
//                txt_ph_min.setText((dataType == 1 ? "今天" : "历史") + "温度最低数值");
//                txt_ph_avg.setText((dataType == 1 ? "24小时" : "历史") + "温度平均数值");
                txt_ph_max_value.setText(parse2Float(0 + "", false) + "℃");
                txt_ph_min_value.setText(parse2Float(0 + "", false) + "℃");
                txt_ph_avg_value.setText(parse2Float(0 + "", true) + "℃");
            }
        }
    }
}