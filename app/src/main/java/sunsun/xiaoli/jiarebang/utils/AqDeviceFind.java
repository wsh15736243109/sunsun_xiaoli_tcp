package sunsun.xiaoli.jiarebang.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.itboye.pondteam.app.MyApplication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import ChirdSdk.CHD_LocalScan;
import sunsun.xiaoli.jiarebang.BuildConfig;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.SearchDeviceInfo;
import sunsun.xiaoli.jiarebang.utils.wifiutil.AqSmartConfig;

/**
 */
public class AqDeviceFind {
    public DatagramSocket mUdpSocket = null;
    public int mUdpPort = 0;
    public Thread mThread = null;
    private boolean mThreadIsRun = false;
    public Handler mHandler;
    private MulticastSocket sender;
    private SearchDeviceInfo searchDeviceInfo;
    String req = "{\"req\":\"device_info\"}";
    int port = 0;
    private Thread myThread = null;
    private CHD_LocalScan mScan;

    public AqDeviceFind(Handler handler) {
        mHandler = handler;
    }

    public AqDeviceFind() {
    }

    public boolean isRun() {
        if (mThread == null) {
            return false;
        }
        return mThreadIsRun;
    }

    public boolean isRunMyThread() {
        if (mThread == null) {
            return false;
        }
        return mThreadIsRun;
    }

    /**
     * 开始查找设备
     *
     * @param port 监听端口，默认15151
     * @return 已经运行或者运行失败返回false，成功返回true
     */
    public boolean start(int port) {
        this.port = port;
        //搜索摄像头
        if (!BuildConfig.APP_TYPE.equals("森森新零售")) {
            chirdDeviceSearch();
        }
        //其他设备
        new udpBroadCast(req, port).start();
        return true;
    }

    DatagramPacket dj;
    Gson gson = new Gson();

    /* 发送udp多播 */
    private class udpBroadCast extends Thread {
        InetAddress group = null;
        byte[] data = new byte[1024];
        int port = 0;

        public udpBroadCast(String dataString, int port) {
            data = dataString.getBytes();
            this.port = port;
        }

        @Override
        public void run() {
//            while (true) {
//                try {
//                    try {
//                        sender = new MulticastSocket();
//                        group = InetAddress.getByName("255.255.255.255");
//                        dj = new DatagramPacket(data, data.length, group, port);
//                        sender.send(dj);
////				sender.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
////                    sender = new MulticastSocket();
//                    DatagramPacket dpIn = null;
//                    byte[] bufferIn = new byte[256];
//                    dpIn = new DatagramPacket(bufferIn, bufferIn.length);
//                    sender.receive(dpIn);
//                    System.out.println(">>>发现设备4");
//                    String packData = new String(bufferIn, 0, bufferIn.length, "gb2312");
//                    searchDeviceInfo = gson.fromJson(packData.replaceAll("\u0000", ""), SearchDeviceInfo.class);
//                    Message message = new Message();
//                    message.obj = searchDeviceInfo;
//                    sendMessage(message);
//                    sender.close();
//                    SystemClock.sleep(3000);
//                } catch (Exception e) {
//                    sender.close();
//                    SystemClock.sleep(3000);
//                }
//            }
            // 变量
//            byte[] datar = new byte[1024];
            // 判断是否处于局域网
            String gwip = new AqSmartConfig(MyApplication.getInstance()).getGateway();
            if (gwip == null) {
                return;
            }
            String[] gwips = gwip.split(".");
            // 接收循环
            while (!Thread.currentThread().isInterrupted()) {
                DatagramPacket dpr = null;
                // 创建UDP
                    if (App.getInstance().isStartSearch) {
                        System.out.println(">>>发现设备1");
                        try {
//                    String ipFront = gwip.substring(0,gwip.lastIndexOf(".")+1);
//                    for (int i = 0; i <= 255; i++) {
//                        group = InetAddress.getByName(ipFront + i);
//                        System.out.println("发现设备3" + ipFront + i);
//                        dpr = new DatagramPacket(data, data.length, group, port);
////                    dpr = new DatagramPacket(data, data.length);
//                        mUdpSocket = new DatagramSocket();
////                        mUdpSocket.setBroadcast(true);
//                        mUdpSocket.setSoTimeout(2000);
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        try {
//                            mUdpSocket.send(dpr);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
                            group = InetAddress.getByName("255.255.255.255");
                            dpr = new DatagramPacket(data, data.length, group, port);
                            mUdpSocket = new DatagramSocket();
                            mUdpSocket.setBroadcast(true);
                            mUdpSocket.setSoTimeout(2000);
                            try {
                                mUdpSocket.send(dpr);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("发现设备2");
                        } catch (SocketException e) {
                            if (mThread != null) {
                                mThread.interrupt();
                            }
                        } catch (UnknownHostException e) {
                            if (mThread != null) {
                                mThread.interrupt();
                            }
                        }
                        try {
                            byte[] bufferIn = new byte[256];
                            DatagramPacket dpIn = new DatagramPacket(bufferIn, bufferIn.length);
                            mUdpSocket.receive(dpIn);
                            String packData = new String(bufferIn, 0, bufferIn.length, "gb2312");
                            packData = packData.replaceAll("\u0000", "");
                            searchDeviceInfo = gson.fromJson(packData, SearchDeviceInfo.class);
                            Message message = new Message();
                            message.obj = searchDeviceInfo;
                            sendMessage(message);
                            mUdpSocket.close();
                            if (mThread != null) {
                                mThread.interrupt();
                            }
                            Thread.sleep(1000);
                            System.out.println("发现设备4" + packData);
                        } catch (Exception e) {
                            e.printStackTrace();
                            mUdpSocket.close();
                            if (mThread != null) {
                                mThread.interrupt();
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            System.out.println("发现设备异常");
                        }
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
            }
        }
    }

    private void chirdDeviceSearch() {
        try {
            locanScanListener();
        } catch (Exception e) {

        }
    }

    Handler mTimeHandler;
    Runnable mTimeRunnable;

    private void locanScanListener() {
        mScan = new CHD_LocalScan();

        mTimeHandler = new Handler();
        mTimeRunnable = new Runnable() {
            public void run() {
                int Num = mScan.getLocalScanNum();
                for (int i = 0; i < Num; i++) {
                    Log.v("摄像头设备搜索",
                            "Num = " + i + "Address:"
                                    + mScan.getLocalDevIpAddress(i) + " did:"
                                    + mScan.getLocalDevDid(i) + " passwd:"
                                    + mScan.getLocalDevPasswd(i));
                }

                if (Num > 0) {
                    searchDeviceInfo = new SearchDeviceInfo();
                    searchDeviceInfo.setDid(mScan.getLocalDevDid(0));
                    searchDeviceInfo.setPwd(mScan.getLocalDevPasswd(0));
                    searchDeviceInfo.setType("SCHD");
                    Message message = new Message();
                    message.obj = searchDeviceInfo;
                    sendMessage(message);
                }

                mTimeHandler.postDelayed(this, 2000);
            }
        };
        mTimeHandler.postDelayed(mTimeRunnable, 2000);
    }

    private void sendMessage(Message msg) {
        if (mHandler != null) {
            mHandler.sendMessage(msg);
        }
    }

    public boolean stop() {
        if (isRun()) {
            mThread.interrupt();
            try {
                mThread.join();
            } catch (InterruptedException e) {
            }
            return true;
        }
        return false;
    }

//    public boolean stopSearch() {
//        if (handler != null) {
//            handler.removeCallbacks(runnable);
//        }
//        return false;
//    }
}
