package ChirdSdk.Apis;

public class st_AudioFrame {
	public static final int CHD_AUDIO_ADBITS_8 = 8;
	public static final int CHD_AUDIO_ADBITS_16 = 16;

	public static final int CHD_AUDIO_CHN_SIGNAL = 1;
	public static final int CHD_AUDIO_CHN_STEREO = 2;

	public static final int CHD_AUDIO_SAMPLE_8000 = 8000;
	public static final int CHD_AUDIO_SAMPLE_44100 = 44100;
	public static final int CHD_AUDIO_SAMPLE_48000 = 48000;

	public static final int G711U = 1;
	public static final int G711A = 2;
	public static final int G726 = 3;
	public static final int G729 = 4;
	public static final int G729A = 5;
	public static final int G729B = 6;
	public static final int AAC = 7;
	public static final int AMR = 8;
	public static final int MP3 = 9;

	public int rate = 0;
	public int channels = 0;
	public int bits = 0;
	public int EncodeType = 0;
	public int data0 = 0;
	public int data1 = 0;
	public int data2 = 0;
	public int data3 = 0;
	public int data4 = 0;

	public long timestamp = 0;
	public int datalen = 0;
	
	public long Address = 0;
	public long pDataAddress = 0;

}