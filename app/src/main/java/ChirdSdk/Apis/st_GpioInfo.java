package ChirdSdk.Apis;

public class st_GpioInfo {
	

	public static final int  CHD_GPIO_DIR_INPUT	 = 0x01;
	public static final int  CHD_GPIO_DIR_OUTPUT = 0x00;
	
	public static final int  CHD_GPIO_STATE_LOW	 = 0x00;
	public static final int  CHD_GPIO_STATE_HIGH = 0x01;

	public int 	number; 				

	public int 		[]	dir			= new int[32];
	public int 		[]	state		= new int[32];		
	
}
