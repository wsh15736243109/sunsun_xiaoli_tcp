package ChirdSdk.Apis;

public class st_VideoAbilityInfo {
	

	public static final int CHD_FMT_YUYV 	= 0X01;
	public static final int CHD_FMT_MJPEG 	= 0x02;
	public static final int CHD_FMT_H264	= 0x03;
	
	public int FormatNum = 0;
	public  	  int [] format = new int[5];
	public int ResoluNum = 0;
	public        int [] width 	= new int[12];
	public        int [] height = new int[12];
	public        int [] maxfps = new int[12];	
	

	public String GetFormatString(int format){	
		switch (format){
		case CHD_FMT_YUYV	: return "YUYV";
		case CHD_FMT_MJPEG	: return "JPEG";
		case CHD_FMT_H264	: return "H264";
		default				: return "OTHER";
		}
	}
	

	public String GetResoluString(int w, int h){
		return String.valueOf(w) + "x" + String.valueOf(h);
	}

}
