package com.rupalimandge.whereareyou;

/**
 * Created by Rupali on 20/01/16.
 */
public class DeviceInfo {

    String deviceId = null;
    String username = null;
    String mobileNo = null;

    public DeviceInfo( String deviceId,String username,String mobileNo){
        this.deviceId = deviceId;
        this.username = username;
        this.mobileNo = mobileNo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
