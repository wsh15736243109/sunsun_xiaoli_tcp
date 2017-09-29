package ChirdSdk.Apis;

public class st_VideoCtrlInfo {
	
	public static final int CHD_VCTRL_BRIGHTNESS	=	0;	
	public static final int CHD_VCTRL_CONTRAST		=	1;	
	public static final int CHD_VCTRL_SATURATION	=	2; 	
	public static final int CHD_VCTRL_HUE			=	3; 	
	public static final int CHD_VCTRL_WHITE_BALANCE	=	4;	
	public static final int CHD_VCTRL_GAMMA			=	5;	
	public static final int CHD_VCTRL_GAIN			=	6;	
	public static final int CHD_VCTRL_SHARPNESS		=	7;	
	public static final int CHD_VCTRL_BACKLIGHT 	=	8;	
	public static final int CHD_VCTRL_EXPOSURE 		=	9;	

	
	public int auto_valid;			
	public int autoval; 				
	
	public int val_valid; 			

	public int minval; 				
	public int curval; 				
	public int maxval; 				
	public int stepval;			
	public int defval; 				

	public int vctrl_auto_valid;

}
