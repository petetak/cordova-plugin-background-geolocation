package com.tenforwardconsulting.cordova.bgloc;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import com.tenforwardconsulting.cordova.bgloc.data.DAOFactory;
import com.tenforwardconsulting.cordova.bgloc.data.LocationDAO;
import org.json.JSONException;
import org.json.JSONObject;

public class BackgroundGpsPlugin extends CordovaPlugin {
    private static final String TAG = "BackgroundGpsPlugin";

    public static final String ACTION_START = "start";
    public static final String ACTION_STOP = "stop";
    public static final String ACTION_CONFIGURE = "configure";
    public static final String ACTION_SET_CONFIG = "setConfig";
	public static final String ACTION_GET_ALL_POINTS = "getAllPoints";
	public static final String ACTION_DELETE_ALL_POINTS = "deleteAllPoints";

    private Intent updateServiceIntent;
    
    private Boolean isEnabled = false;
    
    private String url;
    private String params;
    private String stationaryRadius = "30";
    private String desiredAccuracy = "100";
    private String distanceFilter = "30";
    private String locationTimeout = "60";
    private String isDebugging = "false";
    
    public String getDriveJson() {

        LocationDAO locationDAO = DAOFactory.createLocationDAO(this.cordova.getActivity().getApplicationContext());
        JSONObject drive = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{
            for (com.tenforwardconsulting.cordova.bgloc.data.Location l : locationDAO.getAllLocations()) {
                //Log.d(TAG, "saved location lat"+savedLocation.getLatitude()+" date "+savedLocation.getRecordedAt() + " speed "+savedLocation.getSpeed());
                JSONObject location = new JSONObject();
                location.put("latitude", l.getLatitude());
                location.put("longitude", l.getLongitude());
                location.put("accuracy", l.getAccuracy());
                location.put("speed", l.getSpeed());
                location.put("recorded_at", l.getRecordedAt());
                jsonArray.put(location);
                
            }
            drive.put("drive", jsonArray);
        }
        catch (JSONException jsonException){
            return jsonException.getMessage();
        }

        return drive.toString();

    }

    public void deletePreviousDrive() {

        LocationDAO locationDAO = DAOFactory.createLocationDAO(this.cordova.getActivity().getApplicationContext());
        locationDAO.deleteAllLocations();
    }

    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) {
        Activity activity = this.cordova.getActivity();
        Boolean result = false;
        updateServiceIntent = new Intent(activity, LocationUpdateService.class);
        
        if (ACTION_START.equalsIgnoreCase(action) && !isEnabled) {
            result = true;
            if (params == null || url == null) {
                callbackContext.error("Call configure before calling start");
            } else {
                callbackContext.success();
                updateServiceIntent.putExtra("url", url);
                updateServiceIntent.putExtra("params", params);
                updateServiceIntent.putExtra("stationaryRadius", stationaryRadius);
                updateServiceIntent.putExtra("desiredAccuracy", desiredAccuracy);
                updateServiceIntent.putExtra("distanceFilter", distanceFilter);
                updateServiceIntent.putExtra("locationTimeout", locationTimeout);
                updateServiceIntent.putExtra("desiredAccuracy", desiredAccuracy);
                updateServiceIntent.putExtra("isDebugging", isDebugging);

                activity.startService(updateServiceIntent);
                isEnabled = true;
            }
        } else if (ACTION_STOP.equalsIgnoreCase(action)) {
            isEnabled = false;
            result = true;
            activity.stopService(updateServiceIntent);
            callbackContext.success();
		}
		else if (ACTION_GET_ALL_POINTS.equalsIgnoreCase(action)) {
            result = true;
            this.cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    String res = getDriveJson();
                    callbackContext.success(res);
                }
            });
        }
		else if (ACTION_DELETE_ALL_POINTS.equalsIgnoreCase(action)) {
           	result = true;
            //activity.deleteAllPointsService(updateServiceIntent);
            callbackContext.success();
        } else if (ACTION_CONFIGURE.equalsIgnoreCase(action)) {
            result = true;
            try {
                // [params, url, stationaryRadius, distanceFilter, locationTimeout, desiredAccuracy, debug]);
                this.params = data.getString(0);
                this.url = data.getString(1);
                this.stationaryRadius = data.getString(2);
                this.distanceFilter = data.getString(3);
                this.locationTimeout = data.getString(4);
                this.desiredAccuracy = data.getString(5);
                this.isDebugging = data.getString(6);

            } catch (JSONException e) {
                callbackContext.error("authToken/url required as parameters: " + e.getMessage());
            }
        } else if (ACTION_SET_CONFIG.equalsIgnoreCase(action)) {
            result = true;
            // TODO reconfigure Service
            callbackContext.success();
        }

        return result;
    }
}
