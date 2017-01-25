// implementation of AR-Experience (aka "World")
var World = {
	// you may request new data from server periodically, however: in this sample data is only requested once
	isRequestingData: false,

	// true once data was fetched
	initiallyLoadedData: false,

	// different POI-Marker assets
	markerDrawable_idle: null,
	markerDrawable_selected: null,
	markerDrawable_directionIndicator: null,

	// list of AR.GeoObjects that are currently shown in the scene / World
	markerList: [],

	// The last selected marker
	currentMarker: null,

	// called to inject new POI data
	loadPoisFromJsonData: function loadPoisFromJsonDataFn(poiData) {

		// empty list of visible markers
		World.markerList = [];

		// start loading marker assets
		World.markerDrawable_idle = new AR.ImageResource("assets/marker_idle.png");
		World.markerDrawable_selected = new AR.ImageResource("assets/marker_selected.png");
		World.markerDrawable_directionIndicator = new AR.ImageResource("assets/indi.png");

        var shopDatas = poiData.results.shop;
		// loop through POI-information and create an AR.GeoObject (=Marker) per POI
		for (var currentPlaceNr = 0; currentPlaceNr < shopDatas.length; currentPlaceNr++) {
		    var imagePathLarge = shopDatas[currentPlaceNr].photo.pc.l;
		    var shopImageResourceLarge = new AR.ImageResource(imagePathLarge);

			var singlePoi = {
            				"id": shopDatas[currentPlaceNr].id,
            				"latitude": parseFloat(shopDatas[currentPlaceNr].lat),
            				"longitude": parseFloat(shopDatas[currentPlaceNr].lng),
            				"altitude": 100,
            				"title": shopDatas[currentPlaceNr].name,
            				"description": shopDatas[currentPlaceNr].name_kana,
            				"imageResourceLarge": shopImageResourceLarge,
            				"url": shopDatas[currentPlaceNr].urls.pc
            			};

			World.markerList.push(new Marker(singlePoi));
		}

		World.updateStatusMessage(currentPlaceNr + ' places loaded');
	},

	// updates status message shon in small "i"-button aligned bottom center
	updateStatusMessage: function updateStatusMessageFn(message, isWarning) {

		var themeToUse = isWarning ? "e" : "c";
		var iconToUse = isWarning ? "alert" : "info";

		$("#status-message").html(message);
		$("#popupInfoButton").buttonMarkup({
			theme: themeToUse
		});
		$("#popupInfoButton").buttonMarkup({
			icon: iconToUse
		});
	},


    // user clicked an image -> fire event to open native screen
    onPoiImageClicked: function onPoiImageClickedFn() {
    	var currentMarker = World.currentMarker;
    	var architectSdkUrl = "architectsdk://markerselected?id=" + encodeURIComponent(currentMarker.poiData.id) + "&title=" + encodeURIComponent(currentMarker.poiData.title) + "&description=" + encodeURIComponent(currentMarker.poiData.description) + "&url=" + encodeURIComponent(currentMarker.poiData.url);
    	/*
    		The urlListener of the native project intercepts this call and parses the arguments.
    		This is the only way to pass information from JavaSCript to your native code.
    		Ensure to properly encode and decode arguments.
    		Note: you must use 'document.location = "architectsdk://...' to pass information from JavaScript to native.
    		! This will cause an HTTP error if you didn't register a urlListener in native architectView !
    	*/
    	document.location = architectSdkUrl;
    },

	// location updates, fired every time you call architectView.setLocation() in native environment
	// Note: You may set 'AR.context.onLocationChanged = null' to no longer receive location updates in World.locationChanged.
	locationChanged: function locationChangedFn(lat, lon, alt, acc) {

		// request data if not already present
		if (!World.initiallyLoadedData) {
			World.requestDataFromServer(lat, lon);
			World.initiallyLoadedData = true;
		}
	},

	// fired when user pressed maker in cam
	onMarkerSelected: function onMarkerSelectedFn(marker) {

		// deselect previous marker
		if (World.currentMarker) {
			if (World.currentMarker.poiData.id == marker.poiData.id) {
				return;
			}
			World.currentMarker.setDeselected(World.currentMarker);
		}

		// highlight current one
		marker.setSelected(marker);
		World.currentMarker = marker;
	},

	// screen was clicked but no geo-object was hit
	onScreenClick: function onScreenClickFn() {
		if (World.currentMarker) {
			World.currentMarker.setDeselected(World.currentMarker);
		}
		World.currentMarker = null;
	},

	/*
		JQuery provides a number of tools to load data from a remote origin.
		It is highly recommended to use the JSON format for POI information. Requesting and parsing is done in a few lines of code.
		Use e.g. 'AR.context.onLocationChanged = World.locationChanged;' to define the method invoked on location updates.
		In this sample POI information is requested after the very first location update.
		This sample uses a test-service of Wikitude which randomly delivers geo-location data around the passed latitude/longitude user location.
		You have to update 'ServerInformation' data to use your own own server. Also ensure the JSON format is same as in previous sample's 'myJsonData.js'-file.
	*/
	// request POI data
	requestDataFromServer: function requestDataFromServerFn(lat, lon) {

		// set helper var to avoid requesting places while loading
		World.isRequestingData = true;
		World.updateStatusMessage('Requesting places from web-service');

		// server-url to JSON content provider
		var serverUrl = ServerInformation.POIDATA_SERVER + "&" + ServerInformation.POIDATA_SERVER_ARG_LAT + "=" + lat + "&" + ServerInformation.POIDATA_SERVER_ARG_LON + "=" + lon + "&" + ServerInformation.POIDATA_SERVER_ARG_NR_POIS + "=30";

		var jqxhr = $.getJSON(serverUrl, function(data) {
				World.loadPoisFromJsonData(data);
			})
			.error(function(err) {
				World.updateStatusMessage("Invalid web-service response.", true);
				World.isRequestingData = false;
			})
			.complete(function() {
				World.isRequestingData = false;
			});
	}

};

/* forward locationChanges to custom function */
AR.context.onLocationChanged = World.locationChanged;

/* forward clicks in empty area to World */
AR.context.onScreenClick = World.onScreenClick;

// information about server communication. This sample webservice is provided by Wikitude and returns random dummy places near given location
// HotpepperAPIと連携。
var ServerInformation = {
	POIDATA_SERVER: "https://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key=e8c791dd3c21d317&format=json",
	POIDATA_SERVER_ARG_LAT: "lat",
	POIDATA_SERVER_ARG_LON: "lng",
	POIDATA_SERVER_ARG_NR_POIS: "count"
};
