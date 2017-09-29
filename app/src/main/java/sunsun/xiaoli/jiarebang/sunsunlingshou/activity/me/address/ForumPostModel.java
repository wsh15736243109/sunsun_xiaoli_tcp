package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.address;

import android.graphics.Bitmap;

public class ForumPostModel {
	
	private String name;
	private Bitmap landlord;
	private String post_time;
	private String contenxt; 
	
	private String img_reply;
	private String img_zan;
	private String lei;
	private String centent;
	private String ght;
	/*private int content;//第几楼
	private String post_leift;//话题评论
	private String post_centent;//话题点赞
	private String post_ghtei;//右边图片
*/	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Bitmap getLandlord() {
		return landlord;
	}
	public void setLandlord(Bitmap landlord) {
		this.landlord = landlord;
	}
	
	public String getLei() {
		return lei;
	}
	public void setLei(String lei) {
		this.lei = lei;
	}
	public String getCentent() {
		return centent;
	}
	public void setCentent(String centent) {
		this.centent = centent;
	}
	public String getGht() {
		return ght;
	}
	public void setGht(String ght) {
		this.ght = ght;
	}
	public String getImg_reply() {
		return img_reply;
	}
	public void setImg_reply(String img_reply) {
		this.img_reply = img_reply;
	}
	public String getImg_zan() {
		return img_zan;
	}
	public void setImg_zan(String img_zan) {
		this.img_zan = img_zan;
	}
	public String getContenxt() {
		return contenxt;
	}
	public void setContenxt(String contenxt) {
		this.contenxt = contenxt;
	}
	public String getPost_time() {
		return post_time;
	}
	public void setPost_time(String post_time) {
		this.post_time = post_time;
	}
	

}
