package com.example.skyimagerhoneybadgers;

import java.util.List;

import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;

public class HardwareSettings{
	public Camera mCamera;
	private int LOW_RES_ID = 9;     //ID to call 480x640 
	
	// ---Hardware Setting Constructor------------------------------------------
	// -Precondition: The app has been run
	// -Postcondition: An instance of HWSettings exists
	public HardwareSettings(Camera intCamera){
		mCamera = intCamera;
	}
	//--------------------------------------------------------------------------
	
	// ---Set Camera Parameters-------------------------------------------------
	// -Precondition: An instance of HWSettings exists
	// -Postcondition: The desired camera settings have been set
	public int setCamParams(){
		Camera.Parameters camParams = mCamera.getParameters();
		camParams.setFlashMode("off");
		camParams.setColorEffect("none");
		camParams.setWhiteBalance("cloudy-daylight");
		camParams.setZoom(0);
		camParams.setSceneMode("landscape");
		List<Camera.Size> sizes = camParams.getSupportedPictureSizes();
		Size size = sizes.get(LOW_RES_ID);
		/*for(int i=0;i<sizes.size();i++){
			size = sizes.get(i);										//Uncomment to logcat picture res and corresponding IDs
			Log.d("CAMRES",i + ": "+ size.height+" "+size.width);
		}*/
		
		Log.d("CAMRES", String.valueOf(size.width) + " " + String.valueOf(size.height));
		camParams.setPictureSize(size.width, size.height);
		mCamera.setParameters(camParams);
		return 0;
	}	
	//--------------------------------------------------------------------------
}
