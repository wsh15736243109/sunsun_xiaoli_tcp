package sunsun.xiaoli.jiarebang.utils.wifiutil;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by 赵武 on 2015/3/12. 日期：2015-05-02 1. 解决SSID获取可能缺少前后两个字符的问题
 */
public class AqSmartConfig {

    private DatagramSocket mUdp;
    private int mPort;
    private Thread mThread;
    private ArrayList<Integer> mPack;
    private String mGwip;
    private Context mContext;
    private boolean mThreadIsRun;

    public AqSmartConfig(Context context) {
        mContext = context;
        mThreadIsRun = false;
        mPack = null;
        mGwip = null;
        mThread = null;
        mPort = 15150;
        mUdp = null;
    }
    public AqSmartConfig() {

    }
    /**
     * 获取网关地址
     *
     * @return 成功返回网关地址
     */
    @SuppressWarnings("deprecation")
    public String getGateway() {
        WifiManager myWifi = (WifiManager) mContext
                .getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = myWifi.getDhcpInfo();
        String gwip = android.text.format.Formatter
                .formatIpAddress(dhcpInfo.gateway);
        Log.e("gwip", gwip);
        return gwip;
    }

    /**
     * 判断WiFi是否连接
     *
     * @return 连接状态
     */
    public boolean wifiIsConnect() {
        WifiManager myWifi = (WifiManager) mContext
                .getSystemService(Context.WIFI_SERVICE);
        if (myWifi.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前线程运行状态
     *
     * @return 运行中返回true，停止返回false
     */
    public boolean isRun() {
        if (mThread == null) {
            return false;
        }
        return mThreadIsRun;
    }

    /**
     * 构建数据
     *
     * @param data 要发送的数据内容
     * @param type 要发送的数据类型
     */
    private ArrayList<Integer> makeData(byte[] data, byte type) {
        ArrayList<Integer> pack = new ArrayList<Integer>();
        int start1 = 1121;
        int start2 = 1113;
        int base1 = 600;
        // 纠正数据
        if (data == null) {
            data = new byte[1];
            data[0] = 0x00;
        }
        if (data.length == 0) {
            data = new byte[1];
            data[0] = 0x00;
        }
        byte count = 0;
        byte prevValue = 0;
        byte[] packdata = new byte[data.length + 4];
        int sum = 0;
        System.arraycopy(data, 0, packdata, 2, data.length);
        packdata[0] = (byte) (((int) packdata.length) & 0x0ff);
        packdata[1] = type;
        packdata[data.length + 2] = 0x00;
        for (int i = 0; i < (data.length + 2); i++) {
            sum += (int) packdata[i];
        }
        packdata[data.length + 3] = (byte) (sum & 0xff);
        pack.add(start1);
        pack.add(start2);
        for (int i = 0; i < packdata.length; i++) {
            byte bh = (byte) (packdata[i] >> 4 & 0x0f);
            byte bl = (byte) (packdata[i] & 0x0f);
            int value = (int) (((count ^ prevValue) << 4) | (bh & 0x0f))
                    + base1;
            pack.add(value);
            count++;
            prevValue = bh;
            value = (int) (((count ^ prevValue) << 4) | (bl & 0x0f)) + base1;
            pack.add(value);
            count++;
            prevValue = bl;
            count %= 16;
        }

        Log.e("gwip", pack.size() + "");
        return pack;
    }

    /**
     * 构建包，包含SSID和KEY信息
     *
     * @param ssid 路由器的SSID字节集
     * @param key  路由器的密码字节集
     * @return
     */
    private ArrayList<Integer> makePacket(byte[] ssid, byte[] key) {
        ArrayList<Integer> pack = new ArrayList<Integer>();
        pack.addAll(makeData(ssid, (byte) 0x02));
        pack.addAll(makeData(key, (byte) 0x04));
        Log.e("ssid", ssid.length + ":" + key.length);
        return pack;
    }

    /**
     * 开始发送配置数据
     *
     * @param ssid 路由器的SSID
     * @param key  路由器的密码
     * @return 成功返回true，未连接WiFi的情况下返回false，已经处于配置状态返回false
     */
    public boolean start(String ssid, String key) {
        // 判断WIFI是否连接
        if (wifiIsConnect() == false) {
            return false;
        }
        // 判断是否处于局域网
        String gwip = getGateway();
        if (gwip == null) {
            return false;
        }
        // 判断是否正处于运行状态
        if (isRun()) {
            return false;
        }
        mThreadIsRun = true;
        //发出广播请求的数据内容：{"req":"device_info"}
        String str = "{\"req\":\"device_info\"}";
        // 构建数据包
        mPack = null;
        mPack = makePacket(ssid.getBytes(), key.getBytes());
        // 发送线程
        mGwip = gwip;
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 初始化
                byte[] txb = new byte[2048];
                for (int j = 0; j < txb.length; j++) {
                    txb[j] = (byte) '0';
                }
                InetAddress tagAddr1 = null;
                try {
                    mUdp = new DatagramSocket();
                    mUdp.setBroadcast(true);
                    mUdp.setSoTimeout(10);
                    tagAddr1 = InetAddress.getByName(mGwip);
                } catch (SocketException e) {
                    mThread.interrupt();
                } catch (UnknownHostException e) {
                    mThread.interrupt();
                }
                // 发送
                while (!mThread.isInterrupted()) {
                    for (int i = 0; i < mPack.size(); i++) {
                        DatagramPacket dp = new DatagramPacket(txb,
                                mPack.get(i), tagAddr1, mPort);
                        try {
                            mUdp.send(dp);
                        } catch (IOException e) {
                        }
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        // 关闭UDP
                        if (mUdp != null) {
                            mUdp.close();
                        }
                        mThreadIsRun = false;
                        return;
                    }
                }
                // 关闭UDP
                if (mUdp != null) {
                    mUdp.close();
                }
                mThreadIsRun = false;
            }
        });
        mThread.start();
        return true;
    }

    /**
     * 获取当前WiFi的SSID
     *
     * @return WiFi有效返回SSID，无效返回null
     */
    @SuppressWarnings("static-access")
    public String getCurrentWiFiSSID() {
        WifiManager wifiManager = (WifiManager) mContext
                .getSystemService(mContext.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = null;
        if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            ssid = wifiInfo.getSSID();
            if (ssid.length() > 2) {
                String s1 = ssid.substring(0, 1);
                String s2 = ssid
                        .substring(ssid.length() - 2, ssid.length() - 1);
                if (s1.compareTo("\"") == 0) {
                    if (s1.compareTo("\"") == 0) {
                        ssid = ssid.substring(1, ssid.length() - 1);
                    }
                }
            }
        }
        return ssid;
    }

    /**
     * 停止发送配置数据
     *
     * @return 成功返回true，失败说明已经停止或者线程不存在
     */
    public boolean stop() {
        // 终止接收线程
        if (isRun()) {
            try {
                mThread.interrupt();
                mThread.join();
                return true;
            } catch (InterruptedException e) {
            }
        }
        return false;
    }
}
