package com.cettco.buycar.dealer.entity;

import java.util.Map;

public class Tender {
	private String model;
	private String trim_id;
	private String colors_id;
	private String pickup_time;
	private String license_location;
	private String got_licence;
	private String loan_option;
	private String price;
	private Map<String, String> shops;
	public Map<String, String> getShops() {
		return shops;
	}
	public void setShops(Map<String, String> shops) {
		this.shops = shops;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getTrim_id() {
		return trim_id;
	}
	public void setTrim_id(String trim_id) {
		this.trim_id = trim_id;
	}
	public String getColors_id() {
		return colors_id;
	}
	public void setColors_id(String colors_id) {
		this.colors_id = colors_id;
	}
	public String getPickup_time() {
		return pickup_time;
	}
	public void setPickup_time(String pickup_time) {
		this.pickup_time = pickup_time;
	}
	public String getLicense_location() {
		return license_location;
	}
	public void setLicense_location(String license_location) {
		this.license_location = license_location;
	}
	public String getGot_licence() {
		return got_licence;
	}
	public void setGot_licence(String got_licence) {
		this.got_licence = got_licence;
	}
	public String getLoan_option() {
		return loan_option;
	}
	public void setLoan_option(String loan_option) {
		this.loan_option = loan_option;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
