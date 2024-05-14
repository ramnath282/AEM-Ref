package com.mattel.ag.retail.core.pojos;

/**
 * @author CTS. A simple pojo for getting start and end time for store/bistro
 *         hours.
 */
public class StoreBistroHoursPojo {

	private String storeStartTime;
	private String storeEndTime;
	private boolean storeStoreClosed;
	private String bistroStartTime;
	private String bistroEndTime;
	private boolean bistroStoreClosed;
	private int storeEndDate;
	private int bistroEndDate;
	private String storeEndMonth;
	private String bistroEndMonth;
	private String month;
	private int date;

	public String getStoreEndMonth() {
		return storeEndMonth;
	}

	public void setStoreEndMonth(String storeEndMonth) {
		this.storeEndMonth = storeEndMonth;
	}

	public String getBistroEndMonth() {
		return bistroEndMonth;
	}

	public void setBistroEndMonth(String bistroEndMonth) {
		this.bistroEndMonth = bistroEndMonth;
	}

	public String getStoreStartTime() {
		return storeStartTime;
	}

	public void setStoreStartTime(String storeStartTime) {
		this.storeStartTime = storeStartTime;
	}

	public int getStoreEndDate() {
		return storeEndDate;
	}

	public void setStoreEndDate(int storeEndDate) {
		this.storeEndDate = storeEndDate;
	}

	public String getStoreEndTime() {
		return storeEndTime;
	}

	public int getBistroEndDate() {
		return bistroEndDate;
	}

	public void setBistroEndDate(int bistroEndDate) {
		this.bistroEndDate = bistroEndDate;
	}

	public void setStoreEndTime(String storeEndTime) {
		this.storeEndTime = storeEndTime;
	}

	public String getBistroStartTime() {
		return bistroStartTime;
	}

	public void setBistroStartTime(String bistroStartTime) {
		this.bistroStartTime = bistroStartTime;
	}

	public String getBistroEndTime() {
		return bistroEndTime;
	}

	public void setBistroEndTime(String bistroEndTime) {
		this.bistroEndTime = bistroEndTime;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public boolean isStoreStoreClosed() {
		return storeStoreClosed;
	}

	public void setStoreStoreClosed(boolean storeStoreClosed) {
		this.storeStoreClosed = storeStoreClosed;
	}

	public boolean isBistroStoreClosed() {
		return bistroStoreClosed;
	}

	public void setBistroStoreClosed(boolean bistroStoreClosed) {
		this.bistroStoreClosed = bistroStoreClosed;
	}

	@Override
	public String toString() {
		return "StoreBistroHoursPojo [storeStartTime=" + storeStartTime + ", storeEndTime=" + storeEndTime
				+ ", bistroStartTime=" + bistroStartTime + ", bistroEndTime=" + bistroEndTime + ", storeEndDate="
				+ storeEndDate + ", bistroEndDate=" + bistroEndDate + ", month=" + month + ", date=" + date + "]";
	}

}
