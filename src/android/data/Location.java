package com.tenforwardconsulting.cordova.bgloc.data;

import java.util.Date;

import android.os.SystemClock;
import java.text.SimpleDateFormat;
import java.util.TimeZone;


public class Location {
	private String latitude;
	private String longitude;
	private Date recordedAt;
	private String accuracy;
	private String speed;
	
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public Date getRecordedAt() {
		return recordedAt;
	}
	public String getISORecordedAt(){
		return toISOString(recordedAt);
	}
	public void setRecordedAt(Date recordedAt) {
		this.recordedAt = recordedAt;
	}
	public String getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}
	public String getSpeed() {
		
		// to give similar output to ios
		if(speed.equals("0.0"))
			return "-1";
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public static String toISOString( Date date ) {
        
      SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss'Z'" );
      
      TimeZone tz = TimeZone.getTimeZone( "UTC" );
      
      df.setTimeZone( tz );

      String output = df.format( date );
      
      return output;
      
  }
	
	public static Location fromAndroidLocation(android.location.Location originalLocation) {
		Location location = new Location();
		location.setRecordedAt(new Date(originalLocation.getTime()));
		location.setLongitude(String.valueOf(originalLocation.getLongitude()));
		location.setLatitude(String.valueOf(originalLocation.getLatitude()));
		location.setAccuracy(String.valueOf(originalLocation.getAccuracy()));
		location.setSpeed(String.valueOf(originalLocation.getSpeed()));
		
		return location;
	}
}
