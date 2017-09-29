package sunsun.xiaoli.jiarebang.beans;

import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Queue;

//import com.itboye.sunsunhome.util.GetModelInfo;
//import com.itboye.sunsunhome.util.HttpRequest;
//import com.itboye.sunsunhome.volley.MyJsonRequest;
//import com.itboye.sunsunhome.volley.XErrorListener;
//import com.itboye.sunsunhome.volley.XRequestListener;
//import com.itboye.sunsunhome.www.aq.base.AqDeviceInfo.AqDeviceType;
//import com.p2p.pppp_api.PPPP_APIs;
//import com.starxnet.ipn.iPN_API;


/**
 * Created by 赵武 on 2015/3/9. 本类为核心通信类 日期：2015-04-30 1. 添加getDeviceVersion功能
 * 日期：2015-05-04 1. 解决数值转换时候引起的32KB的图像接收异常的BUG 日期：2015-05-12 1. 以多线程方式结束PPPP连接
 */
public class AqDevice {

	private Thread mConnectThread;
	private Thread mCommandReceiveThread;
	private Thread mVideoReceiveThread;
	private Thread mCommandTransmitThread;
	private Queue<CommandItem> mCommandQueue;
	private AqDevice mThis;
	private byte[] mNewReceiveData = null;
	private int mNewReceiveDataLength = 0;
	public Handler mHandler;
	public int mDeviceHandle = 0;

	public AqDeviceInfo mDeviceInfo;
	private boolean mConnectState = false;
	public AqPeriod mLampPeriod;
	public AqPeriod mPumpPeriod;
	public AqPeriod mUvLampPeriod;
	public AqFaultState mFaultState;
	public int mMaxFaultTemp;
	public int mMinFaultTemp;
	public int mMaxTemp;
	public int mCurrentTemp;
	public int mCurrentPh;
	public boolean mLampRelaysState;
	public boolean mPumpRelaysState;
	public boolean mUvLampRelaysState;
	public boolean mAutoModeState;
	public boolean mLockState;
	public AqDateTime mDateTime;
	public boolean mSubscribeTempState = false;
	public boolean mSubscribeLevelState = false;
	public boolean mSubscribeLoadState = false;
	public boolean mVideoState = false;
	public String mSubscribeTokenTemp = "";
	public String mSubscribeTokenLevel = "";
	public String mSubscribeTokenLoad = "";
	private Database mDatabase;
	private String mClientID;
	public boolean mInitState; // 是否完成基本信息初始化

	public String mMutexSubscribeThread;
	public String mMutexSubscribe;
	public String mMutexMcuState;
	public int lightW, shuiBengW, ZiwaixianW, XunhuanW;
	private JSONObject out;
	public enum ReadState {
		Ok, TimeOut, Error,
	}

	public enum Relays {
		Mode, Lamp, Pump, UvLamp
	}

	public enum SubscribeType {
		Temp, Level, Load,
	}

	public class CommandItem {
		public byte[] mData; // 数据包
		public int mDataLength; // 命令数据包长度
		public int mPackType; // 数据包类型
		public boolean mReturnMode; // 是否为返回模式
		public int mTimeoutMs; // 命令超时设置时间
		public int mMCUCommandType; // 当前操作的MCU命令类型
		public byte[] mReturnData; // 返回数据，只有在返回模式下有效
		public int mReturnDataLength; // 返回数据长度，只有在返回模式下有效
		public boolean mCommandIsOk; // 返回表示命令是否执行成功

		public CommandItem(byte[] data, int dataLength, boolean returnMode,
				int packType, int mcuCommandType, int timeoutMs) {
			mData = data;
			mReturnMode = returnMode;
			mPackType = packType;
			mDataLength = dataLength;
			mMCUCommandType = mcuCommandType;
			mTimeoutMs = timeoutMs;
			mReturnData = null;
			mReturnDataLength = 0;
			mCommandIsOk = false;
		}
	}

	public AqDevice(Handler handler, Database database, String clientID,
			AqDeviceInfo devInfo) {
		mClientID = clientID;
		mDatabase = database;
		mDeviceInfo = devInfo;
		mHandler = handler;
		mThis = this;
		mInitState = false;
		mCommandQueue = new LinkedList<CommandItem>();
		mLampPeriod = new AqPeriod();
		mPumpPeriod = new AqPeriod();
		mUvLampPeriod = new AqPeriod();
		mFaultState = new AqFaultState();
		mDateTime = new AqDateTime();
		try {
			mSubscribeTempState = mDatabase
					.getTempFaultConfig(mDeviceInfo.mDid);
			mSubscribeLevelState = mDatabase
					.getLevelFaultConfig(mDeviceInfo.mDid);
			mSubscribeLoadState = mDatabase
					.getLoadFaultConfig(mDeviceInfo.mDid);
		} catch (Exception e) {
		}
		mMutexSubscribeThread = new String("mMutexSubscribeThread");
		mMutexSubscribe = new String("mMutexSubscribe");
		mMutexMcuState = new String("mMutexMcuState");
	}

	public boolean getConnectState() {
		synchronized (mConnectThread) {
			return mConnectState;
		}
	}

	public void closeConnect() {
		synchronized (mConnectThread) {
			mConnectState = false;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
//					PPPP_APIs.PPPP_Close(mDeviceHandle);
				} catch (Exception e) {
				}
			}
		}).start();
	}

//	private void asyncReturn(CommandItem item) {
//		synchronized (mMutexMcuState) {
//			AqDeviceMessage msgObj = new AqDeviceMessage();
//			msgObj.mDevice = mThis;
//			msgObj.mCommandItem = item;
//			Message msg = new Message();
//			msg.obj = msgObj;
//			msg.what = MessageWhat.DeviceMessage;
//			if (!item.mCommandIsOk) {
//				if (item.mPackType == AqCommType.MJPEG_START) {
//					msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.VideoStartFailed;
//				} else if (item.mPackType == AqCommType.MJPEG_STOP) {
//					msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.VideoStopFailed;
//				} else if (item.mPackType == AqCommType.VERSION) {
//					msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.GetSoftVersionFailed;
//				} else if (item.mPackType == AqCommType.LINUX_REBOOT) {
//					msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.LinuxRebootFailed;
//				} else if (item.mPackType == AqCommType.MCU_COMM) {
//					switch (item.mMCUCommandType) {
//					case AqCommType.MCU_GET_STATE:
//						msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.GetStateFailed;
//						break;
//					case AqCommType.MCU_GET_SUBSCRIBE_TOKEN:
//						msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.GetSubscribeTokenFailed;
//						break;
//					case AqCommType.MCU_SET_DATETIME:
//						msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetDatetimeFailed;
//						break;
//					case AqCommType.MCU_SET_PERIOD:
//						msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetPeriodFailed;
//						break;
//					case AqCommType.MCU_SET_OUTPUT_EX:
//						msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetRelaysFailed;
//						break;
//					case AqCommType.MCU_SET_TEMP:
//						msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetMaxTempFailed;
//						break;
//					case AqCommType.MCU_SET_LOCK:
//						msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetLockFailed;
//						break;
//					case AqCommType.MCU_SET_FAULT_TEMP:
//						msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetFaultTempFailed;
//						break;
//					case AqCommType.MCU_GET_VERSION:
//						msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.GetVersionFailed;
//						break;
//					default:
//						break;
//					}
//				}
//				try {
//					out = new JSONObject();
//
//					JSONObject info = new JSONObject();
//					out.put("did", GetModelInfo.GetNetworkType());
//					out.put("type", "DEVICE_LINKING");
//					out.put("context", info);
//					out.put("log", "一键配置超时");
//					info.put("mobileNetworkCode",
//							GetModelInfo.GetNetworkType());
//					info.put("password", "");
//					info.put("did", "");
//					info.put("appVersion", GetModelInfo
//							.getAppVersionName(App.context));
//					info.put("wifiName", "wifiname");
//					info.put("version", GetModelInfo
//							.getAppVersionName(App.context));
//
//					info.put("type", "DEVICE_LINKING");
//					info.put("currentRadioAccessTechnology", "");
//					info.put("wifi", "");
//					out.put("context", info);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				MyJsonRequest<String> request = new MyJsonRequest.Builder<String>()
//						.apiVer("100")
//						.typeKey("BY_Log_deviceLog")//错误日志上传
//						.param("context", out.toString())//上传手机型号、手机ip、手机所在网络
//						.param("log", msgObj.mMessageType)
//						.param("type", "DEVICE_LINKING")
//						.requestListener(
//								new XRequestListener<String>() {
//
//									@Override
//									public void onResponse(String arg0) {
//										Log.v("aq", "AqDevice-------");
//									}
//								}).errorListener(new XErrorListener() {
//
//							@Override
//							public void onErrorResponse(Exception exception, int code,
//									String msg) {
//
//							}
//						}).build();
//				HttpRequest.getDefaultRequestQueue().add(request);
//
//				// 发送消息
//				sendMessage(msg);
//				return;
//			}
//			if (item.mPackType == AqCommType.MCU_COMM) {
//				switch (item.mMCUCommandType) {
//				case AqCommType.MCU_GET_STATE:
//					// 处理单片机全局状态
//					if (item.mReturnDataLength >= 4) {
//						byte[] b = item.mReturnData;
//						int l = item.mReturnDataLength;
//						mCurrentTemp = ((b[1 + 4] << 8) & 0xff00)
//								| (b[2 + 4] & 0xff);
//						mCurrentPh = ((b[3 + 4] << 8) & 0xff00)
//								| (b[4 + 4] & 0xff);
//						byte relays = b[5 + 4];
//						if ((relays & 0x01) != 0x00) {
//
//							mLampRelaysState = true;
//						} else {
//							mLampRelaysState = false;
//						}
//						if ((relays & 0x02) != 0x00) {
//
//							mPumpRelaysState = true;
//						} else {
//							mPumpRelaysState = false;
//						}
//						if ((relays & 0x10) != 0x00) {
//
//							// 01011100
//							// 00000001
//
//							// 00000100
//							// 00010000
//							// 01000000
//							mUvLampRelaysState = true;
//						} else {
//							mUvLampRelaysState = false;
//						}
//						if ((relays & 0x80) != 0x00) {
//							mAutoModeState = true;
//						} else {
//							mAutoModeState = false;
//						}
//						mDateTime.setBytes(b, 6 + 4);
//						mLampPeriod.setBytes(b, 13 + 4);
//						mUvLampPeriod.setBytes(b, 19 + 4);
//						mPumpPeriod.setBytes(b, 25 + 4);
//						mMaxTemp = ((b[32 + 4] << 8) & 0xff00)
//								| (b[33 + 4] & 0xff);
//						if (b[34 + 4] != 0x00) {
//							mLockState = true;
//						} else {
//							mLockState = false;
//						}
//
//						mFaultState.setBytes(b, 35 + 4);
//						mMinFaultTemp = ((b[37 + 4] << 8) & 0xff00)
//								| (b[38 + 4] & 0xff);
//						mMaxFaultTemp = ((b[39 + 4] << 8) & 0xff00)
//								| (b[40 + 4] & 0xff);
//						System.out.println("版本" + this.mDeviceInfo.mDeviceType
//								+ "字节" + b[41] + "");
//
//						// 增加4个功率
//						if (this.mDeviceInfo.mDeviceType == AqDeviceType.AQ211) {
//							XunhuanW = (b[41 + 4]);
//							ZiwaixianW = (b[42 + 4]);
//							shuiBengW = (b[43 + 4]);
//							lightW = (b[44 + 4]);
//							System.out.println("灯光照明：" + XunhuanW + "冲浪水泵："
//									+ shuiBengW + "紫外线：" + ZiwaixianW + "循环功率"
//									+ lightW);
//							if (XunhuanW <= 0 || XunhuanW >= 100) {
//								XunhuanW = 0;
//							}
//							if (shuiBengW <= 0 || shuiBengW >= 100) {
//								shuiBengW = 0;
//							}
//							if (ZiwaixianW <= 0 || ZiwaixianW >= 100) {
//								ZiwaixianW = 0;
//							}
//							if (lightW <= 0 || lightW >= 100) {
//								lightW = 0;
//							}
//						}
//
//						msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.GetStateOk;
//					}
//					break;
//				case AqCommType.MCU_GET_SUBSCRIBE_TOKEN:
//					// 处理订阅令牌
//					if (item.mReturnDataLength >= 4) {
//						// 暂时只处理返回3项
//						byte[] b = item.mReturnData;
//						int l = item.mReturnDataLength;
//						if (b[4] == AqCommType.MCU_GET_SUBSCRIBE_TOKEN) {
//							if (b[5] == 3) {
//								int[] s = new int[3];
//								int j = 0;
//								for (int i = 6; i < l; i++) {
//									if (b[i] == 0x00) {
//										s[j] = i;
//										j++;
//									}
//								}
//								if (j == 3) {
//									byte[] b1 = new byte[s[0] - 6];
//									byte[] b2 = new byte[s[1] - s[0] - 1];
//									byte[] b3 = new byte[s[2] - s[1] - 1];
//									System.arraycopy(b, 6, b1, 0, b1.length);
//									System.arraycopy(b, s[0] + 1, b2, 0,
//											b2.length);
//									System.arraycopy(b, s[1] + 1, b3, 0,
//											b3.length);
//									try {
//										mSubscribeTokenTemp = new String(b1,
//												"UTF-8");
//										mSubscribeTokenLevel = new String(b2,
//												"UTF-8");
//										mSubscribeTokenLoad = new String(b3,
//												"UTF-8");
//										msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.GetSubscribeTokenOk;
//										break;
//									} catch (UnsupportedEncodingException e) {
//									}
//								}
//							}
//						}
//					}
//					msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.GetSubscribeTokenFailed;
//					break;
//				case AqCommType.MCU_SET_DATETIME:
//					msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetDatetimeOk;
//					break;
//				case AqCommType.MCU_SET_PERIOD:
//					msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetPeriodOk;
//					break;
//				case AqCommType.MCU_SET_OUTPUT_EX:
//					msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetRelaysOk;
//					break;
//				case AqCommType.MCU_SET_TEMP:
//					msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetMaxTempOk;
//					break;
//				case AqCommType.MCU_SET_LOCK:
//					msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetLockOk;
//					break;
//				case AqCommType.MCU_SET_FAULT_TEMP:
//					msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetFaultTempOk;
//					break;
//				case AqCommType.MCU_GET_VERSION:
//					if (item.mReturnDataLength >= 4) {
//						byte[] b = item.mReturnData;
//						int l = item.mReturnDataLength;
//						if (b[4] == AqCommType.MCU_GET_VERSION) {
//							mDeviceInfo.mDeviceVersion = ((b[5] << 8) & 0xff00)
//									| (b[6] & 0xff);
//							switch (b[5]) {
//							case 0x17:
//								mDeviceInfo.mDeviceType = AqDeviceInfo.AqDeviceType.AQ107;
//								break;
//							case 0x29:
//								mDeviceInfo.mDeviceType = AqDeviceInfo.AqDeviceType.AQ209;
//								break;
//							case 0x28:
//								mDeviceInfo.mDeviceType = AqDeviceInfo.AqDeviceType.AQ208;
//								break;
//							case 0x21:
//								mDeviceInfo.mDeviceType = AqDeviceInfo.AqDeviceType.AQ211;
//								break;
//							case (byte) 0x85:
//								mDeviceInfo.mDeviceType = AqDeviceInfo.AqDeviceType.AQ805;
//								break;
//							default:
//								mDeviceInfo.mDeviceType = AqDeviceInfo.AqDeviceType.NULL;
//								break;
//							}
//							msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.GetVersionOk;
//						}
//					}
//					break;
//				default:
//					break;
//				}
//			} else if (item.mPackType == AqCommType.MJPEG_FRAME) {
//				// 视频帧数据
//				try {
//					if (item.mReturnDataLength <= (AppConfig.Video_Frame_Size + 4)) {
//						Bitmap frame = BitmapFactory
//								.decodeByteArray(item.mReturnData, 4,
//										item.mReturnDataLength - 4);
//						if (frame != null) {
//							msgObj.mFrame = frame;
//							msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.NewVideoFrame;
//						}
//					}
//				} catch (Exception e) {
//				}
//			} else if (item.mPackType == AqCommType.MJPEG_START) {
//				msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.VideoStartOk;
//			} else if (item.mPackType == AqCommType.MJPEG_STOP) {
//				msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.VideoStopOk;
//			} else if (item.mPackType == AqCommType.VERSION) {
//				// 处理软件版本获取，预留
//				msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.GetSoftVersionOk;
//			} else if (item.mPackType == AqCommType.LINUX_REBOOT) {
//				msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.LinuxRebootOk;
//			}
//			// 发送消息
//			sendMessage(msg);
//		}
//	}

//	public void asyncConnect() {
//		// 命令发送线程
//		mCommandTransmitThread = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				while (true) {
//					CommandItem item = null;
//					synchronized (mCommandQueue) {
//						item = mCommandQueue.poll();
//					}
//					if (item == null) {
//						try {
//							Thread.sleep(10);
//						} catch (InterruptedException e) {
//							return;
//						}
//						continue;
//					}
//					synchronized (mCommandReceiveThread) {
//						mNewReceiveData = null;
//						mNewReceiveDataLength = 0;
//					}
//					int ret = PPPP_APIs.PPPP_Write(mDeviceHandle, (byte) 1,
//							item.mData, item.mDataLength);
//					if (ret < PPPP_APIs.ERROR_PPPP_SUCCESSFUL) {
//						// 发送失败
//						asyncReturn(item);
//						continue;
//					}
//					// 发送成功，等待返回
//					if (!item.mReturnMode) {
//						synchronized (item) {
//							item.mReturnData = null;
//							item.mReturnDataLength = 0;
//							item.mCommandIsOk = true;
//						}
//						asyncReturn(item);
//						continue;
//					}
//					boolean returnOk = false;
//					for (int i = 0; i < (item.mTimeoutMs / 5); i++) {
//						// 延迟5ms
//						try {
//							Thread.sleep(5);
//						} catch (InterruptedException e) {
//							return;
//						}
//						// 判断是否有最新命令
//						synchronized (mCommandReceiveThread) {
//							if (mNewReceiveData != null) {
//								synchronized (item) {
//									item.mReturnData = mNewReceiveData;
//									item.mReturnDataLength = mNewReceiveDataLength;
//									item.mCommandIsOk = true;
//								}
//								asyncReturn(item);
//								mNewReceiveData = null;
//								mNewReceiveDataLength = 0;
//								// 数据返回成功
//								returnOk = true;
//								break;
//							}
//						}
//					}
//					if (!returnOk) {
//						// 数据返回失败
//						asyncReturn(item);
//					}
//				}
//			}
//		});
//
//		// 命令接收线程
//		mCommandReceiveThread = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				while (true) {
//					byte[] b = new byte[512];
//					int[] l = new int[1];
//					if (syncReadPack(b, l, 1, -1) == ReadState.Ok) {
//						synchronized (mCommandReceiveThread) {
//							mNewReceiveData = b;
//							mNewReceiveDataLength = l[0];
//						}
//					}
//					synchronized (mConnectThread) {
//						if (!mConnectState) {
//							return;
//						}
//					}
//				}
//			}
//		});
//
//		// 视频接收线程
//		mVideoReceiveThread = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				while (true) {
//					byte[] b = new byte[AppConfig.Video_Frame_Size];
//					int[] l = new int[1];
//					if (syncReadPack(b, l, 0, -1) == ReadState.Ok) {
//						CommandItem item = new CommandItem(null, 0, false,
//								AqCommType.MJPEG_FRAME, 0, 0);
//						item.mReturnData = b;
//						item.mReturnDataLength = l[0];
//						item.mCommandIsOk = true;
//						asyncReturn(item);
//					}
//					synchronized (mConnectThread) {
//						if (!mConnectState) {
//							return;
//						}
//					}
//				}
//			}
//		});
//
//		// 连接线程
//		mConnectThread = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					// 连接设备
//					AqDeviceMessage msgObj = new AqDeviceMessage();
//					msgObj.mDevice = mThis;
//					Message msg = new Message();
//					msg.what = MessageWhat.DeviceMessage;
//					msg.obj = msgObj;
//					mDeviceHandle = PPPP_APIs.PPPP_Connect(mDeviceInfo.mDid,
//							(byte) 1, 0);
//					if (mDeviceHandle < 0) {
//						synchronized (mConnectThread) {
//							mConnectState = false;
//						}
//						msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.ConnectFailed;
//						sendMessage(msg);
//						return;
//					}
//					synchronized (mConnectThread) {
//						mConnectState = true;
//					}
//					msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.ConnectOk;
//					sendMessage(msg);
//					// 收发数据
//					mCommandReceiveThread.start();
//					mVideoReceiveThread.start();
//					mCommandTransmitThread.start();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
//		mConnectThread.start();
//	}

	private void sendMessage(Message msg) {
		if (mHandler != null) {
			mHandler.sendMessage(msg);
		}
	}

//	private ReadState syncReadPack(byte[] buffer, int[] length, int channel,
//			int timeoutMs) {
//		try {
//			byte[] buf1 = new byte[4];
//			int[] len1 = new int[1];
//			len1[0] = 4;
//			int ret = PPPP_APIs.PPPP_Read(mDeviceHandle, (byte) channel, buf1,
//					len1, timeoutMs);
//			if (ret != PPPP_APIs.ERROR_PPPP_SUCCESSFUL) {
//				return ReadState.TimeOut;
//			}
//			if (buf1[0] != (byte) 0xaa) {
//				return ReadState.Error;
//			}
//			int contentLen1 = ((buf1[3] << 8) & 0xff00) | (buf1[2] & 0xff);
//			if (contentLen1 > 0) {
//				byte[] buf2 = new byte[contentLen1];
//				int[] len2 = new int[1];
//				len2[0] = contentLen1;
//				ret = PPPP_APIs.PPPP_Read(mDeviceHandle, (byte) channel, buf2,
//						len2, timeoutMs);
//				if (ret != PPPP_APIs.ERROR_PPPP_SUCCESSFUL) {
//					return ReadState.TimeOut;
//				}
//				try {
//					System.arraycopy(buf1, 0, buffer, 0, 4);
//					System.arraycopy(buf2, 0, buffer, 4, contentLen1);
//					length[0] = contentLen1 + 4;
//				} catch (Exception e) {
//					return ReadState.Error;
//				}
//				return ReadState.Ok;
//			}
//			return ReadState.Error;
//		} catch (Exception e) {
//			return ReadState.Error;
//		}
//	}

//	private byte[] buildMcuData(byte[] data, byte mode) {
//		byte[] b = new byte[data.length + 4 + 18];
//		b[0] = (byte) 0xaa;
//		b[1] = (byte) AqCommType.MCU_COMM;
//		ByteUtil.putShort(b, (short) (data.length + 18), 2);
//		try {
//			byte[] p = mDeviceInfo.mPassword.getBytes("utf-8");
//			for (int i = 0; i < 16; i++) {
//				if (i < p.length) {
//					b[i + 4] = p[i];
//				}
//			}
//			b[19] = 0;
//		} catch (UnsupportedEncodingException e) {
//		}
//		b[20] = mode;
//		b[21] = (byte) data.length;
//		for (int i = 0; i < data.length; i++) {
//			b[i + 22] = data[i];
//		}
//		return b;
//	}

//	public CommandItem setFaultTemp(int maxValue, int minValue) {
//		synchronized (mMutexMcuState) {
//			mMaxFaultTemp = maxValue;
//			mMinFaultTemp = minValue;
//			byte[] buffer = new byte[6];
//			buffer[0] = AqCommType.MCU_SET_FAULT_TEMP;
//			buffer[1] = 0x00;
//			buffer[2] = (byte) (minValue / 256 % 256);
//			buffer[3] = (byte) (minValue % 256);
//			buffer[4] = (byte) (maxValue / 256 % 256);
//			buffer[5] = (byte) (maxValue % 256);
//			byte[] b = buildMcuData(buffer, (byte) 0);
//			CommandItem item = new CommandItem(b, b.length, false,
//					AqCommType.MCU_COMM, AqCommType.MCU_SET_FAULT_TEMP,
//					AppConfig.Command_Timeout);
//			synchronized (mCommandQueue) {
//				mCommandQueue.offer(item);
//				return item;
//			}
//		}
//	}

//	public CommandItem getState() {
//		byte[] buffer = new byte[2];
//		buffer[0] = AqCommType.MCU_GET_STATE;
//		buffer[1] = AqCommType.MCU_GET_STATE;
//		byte[] b = buildMcuData(buffer, (byte) 1);
//		CommandItem item = new CommandItem(b, b.length, true,
//				AqCommType.MCU_COMM, AqCommType.MCU_GET_STATE,
//				AppConfig.Command_Timeout);
//		synchronized (mCommandQueue) {
//			mCommandQueue.offer(item);
//			return item;
//		}
//	}

//	public CommandItem setTemp(int value) {
//		synchronized (mMutexMcuState) {
//			mMaxTemp = value;
//			byte[] buffer = new byte[3];
//			buffer[0] = AqCommType.MCU_SET_TEMP;
//			buffer[1] = (byte) (value / 256 % 256);
//			buffer[2] = (byte) (value % 256);
//			byte[] b = buildMcuData(buffer, (byte) 0);
//			CommandItem item = new CommandItem(b, b.length, false,
//					AqCommType.MCU_COMM, AqCommType.MCU_SET_TEMP,
//					AppConfig.Command_Timeout);
//			synchronized (mCommandQueue) {
//				mCommandQueue.offer(item);
//				return item;
//			}
//		}
//	}

//	public CommandItem setLock(boolean value) {
//		synchronized (mMutexMcuState) {
//			mLockState = value;
//			byte[] buffer = new byte[2];
//			buffer[0] = AqCommType.MCU_SET_LOCK;
//			if (value) {
//				buffer[1] = 0x01;
//			} else {
//				buffer[1] = 0x00;
//			}
//			byte[] b = buildMcuData(buffer, (byte) 0);
//			CommandItem item = new CommandItem(b, b.length, false,
//					AqCommType.MCU_COMM, AqCommType.MCU_SET_LOCK,
//					AppConfig.Command_Timeout);
//			synchronized (mCommandQueue) {
//				mCommandQueue.offer(item);
//				return item;
//			}
//		}
//	}

	private class SubscribeRunnable implements Runnable {

		private AqDevice mDevice;
		private boolean mSubscribeValue;
		private SubscribeType mType;

		public SubscribeRunnable(AqDevice device, boolean subscribeValue,
				SubscribeType type) {
			mDevice = device;
			mSubscribeValue = subscribeValue;
			mType = type;
		}

		private void sendMessage(Message msg) {
			if (mDevice.mHandler != null) {
				mDevice.mHandler.sendMessage(msg);
			}
		}

		@Override
		public void run() {
//			synchronized (mDevice.mMutexSubscribeThread) {
//				AqDeviceMessage msgObj = new AqDeviceMessage();
//				msgObj.mDevice = mDevice;
//				msgObj.mCommandItem = null;
//				Message msg = new Message();
//				msg.obj = msgObj;
//				msg.what = MessageWhat.DeviceMessage;
//				String subscribeToken = "";
//				int subId = 0;
//				int tempSubId = mDevice.mDatabase
//						.getTempFaultConfigSubscribeID(mDevice.mDeviceInfo.mDid);
//				int levelSubId = mDevice.mDatabase
//						.getLevelFaultConfigSubscribeID(mDevice.mDeviceInfo.mDid);
//				int loadSubId = mDevice.mDatabase
//						.getLoadFaultConfigSubscribeID(mDevice.mDeviceInfo.mDid);
//				synchronized (mDevice.mMutexSubscribe) {
//					switch (mType) {
//					case Temp:
//						subscribeToken = mDevice.mSubscribeTokenTemp;
//						subId = tempSubId;
//						break;
//					case Level:
//						subscribeToken = mDevice.mSubscribeTokenLevel;
//						subId = levelSubId;
//						break;
//					case Load:
//						subscribeToken = mDevice.mSubscribeTokenLoad;
//						subId = loadSubId;
//						break;
//					default:
//						break;
//					}
//				}
//				int[] id = new int[1];
//				id[0] = 0;
//				int ret = 0;
//				if (mSubscribeValue) {
//					ret = iPN_API.iPN_Subscribe_XinGe(subscribeToken,
//							AppConfig.iPN_AESKey, mDevice.mClientID, id);
//				} else {
//					ret = iPN_API.iPN_UnSubscribe_XinGe(subscribeToken,
//							AppConfig.iPN_AESKey, subId);
//				}
//				synchronized (mDevice.mMutexSubscribe) {
//					if (ret != iPN_API.iPN_Error_Success) {
//						System.out.println("kaiqishibai----1");
//						// 失败
//						switch (mType) {
//						case Temp:
//							mDevice.mSubscribeTempState = mDevice.mDatabase
//									.getTempFaultConfig(mDevice.mDeviceInfo.mDid);
//							msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetSubscribeTempFailed;
//							break;
//						case Level:
//							mDevice.mSubscribeLevelState = mDevice.mDatabase
//									.getLevelFaultConfig(mDevice.mDeviceInfo.mDid);
//							msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetSubscribeLevelFailed;
//							break;
//						case Load:
//							mDevice.mSubscribeLoadState = mDevice.mDatabase
//									.getLoadFaultConfig(mDevice.mDeviceInfo.mDid);
//							msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetSubscribeLoadFailed;
//							break;
//						default:
//							break;
//						}
//
//					} else {
//
//						System.out.println("kaiqishibai----2");
//						// 成功
//						switch (mType) {
//						case Temp:
//							mDevice.mSubscribeTempState = mSubscribeValue;
//							tempSubId = id[0];
//							msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetSubscribeTempOk;
//							break;
//						case Level:
//							mDevice.mSubscribeLevelState = mSubscribeValue;
//							levelSubId = id[0];
//							msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetSubscribeLevelOk;
//							break;
//						case Load:
//							mDevice.mSubscribeLoadState = mSubscribeValue;
//							loadSubId = id[0];
//							msgObj.mMessageType = AqDeviceMessage.AqDeviceMessageType.SetSubscribeLoadOk;
//							break;
//						default:
//							break;
//						}
//					}
//					mDevice.mDatabase.saveFaultConfig(mDevice.mDeviceInfo.mDid,
//							mDevice.mSubscribeTempState,
//							mDevice.mSubscribeLevelState,
//							mDevice.mSubscribeLoadState, tempSubId, levelSubId,
//							loadSubId);
//					sendMessage(msg);
//				}
//			}
		}
	}

	private void setSubscribe(SubscribeType type, boolean value) {
		Thread subscribeThread = new Thread(new SubscribeRunnable(this, value,
				type));
		subscribeThread.start();
	}

	public void setSubscribeTemp(boolean value) {
		synchronized (mMutexSubscribe) {
			mSubscribeTempState = value;
			setSubscribe(SubscribeType.Temp, value);
		}
	}

	public void setSubscribeLevel(boolean value) {
		synchronized (mMutexSubscribe) {
			mSubscribeLevelState = value;
			setSubscribe(SubscribeType.Level, value);
		}
	}

	public void setSubscribeLoad(boolean value) {
		synchronized (mMutexSubscribe) {
			mSubscribeLoadState = value;
			setSubscribe(SubscribeType.Load, value);
		}
	}

//	public CommandItem getDeviceVersion() {
//		byte[] buffer = new byte[2];
//		buffer[0] = AqCommType.MCU_GET_VERSION;
//		buffer[1] = AqCommType.MCU_GET_VERSION;
//		byte[] b = buildMcuData(buffer, (byte) 1);
//		CommandItem item = new CommandItem(b, b.length, true,
//				AqCommType.MCU_COMM, AqCommType.MCU_GET_VERSION,
//				AppConfig.Command_Timeout);
//		synchronized (mCommandQueue) {
//			mCommandQueue.offer(item);
//			return item;
//		}
//	}

//	public CommandItem getSubscribeToken(boolean temp, boolean level,
//			boolean load) {
//		byte[] buffer = new byte[3];
//		buffer[0] = AqCommType.MCU_GET_SUBSCRIBE_TOKEN;
//		buffer[1] = 0x00;
//		buffer[2] = 0x00;
//		if (temp) {
//			buffer[2] |= 0x01;
//		}
//		if (level) {
//			buffer[2] |= 0x02;
//		}
//		if (load) {
//			buffer[2] |= 0x04;
//		}
//		byte[] b = buildMcuData(buffer, (byte) 1);
//		CommandItem item = new CommandItem(b, b.length, true,
//				AqCommType.MCU_COMM, AqCommType.MCU_GET_SUBSCRIBE_TOKEN,
//				AppConfig.Command_Timeout);
//		synchronized (mCommandQueue) {
//			mCommandQueue.offer(item);
//			return item;
//		}
//	}

//	public CommandItem setDateTime(AqDateTime dateTime) {
//		synchronized (mMutexMcuState) {
//			mDateTime = dateTime;
//			byte[] buffer = new byte[8];
//			buffer[0] = AqCommType.MCU_SET_DATETIME;
//			dateTime.getBytes(buffer, 1);
//			byte[] b = buildMcuData(buffer, (byte) 0);
//			CommandItem item = new CommandItem(b, b.length, false,
//					AqCommType.MCU_COMM, AqCommType.MCU_SET_DATETIME,
//					AppConfig.Command_Timeout);
//			synchronized (mCommandQueue) {
//				mCommandQueue.offer(item);
//				return item;
//			}
//		}
//	}

//	public CommandItem setRelays(Relays relays, boolean value) {
//		synchronized (mMutexMcuState) {
//			byte[] buffer = new byte[2];
//			buffer[0] = AqCommType.MCU_SET_OUTPUT_EX;
//			switch (relays) {
//			case Lamp:
//				buffer[1] = 0x06;
//				mLampRelaysState = value;
//				break;
//			case Pump:
//				buffer[1] = 0x04;
//				mPumpRelaysState = value;
//				break;
//			case UvLamp:
//				buffer[1] = 0x02;
//				mUvLampRelaysState = value;
//				break;
//			case Mode:
//				buffer[1] = 0x00;
//				mAutoModeState = value;
//				break;
//			default:
//				break;
//			}
//			if (value) {
//				buffer[1] |= 0x01;
//			}
//			byte[] b = buildMcuData(buffer, (byte) 0);
//			CommandItem item = new CommandItem(b, b.length, false,
//					AqCommType.MCU_COMM, AqCommType.MCU_SET_OUTPUT_EX,
//					AppConfig.Command_Timeout);
//			synchronized (mCommandQueue) {
//				mCommandQueue.offer(item);
//				return item;
//			}
//		}
//	}

//	public CommandItem setPeriod(AqPeriod lamp, AqPeriod pump, AqPeriod uvLamp) {
//		byte[] buffer = new byte[19];
//		buffer[0] = AqCommType.MCU_SET_PERIOD;
//		AqPeriod.copy(lamp, mLampPeriod);
//		AqPeriod.copy(pump, mPumpPeriod);
//		AqPeriod.copy(uvLamp, mUvLampPeriod);
//		mLampPeriod.getBytes(buffer, 1);
//		mUvLampPeriod.getBytes(buffer, 7);
//		mPumpPeriod.getBytes(buffer, 13);
//		byte[] b = buildMcuData(buffer, (byte) 0);
//		CommandItem item = new CommandItem(b, b.length, false,
//				AqCommType.MCU_COMM, AqCommType.MCU_SET_PERIOD,
//				AppConfig.Command_Timeout);
//		synchronized (mCommandQueue) {
//			mCommandQueue.offer(item);
//			return item;
//		}
//	}

//	public CommandItem setPeriodLamp(AqPeriod lamp) {
//		synchronized (mMutexMcuState) {
//			return setPeriod(lamp, mPumpPeriod, mUvLampPeriod);
//		}
//	}

//	public CommandItem setPeriodPump(AqPeriod pump) {
//		synchronized (mMutexMcuState) {
//			return setPeriod(mLampPeriod, pump, mUvLampPeriod);
//		}
//	}

//	public CommandItem setPeriodUvLamp(AqPeriod uvLamp) {
//		synchronized (mMutexMcuState) {
//			return setPeriod(mLampPeriod, mPumpPeriod, uvLamp);
//		}
//	}

//	public CommandItem setPeriodUvLampPump(AqPeriod period) {
//		synchronized (mMutexMcuState) {
//			return setPeriod(mLampPeriod, period, period);
//		}
//	}

//	public CommandItem setVideoStart(short count, short cycle, short width,
//			short height) {
//		mVideoState = true;
//		byte[] b = new byte[16 + 4 + 8];
//		b[0] = (byte) 0xaa;
//		b[1] = (byte) AqCommType.MJPEG_START;
//		ByteUtil.putShort(b, (short) (16 + 8), 2);
//		try {
//			byte[] p = mDeviceInfo.mPassword.getBytes("utf-8");
//			for (int i = 0; i < 16; i++) {
//				if (i < p.length) {
//					b[i + 4] = p[i];
//				}
//			}
//			b[19] = 0;
//		} catch (UnsupportedEncodingException e) {
//		}
//		ByteUtil.putShort(b, count, 20);
//		ByteUtil.putShort(b, cycle, 22);
//		ByteUtil.putShort(b, height, 24);
//		ByteUtil.putShort(b, width, 26);
//		CommandItem item = new CommandItem(b, b.length, false,
//				AqCommType.MJPEG_START, 0, AppConfig.Command_Timeout);
//		synchronized (mCommandQueue) {
//			mCommandQueue.offer(item);
//			return item;
//		}
//	}

//	public CommandItem setVideoStop() {
//		mVideoState = false;
//		byte[] b = new byte[16 + 4];
//		b[0] = (byte) 0xaa;
//		b[1] = (byte) AqCommType.MJPEG_STOP;
//		ByteUtil.putShort(b, (short) 16, 2);
//		try {
//			byte[] p = mDeviceInfo.mPassword.getBytes("utf-8");
//			for (int i = 0; i < 16; i++) {
//				if (i < p.length) {
//					b[i + 4] = p[i];
//				}
//			}
//			b[19] = 0;
//		} catch (UnsupportedEncodingException e) {
//		}
//		CommandItem item = new CommandItem(b, b.length, false,
//				AqCommType.MJPEG_STOP, 0, AppConfig.Command_Timeout);
//		synchronized (mCommandQueue) {
//			mCommandQueue.offer(item);
//			return item;
//		}
//	}

//	public CommandItem getSoftVersion() {
//		mVideoState = false;
//		byte[] b = new byte[16 + 4];
//		b[0] = (byte) 0xaa;
//		b[1] = (byte) AqCommType.VERSION;
//		ByteUtil.putShort(b, (short) 16, 2);
//		try {
//			byte[] p = mDeviceInfo.mPassword.getBytes("utf-8");
//			for (int i = 0; i < 16; i++) {
//				if (i < p.length) {
//					b[i + 4] = p[i];
//				}
//			}
//			b[19] = 0;
//		} catch (UnsupportedEncodingException e) {
//		}
//		CommandItem item = new CommandItem(b, b.length, true,
//				AqCommType.VERSION, 0, AppConfig.Command_Timeout);
//		synchronized (mCommandQueue) {
//			mCommandQueue.offer(item);
//			return item;
//		}
//	}

//	public CommandItem linuxReboot() { // 有点偷懒了，重复...
//		mVideoState = false;
//		byte[] b = new byte[16 + 4];
//		b[0] = (byte) 0xaa;
//		b[1] = (byte) AqCommType.LINUX_REBOOT;
//		ByteUtil.putShort(b, (short) 16, 2);
//		try {
//			byte[] p = mDeviceInfo.mPassword.getBytes("utf-8");
//			for (int i = 0; i < 16; i++) {
//				if (i < p.length) {
//					b[i + 4] = p[i];
//				}
//			}
//			b[19] = 0;
//		} catch (UnsupportedEncodingException e) {
//		}
//		CommandItem item = new CommandItem(b, b.length, false,
//				AqCommType.LINUX_REBOOT, 0, AppConfig.Command_Timeout);
//		synchronized (mCommandQueue) {
//			mCommandQueue.offer(item);
//			return item;
//		}
//	}
}
