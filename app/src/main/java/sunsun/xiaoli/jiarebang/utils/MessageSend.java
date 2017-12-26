package sunsun.xiaoli.jiarebang.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itboye.pondteam.bean.DeviceDetailModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

public class MessageSend {
    private static MessageSend instanca;
    private final String TAG = "NFC";
    private Socket socket;
    private boolean flag = true;
    private String baseStr = "{\"t\": \"%1$s\",\"did\": \"%2$s\",\"token\": \"123456\",\"uid\": \"%3$s\"}";

    private Handler handler;
    private String msg;
    public static final String HOST = "101.37.37.167";
    public static final int PORT = 8300;

    public synchronized static MessageSend getInstanca() {
        if (instanca == null) {
            instanca = new MessageSend();
        }
        return instanca;
    }

    public MessageSend connecSocket() {
        if (socket != null)
            return this;
        SendThread sThread = new SendThread(HOST, PORT);
        sThread.start();
        return this;
    }
    public MessageSend buildData(Handler handler, String... msg) {
        this.msg = String.format(baseStr, msg[2], msg[0], msg[1]);
        this.handler = handler;
        return this;
    }

    public void sendProtocol() {
        if (socket != null) {
            try {
                OutputStream os = socket.getOutputStream();
                os.write(this.msg.getBytes("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void sendProtocol(byte[] b) {
        if (socket != null) {
            try {
                OutputStream os = socket.getOutputStream();
                os.write(b);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeSocket() {
        if (socket != null) {
            try {
                flag = false;
                socket.close();
                socket = null;
                Log.d(TAG, "断开服务器连接");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class SendThread extends Thread {
        String ip;
        int potr;

        public SendThread(String ip, int port) {
            // TODO Auto-generated constructor stub
            this.ip = ip;
            this.potr = port;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            socket = new Socket();
            try {
                byte buffer[] = new byte[258];
                socket.connect(new InetSocketAddress(ip, potr), 5000);
                Log.d(TAG, "连上服务器：" + ip + ",端口：" + potr);
                flag = true;
                while (flag) {
                    InputStream isInputStream = socket.getInputStream();
                    int lenth = isInputStream.read(buffer);
                    if (lenth <= 0) {
                        socket.close();
                        return;
                    }
                    String data = new String(Arrays.copyOf(buffer,
                            lenth)).trim();
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
                            message.arg2 = -1;
                            handler.sendMessage(message);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public final String byte2hex(byte b[]) {
        if (b == null) {
            throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
        }

        String hs = "";
        String stmp = "";
        int len = 0;
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xff);
            if (n == 2)
                len = Integer.parseInt(stmp, 16) + 4;
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            if (len > 0 && len == n + 1)
                break;
        }
        return hs.toUpperCase();
    }

}
