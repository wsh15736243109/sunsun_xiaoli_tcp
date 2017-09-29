package ChirdSdk;

import android.util.Log;

import ChirdSdk.Apis.chd_wmp_apis;
import ChirdSdk.Apis.st_SearchInfo;

public class CHD_LocalScan {
	
	 private chd_wmp_apis  WMP	   = new chd_wmp_apis(); 
	 private st_SearchInfo DevInfo = new st_SearchInfo();
	 
	public CHD_LocalScan(){ 
		WMP.CHD_WMP_ScanDevice_InitIndex(1000, 1); 
	}
	
	public int  getLocalScanNum(){   
		 return WMP.CHD_WMP_Scan_GetDeviceInformation(DevInfo); 	
	}
	 
	public int getLocalDevId(int Num){
		int Cnt = WMP.CHD_WMP_Scan_GetDeviceInformation(DevInfo); 
		if (Num >= Cnt)	return -1;
		
		Log.v("test", "did:"+DevInfo.did+" passwd:"+DevInfo.passwd);
		 
		return DevInfo.id[Num];
	}
	 
	public String getLocalDevAlias(int Num){
		if (Num >= WMP.CHD_WMP_Scan_GetDeviceInformation(DevInfo)){
			return null;
		}

		return DevInfo.alias[Num];
	 }
	 
	public String getLocalDevIpAddress(int Num){
		if (Num >= WMP.CHD_WMP_Scan_GetDeviceInformation(DevInfo)){
				return null;
		}
				
		return DevInfo.address[Num];
	 }
	
	public String getLocalDevDid(int Num){
		if (Num >= WMP.CHD_WMP_Scan_GetDeviceInformation(DevInfo)){
				return null;
		}
				
		return DevInfo.did[Num];
	 }
	
	public String getLocalDevPasswd(int Num){
		if (Num >= WMP.CHD_WMP_Scan_GetDeviceInformation(DevInfo)){
				return null;
		}
				
		return DevInfo.passwd[Num];
	 }
	
	 public String getLocalDevVersion(int Num){
		if (Num >= WMP.CHD_WMP_Scan_GetDeviceInformation(DevInfo)){
				return null;
		}
				
		return Long.toString(DevInfo.version[Num] >> 16) + "."
		+ Long.toString((DevInfo.version[Num] & 0x0000ff00) >> 8)
		+ Long.toString(DevInfo.version[Num] & 0x000000ff);
	 }
		
	protected void finalize(){
		WMP.CHD_WMP_ScanDevice_UnInit();
    }
}
