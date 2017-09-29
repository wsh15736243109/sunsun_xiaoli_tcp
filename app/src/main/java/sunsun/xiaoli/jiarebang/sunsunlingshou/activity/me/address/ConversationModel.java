package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.address;

import android.graphics.Bitmap;

public class ConversationModel {
	/****
	 * 论坛话题实体类
	 * 
	 * @author2016-03-3
	 */
	
	private String name;// 名字
	private String lou;// 是否是楼主
	private String time;// 评论时间
	private String content;// 评论内容
	private String head;// 头像
	private Bitmap ping;// 评论
	private Bitmap zan;// 赞
	
//关于楼主的字段
	private String lou_hed;
	private String lou_name;
	private String title;
	private String lou_content;
	private String  img_top;
	private String img_button;
	private Bitmap lou_zan;
	private Bitmap img_time;
	
public Bitmap getImg_time() {
		return img_time;
	}
	public void setImg_time(Bitmap img_time) {
		this.img_time = img_time;
	}
public String getLou_hed() {
		return lou_hed;
	}
	public void setLou_hed(String lou_hed) {
		this.lou_hed = lou_hed;
	}
	public String getLou_name() {
		return lou_name;
	}
	public void setLou_name(String lou_name) {
		this.lou_name = lou_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLou_content() {
		return lou_content;
	}
	public void setLou_content(String lou_content) {
		this.lou_content = lou_content;
	}
	public String getImg_top() {
		return img_top;
	}
	public void setImg_top(String img_top) {
		this.img_top = img_top;
	}
	public String getImg_button() {
		return img_button;
	}
	public void setImg_button(String img_button) {
		this.img_button = img_button;
	}
	public Bitmap getLou_zan() {
		return lou_zan;
	}
	public void setLou_zan(Bitmap lou_zan) {
		this.lou_zan = lou_zan;
	}
	//	private Bitmap mhead;// 用户头像
//	private Bitmap mcomment;// 用户评论
//	private Bitmap mzambia;// 点赞
//	private String name;// 用户名字
//	private String contxt;// 是否是楼主
//	private String time;// 评论登陆时间
//	private String content;// 评论内容
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLou() {
		return lou;
	}
	public void setLou(String lou) {
		this.lou = lou;
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
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
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

	
}
