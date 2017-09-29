package ChirdSdk;

import ChirdSdk.Apis.chd_wmp_apis;

public class CHD_SmartConf {
	private chd_wmp_apis  WMP	   = new chd_wmp_apis(); 
	
	boolean	isOpen;
	
	public CHD_SmartConf(){
		isOpen = false;
	}
	
	public boolean isOpen(){
		return isOpen;
	}
	
	public int openSmartConfig(String RouterName, String RouterPasswd, String DevId){
		
		int ret = WMP.CHD_WMP_SmartConfig_Begin(RouterName, RouterPasswd, DevId);
		if (ret < 0)	return ret;
		
		isOpen = true;
		
		return 0;
	}
	
	public int closeSmartConfig(){
		
		int ret =  WMP.CHD_WMP_SmartConfig_End();
		if (ret < 0)	return ret;
		isOpen = false;
		
		return 0;
	}
	
	protected void finalize(){
		
		if (isOpen){
			WMP.CHD_WMP_SmartConfig_End();
		}
    }
}
