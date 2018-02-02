package com.itboye.pondteam.bean;

import java.io.Serializable;
import java.util.ArrayList;

/***
 * 导航bean
 * @author admin
 *
 */
public class NavigationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  ["id"] => string(1) "1"
      ["name"] => string(15) "江干区店铺"
      ["lat"] => string(2) "30"
      ["lng"] => string(2) "20"
      ["country"] => string(3) "457"
      ["province"] => string(2) "33"
      ["city"] => string(6) "330100"
      ["area"] => string(6) "310016"
      ["telephone"] => string(8) "64697032"
      ["person_name"] => string(9) "王小明"
      ["mobile"] => string(11) "14628456654"
      ["address_detail"] => string(44) "浙江省杭州市江干区下沙街道12号"
      ["branch_imgs"] => string(9) "1,2,4,6,7"
	 */
	/**
	 *  ["id"] => string(1) "1"
    ["name"] => string(15) "江干区店铺"
    ["lat"] => string(2) "30"
    ["lng"] => string(2) "20"
    ["country"] => string(3) "457"
    ["province"] => string(2) "33"
    ["city"] => string(6) "330100"
    ["area"] => string(6) "310016"
    ["telephone"] => string(8) "64697032"
    ["person_name"] => string(9) "王小明"
    ["mobile"] => string(11) "14628456654"
    ["address_detail"] => string(44) "浙江省杭州市江干区下沙街道12号"
    ["branch_imgs"] => array(4) {
      [0] => string(5) "13624"
      [1] => string(5) "13627"
      [2] => string(5) "13628"
      [3] => string(5) "13630"
    }
	 */
	int count;
	ArrayList<NavigationDetail> list;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ArrayList<NavigationDetail> getList() {
		return list;
	}

	public void setList(ArrayList<NavigationDetail> list) {
		this.list = list;
	}

	public static class NavigationDetail implements Serializable{
		String id;
		String name;
		double lat;
		double lng;
		String telephone;
		String country;
		String province;
		String city;
		String area;
		String personName;
		String mobile;
		String addressDetail;
		String branchImgs;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTelephone() {
			return telephone;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public String getPersonName() {
			return personName;
		}
		public void setPersonName(String personName) {
			this.personName = personName;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getAddressDetail() {
			return addressDetail;
		}
		public void setAddressDetail(String addressDetail) {
			this.addressDetail = addressDetail;
		}
		public String getBranchImgs() {
			return branchImgs;
		}
		public void setBranchImgs(String branchImgs) {
			this.branchImgs = branchImgs;
		}
		public String getPlace() {
			return id;
		}
		public void setPlace(String place) {
			this.id = place;
		}
		public String getAddress() {
			return name;
		}
		public void setAddress(String address) {
			this.name = address;
		}
		public double getLat() {
			return lat;
		}
		public void setLat(double lat) {
			this.lat = lat;
		}
		public double getLng() {
			return lng;
		}
		public void setLng(double lng) {
			this.lng = lng;
		}
		public String getPhone() {
			return telephone;
		}
		public void setPhone(String phone) {
			this.telephone = phone;
		}
	}

}
