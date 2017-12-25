package sunsun.xiaoli.jiarebang.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itboye.pondteam.bean.DeviceDetailModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by wsh on 2017/9/27.
 * TCP 长连接获取数据
 */

public class TcpUtil {

    private static final long HEART_BEAT_RATE = 3 * 100;

    public static final String HOST = "101.37.37.167";
    public static final int PORT = 8300;

    private String baseStr = "{\"t\": \"%1$s\",\"did\": \"%2$s\",\"token\": \"123456\",\"uid\": \"%3$s\"}";

    private String msg;
    private Handler handler;
    private WeakReference<Socket> mSocket;
    private ReadThread mReadThread;


    // For heart Beat
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);
        }
    };

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @param handler 更新UI通信
     * @param msg     0：did  1:uid  2:t
     */
    public TcpUtil(Handler handler, String... msg) {
        this.msg = String.format(baseStr, msg[2], msg[0], msg[1]);
//        "{\"t\": \""+msg[3]+"\",\"did\": \""+msg[0]+"\",\"token\": \"123456\",\"uid\": \""+msg[1]+"\"}";
        this.handler = handler;

    }

    public void start() {

//        initSocket();
        new InitSocketThread().start();
    }

    class InitSocketThread extends Thread {
        @Override
        public void run() {
            super.run();
            initSocket();
        }
    }

    private void initSocket() {
        try {
            Socket so = new Socket(HOST, PORT);
            mSocket = new WeakReference<Socket>(so);
            mReadThread = new ReadThread(so);
            mReadThread.start();
            mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//初始化成功后，就准备发送心跳包
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("TCP 接收数据 error" + e.getMessage());
//            mReadThread.interrupt();
            mReadThread.release();
        }
    }

    private long sendTime = 0L;
    private Runnable heartBeatRunnable = new Runnable() {

        @Override
        public void run() {
            if (System.currentTimeMillis() - sendTime >= HEART_BEAT_RATE) {
                boolean isSuccess = sendMsg(TcpUtil.this.msg);//就发送一个\r\n过去 如果发送失败，就重新初始化一个socket
                if (!isSuccess) {
                    mHandler.removeCallbacks(heartBeatRunnable);
                    mReadThread.release();
                    releaseLastSocket(mSocket);
                    initSocket();
                }

            }
            mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);
//            (this, HEART_BEAT_RATE);
        }
    };

    public void releaseTcp() {
        releaseLastSocket(mSocket);
        mHandler.removeCallbacks(heartBeatRunnable);
        mReadThread.release();
    }

    Socket soc;
    OutputStream os;
    String message;

    public boolean sendMsg(String msg) {
        if (null == mSocket || null == mSocket.get()) {
            return false;
        }
        soc = mSocket.get();
        try {
            if (!soc.isClosed() && !soc.isOutputShutdown()) {
                os = soc.getOutputStream();
                message = msg + "\r\n";
                //不加Thread  android7.0報錯
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            os.write(message.getBytes());
                            os.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("TCP 接收数据  数据发送失败");
                            //Sockect数据发送失败，则重新初始化Socket建立连接
                            initSocket();
                        }
                    }
                }.start();
                sendTime = System.currentTimeMillis();//每次发送成数据，就改一下最后成功发送的时间，节省心跳间隔时间
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void releaseLastSocket(WeakReference<Socket> mSocket) {
        try {
            if (null != mSocket) {
                Socket sk = mSocket.get();
                if (sk != null) {
                    if (!sk.isClosed()) {
                        sk.close();
                    }
                }
                sk = null;
                mSocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Thread to read content from Socket
    class ReadThread extends Thread {
        private WeakReference<Socket> mWeakSocket;
        private boolean isStart = true;

        public ReadThread(Socket socket) {
            mWeakSocket = new WeakReference<Socket>(socket);
        }

        public void release() {
            isStart = false;
            releaseLastSocket(mWeakSocket);
        }

        @SuppressLint("NewApi")
        @Override
        public void run() {
            super.run();
            Socket socket = mWeakSocket.get();
            if (null != socket) {
                try {
                    InputStream is = socket.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int length = 0;
                    while (!socket.isClosed() && !socket.isInputShutdown()
                            && isStart && ((length = is.read(buffer)) != -1)) {
                        if (length > 0) {
                            String data = new String(Arrays.copyOf(buffer,
                                    length)).trim();
                            //收到服务器过来的消息，就通过Broadcast发送出去
                                //其他消息回复
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    Gson gson = new Gson();
                                    int tCode = -1;
                                    Message message = new Message();
                                    message.arg1 = tCode;
                                    if (jsonObject.has("t")) {
                                        tCode = jsonObject.getInt("t");
                                        message.arg1 = tCode;
                                        switch (tCode) {
                                            case 101:
                                                message.obj = data;
                                                break;
                                            case 102:
                                                Type type = new TypeToken<DeviceDetailModel>() {
                                                }.getType();
                                                String dData = jsonObject.getString("d");
                                                DeviceDetailModel detailModel = gson.fromJson(dData, type);
                                                message.obj = detailModel;
                                                handler.sendMessage(message);
                                                break;
                                        }
                                    } else {
                                        message.obj = data;
                                        message.arg2=-1;
                                        handler.sendMessage(message);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
