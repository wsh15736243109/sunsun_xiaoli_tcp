package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.address;

import android.graphics.Bitmap;

/***
 * 水族馆
 * @author Gooraye
 *
 */
public class PetModel {
	private Bitmap imghed;//touxiang
	private Bitmap leit;//做图片
	private Bitmap centet;//中间
	private Bitmap right;//右边
	private Bitmap timeaq;//时间
	private  Bitmap item_aqtime;
	private Bitmap ping;//评论
	private Bitmap zan;//赞
	private String pinaq;
	private String zanaq;
	private String time;//时间
	private String content;//内容
	public Bitmap getImghed() {
		return imghed;
	}
	public void setImghed(Bitmap imghed) {
		this.imghed = imghed;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Bitmap getLeit() {
		return leit;
	}
	public void setLeit(Bitmap leit) {
		this.leit = leit;
	}
	public Bitmap getCentet() {
		return centet;
	}
	public void setCentet(Bitmap centet) {
		this.centet = centet;
	}
	public Bitmap getRight() {
		return right;
	}
	public void setRight(Bitmap right) {
		this.right = right;
	}
	public Bitmap getTimeaq() {
		return timeaq;
	}
	public void setTimeaq(Bitmap timeaq) {
		this.timeaq = timeaq;
	}
	public Bitmap getItem_aqtime() {
		return item_aqtime;
	}
	public void setItem_aqtime(Bitmap item_aqtime) {
		this.item_aqtime = item_aqtime;
	}
	public Bitmap getPing() {
		return ping;
	}
	public void setPing(Bitmap ping) {
		this.ping = ping;
	}
	public Bitmap getZan() {
		return zan;
	}
	public void setZan(Bitmap zan) {
		this.zan = zan;
	}
	public String getPinaq() {
		return pinaq;
	}
	public void setPinaq(String pinaq) {
		this.pinaq = pinaq;
	}
	public String getZanaq() {
		return zanaq;
	}
	public void setZanaq(String zanaq) {
		this.zanaq = zanaq;
	}
	private String name;
	private String unsername;
	private Bitmap donw;
	public Bitmap getDonw() {
		return donw;
	}
	public void setDonw(Bitmap donw) {
		this.donw = donw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnsername() {
		return unsername;
	}
	public void setUnsername(String unsername) {
		this.unsername = unsername;
	}

}
