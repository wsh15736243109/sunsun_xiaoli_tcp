package com.itboye.pondteam.bean;

import android.text.Spanned;

import java.util.List;

public class KefuBeans {
	
	private int countl;
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
