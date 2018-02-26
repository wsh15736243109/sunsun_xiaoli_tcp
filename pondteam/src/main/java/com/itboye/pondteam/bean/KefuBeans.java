package com.itboye.pondteam.bean;

import android.text.Spanned;

import java.util.List;

public class KefuBeans {
	
	private int countl;
	String status,queueid,createTime,servicerUid,head,youname,youhead;
	Spanned text;
	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getYouname() {
		return youname;
	}

	public void setYouname(String youname) {
		this.youname = youname;
	}

	public String getYouhead() {
		return youhead;
	}

	public void setYouhead(String youhead) {
		this.youhead = youhead;
	}

	public String getServicerUid() {
		return servicerUid;
	}

	public void setServicerUid(String servicerUid) {
		this.servicerUid = servicerUid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Spanned getText() {
		return text;
	}

	public void setText(Spanned text) {
		this.text = text;
	}

	public String getQueueid() {
		return queueid;
	}

	public void setQueueid(String queueid) {
		this.queueid = queueid;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	private List<CounBeans> lsit;
	public int getCountl() {
		return countl;
	}
	public void setCountl(int countl) {
		this.countl = countl;
	}
	public List<CounBeans> getLsit() {
		return lsit;
	}
	public void setLsit(List<CounBeans> lsit) {
		this.lsit = lsit;
	}
	public static   class CounBeans{
		boolean have;
		private Spanned coutent;
		public boolean isHave() {
			return have;
		}
		public void setHave(boolean have) {
			this.have = have;
		}
		public Spanned getCoutent() {
			return coutent;
		}
		public void setCoutent(Spanned coutent) {
			this.coutent = coutent;
		}
	}

}
