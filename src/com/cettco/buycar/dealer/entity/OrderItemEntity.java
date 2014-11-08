package com.cettco.buycar.dealer.entity;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "orders")
public class OrderItemEntity {
	@DatabaseField(generatedId = true)
	private int order_id;
	@DatabaseField
	private String id;
	@DatabaseField
	private String model_id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String trim;
	@DatabaseField
	private String trim_id;
	@DatabaseField
	private String price;
	@DatabaseField
	private String bidNum;
	@DatabaseField
	private String state;
	@DatabaseField
	private String url;
	@DatabaseField
	private Date time;
	@DatabaseField
	private String pic_url;
	public String getBid_id() {
		return bid_id;
	}
	public void setBid_id(String bid_id) {
		this.bid_id = bid_id;
	}
	public String getBider() {
		return bider;
	}
	public void setBider(String bider) {
		this.bider = bider;
	}
	@DatabaseField
	private String model;
	@DatabaseField
	private String bid_id;
	@DatabaseField
	private String bider;
	@DatabaseField
	private String bargain_id;
	public String getBargain_id() {
		return bargain_id;
	}
	public void setBargain_id(String bargain_id) {
		this.bargain_id = bargain_id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	private String updated_at;
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPic_url() {
		return pic_url;
	}
	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}
	public String getId() {
		return id;
	}
	public String getTrim_id() {
		return trim_id;
	}
	public void setTrim_id(String trim_id) {
		this.trim_id = trim_id;
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
	public String getTrim() {
		return trim;
	}
	public void setTrim(String trim) {
		this.trim = trim;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getBidNum() {
		return bidNum;
	}
	public void setBidNum(String bidNum) {
		this.bidNum = bidNum;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getModel_id() {
		return model_id;
	}
	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
