package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.address;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sunsun.xiaoli.jiarebang.R;

import static sunsun.xiaoli.jiarebang.utils.Util.queryProvince;


public class AddressSelectView extends LinearLayout implements OnWheelChangedListener {

    //	private final String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/address";
    private String DB_PATH;// =App.ctx.getCacheDir().getCanonicalPath() + "/address";
    private final String DB_FILENAME = "itboye.db";

    private Context context;

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;

    // 数据库
    private SQLiteDatabase db;


    /**
     * 省份数组
     */
    protected String[] mProvinceDatas;

    /**
     * 市区数组
     */
    protected String[] mCityDatas;

    /**
     * 地区数组
     */
    protected String[] mDistrictDatas;

    List<ProvinceModel> provinceLv = new ArrayList<ProvinceModel>();

    List<CityModel> cityLv = new ArrayList<CityModel>();

    List<DistrictModel> districtLv = new ArrayList<DistrictModel>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProvinceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName;

    // 当前省份No
    protected int currentProvinceNo;

    // 当前市区No
    protected int currentCityNo;

    // 当前地区No
    protected int currentDistrictNo;

    public AddressSelectView(Context context) {
        this(context, null);
        init();
    }

    public AddressSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

//	 public AddressSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
//	        super(context, attrs, 0);
//			init();
//	 }
//	
//	 public AddressSelectView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//	        super(context, attrs, defStyleAttr,defStyleRes);
//			init();
//	 }

    /**
     * 解析省数据
     */
    protected void initProvinceDatas() {

        Cursor cursor = db.rawQuery("select * from common_province", null);
        while (cursor.moveToNext()) {
            ProvinceModel province = new ProvinceModel(cursor.getString(2), cursor.getInt(1));
            provinceLv.add(province);
        }
        mProvinceDatas = new String[provinceLv.size()];
        for (int i = 0; i < mProvinceDatas.length; i++) {
            mProvinceDatas[i] = provinceLv.get(i).getName();
        }
        cursor.close();
        currentProvinceNo = provinceLv.get(0).getNumber();
    }

    /**
     * 解析市数据
     */
    protected void initCityDatas() {
        Cursor cursor = db.rawQuery("select * from common_city", null);
        while (cursor.moveToNext()) {
            CityModel city = new CityModel(cursor.getString(2), cursor.getInt(1), cursor.getInt(3));
            cityLv.add(city);
        }
        cursor.close();
    }

    /**
     * 解析地区数据
     */
    protected void initDistrictDatas() {
        Cursor cursor = db.rawQuery("select * from common_area", null);
        while (cursor.moveToNext()) {
            DistrictModel district = new DistrictModel(cursor.getString(2), cursor.getInt(1), cursor.getInt(3));
            districtLv.add(district);
        }
        cursor.close();
    }

    private void init() {

        try {
            DB_PATH = getContext().getCacheDir().getCanonicalPath() + "/address";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        LayoutInflater.from(context).inflate(R.layout.fragment_address, this, true);
        setUpViews();
        setUpListener();
        db = openDataBase();
        setUpData();
        db.close();
    }

    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.province);
        mViewCity = (WheelView) findViewById(R.id.city);
        mViewDistrict = (WheelView) findViewById(R.id.district);
    }

    private void setUpListener() {
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);
    }

    public void setAreaVisible(boolean isVisible) {
        mViewDistrict.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setUpData() {
        initProvinceDatas();
        initCityDatas();
        initDistrictDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(context, mProvinceDatas));
//		// 设置可见条目数量
        mViewProvince.setVisibleItems(5);
        mViewCity.setVisibleItems(5);
        mViewDistrict.setVisibleItems(5);
        mViewProvince.setCurrentItem(0);
        updateCities();
        updateAreas();
        updateDistrict();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {

        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            updateDistrict();
        }
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProvinceName = mProvinceDatas[pCurrent];
        // 拿到当前省份编号
        for (ProvinceModel province : provinceLv) {
            if (province.getName() == mCurrentProvinceName) {
                currentProvinceNo = province.getNumber();
            }
        }
        if (mCurrentProvinceName.equals("重庆市") || mCurrentProvinceName.equals("北京市") || mCurrentProvinceName.equals("上海市") || mCurrentProvinceName.equals("天津市")) {
            mCityDatas = new String[]{mCurrentProvinceName};
            currentCityNo=currentProvinceNo;
            mCurrentCityName = mCurrentProvinceName;
            mViewCity.setViewAdapter(new ArrayWheelAdapter<>(context, mCityDatas));
            mViewCity.setCurrentItem(0);
        } else {
             // 该省份下的城市list
            List<String> tempCityStr = new ArrayList<String>();
            for (CityModel city : cityLv) {
                if (city.getFatherNum() == currentProvinceNo) {
                    tempCityStr.add(city.getName());
                }
            }
            if (tempCityStr.size() > 0) {
                mCityDatas = new String[tempCityStr.size()];
                for (int i = 0; i < tempCityStr.size(); i++) {
                    mCityDatas[i] = tempCityStr.get(i);
                }
                mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(context, mCityDatas));
                mViewCity.setCurrentItem(0);
                updateAreas();
            } else {
                mCityDatas = new String[]{""};
                mDistrictDatas = new String[]{""};
                currentCityNo = 0;
                currentDistrictNo = 0;
                mCurrentCityName = "";
                mCurrentDistrictName = "";
                mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(context, mCityDatas));
                mViewCity.setCurrentItem(0);
                mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context, mDistrictDatas));
                mViewDistrict.setCurrentItem(0);
            }
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        if (mCityDatas.length <= 0) {
            return;
        }
        int pCurrent = mViewCity.getCurrentItem();
        String tempCityName = mCityDatas[pCurrent];
        // 拿到当前城市编号与名称
        for (CityModel city : cityLv) {
            if ((city.getFatherNum() == currentProvinceNo) && (city.getName() == tempCityName)) {
                currentCityNo = city.getNumber();
                mCurrentCityName = city.getName();
            }
        }
        // 该城市下的地区list
        List<String> tempDistrictStr = new ArrayList<String>();
        for (DistrictModel district : districtLv) {
            if (district.getFatherNum() == currentCityNo) {
                tempDistrictStr.add(district.getName());
            }
        }
        mDistrictDatas = new String[tempDistrictStr.size()];
        for (int i = 0; i < tempDistrictStr.size(); i++) {
            mDistrictDatas[i] = tempDistrictStr.get(i);
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context, mDistrictDatas));
        mViewDistrict.setCurrentItem(0);
        updateDistrict();
    }

    /**
     * 根据当前的地区，拿到地区信息
     */
    private void updateDistrict() {
        if (mDistrictDatas.length <= 0) {
            return;
        }
        int pCurrent = mViewDistrict.getCurrentItem();
        String tempDistrict = mDistrictDatas[pCurrent];
        // 拿到当前地区编号和名称
        for (DistrictModel district : districtLv) {
            if ((district.getFatherNum() == currentCityNo) && (district.getName() == tempDistrict)) {
                currentDistrictNo = district.getNumber();
                mCurrentDistrictName = district.getName();
            }
        }
    }

    /**
     * 获取当前位置信息
     */
    public String getAddressData() {
        return mCurrentProvinceName + mCurrentCityName + mCurrentDistrictName;
    }

    //s省
    public String getProvinceName() {
        return mCurrentProvinceName;
    }

    //市
    public String getCityName() {
        return mCurrentCityName;
    }

    //区
    public String getDistrictName() {
        return mCurrentDistrictName;
    }

    /**
     * 获取当前省份编号
     */
    public int getProvinceNo() {
        return currentProvinceNo;
    }

    /**
     * 获取当前市区编号
     */
    public int getCityNo() {
        return currentCityNo;
    }

    /**
     * 获取当前地区编号
     */
    public int getDistrictNo() {
        return currentDistrictNo;
    }

    private SQLiteDatabase openDataBase() {
        try {
            String dbFileName = DB_PATH + "/" + DB_FILENAME;
            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!new File(DB_PATH, DB_FILENAME).exists()) {
                new File(DB_PATH, DB_FILENAME).createNewFile();
                InputStream is = getResources().openRawResource(R.raw.itboye);
                FileOutputStream fos = new FileOutputStream(dbFileName);
                byte[] buffer = new byte[8192];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            db = SQLiteDatabase.openOrCreateDatabase(dbFileName, null);
            return db;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setProvince(String provinceName) {
        CityModel cityModel = queryProvince(provinceName);
        mViewProvince.setCurrentItem(cityModel.getId() - 1);
    }

    public void setCityName(String cityName) {
        int position = 0;
        //查出list中的索引下标
        for (int i = 0; i < mCityDatas.length; i++) {
            if (cityName.equals(mCityDatas[i])) {
                position = i;
                break;
            }
        }
        mViewCity.setCurrentItem(position);
    }
}
