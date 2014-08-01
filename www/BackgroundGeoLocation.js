var exec = require("cordova/exec");
module.exports = {
    configure: function(success, failure, config) {
        var params              = JSON.stringify(config.params || {}),
            url                 = config.url        || 'BackgroundGeoLocation_url',
            stationaryRadius    = (config.stationaryRadius >= 0) ? config.stationaryRadius : 5,    // meters
            distanceFilter      = (config.distanceFilter >= 0) ? config.distanceFilter : 5,       // meters
            locationTimeout     = (config.locationTimeout >= 0) ? config.locationTimeout : 1,      // seconds
            desiredAccuracy     = (config.desiredAccuracy >= 0) ? config.desiredAccuracy : 5;     // meters
            debug               = config.debug || false;

        exec(success || function() {},
             failure || function() {},
             'BackgroundGeoLocation',
             'configure',
             [params, url, stationaryRadius, distanceFilter, locationTimeout, desiredAccuracy, debug]);
    },
    start: function(success, failure, config) {
        exec(success || function() {},
             failure || function() {},
             'BackgroundGeoLocation',
             'start',
             []);
    },
    stop: function(success, failure, config) {
        exec(success || function() {},
            failure || function() {},
            'BackgroundGeoLocation',
            'stop',
            []);
    },
    finish: function(success, failure) {
        exec(success || function() {},
            failure || function() {},
            'BackgroundGeoLocation',
            'finish',
            []);  
    },
    changePace: function(isMoving, success, failure) {
        exec(success || function() {},
            failure || function() {},
            'BackgroundGeoLocation',
            'onPaceChange',
            [isMoving]);   
    },
	getAllPoints: function(success, failure) {
        exec(success || function() {},
            failure || function() {},
            'BackgroundGeoLocation',
            'getAllPoints',
            []);   
    },
	deleteAllPoints: function(success, failure) {
        exec(success || function() {},
            failure || function() {},
            'BackgroundGeoLocation',
            'deleteAllPoints',
            []);   
    },
    /**
    * @param {Integer} stationaryRadius
    * @param {Integer} desiredAccuracy
    * @param {Integer} distanceFilter
    * @param {Integer} timeout
    */
    setConfig: function(success, failure, config) {
        exec(success || function() {},
            failure || function() {},
            'BackgroundGeoLocation',
            'setConfig',
            [config]);
    },
   /**
    * Returns current stationaryLocation if available.  null if not
    */
   getStationaryLocation: function(success, failure) {
       exec(success || function() {},
            failure || function() {},
            'BackgroundGeoLocation',
            'getStationaryLocation',
            []);
       }
};
