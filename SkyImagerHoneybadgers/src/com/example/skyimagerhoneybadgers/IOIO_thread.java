package com.example.skyimagerhoneybadgers;

import ioio.lib.api.AnalogInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;

public class IOIO_thread extends BaseIOIOLooper 
{
	private AnalogInput input_;
	private PwmOutput pwmOutput1_;
	private PwmOutput pwmOutput2_;
	private DigitalOutput led_;
	private boolean flash = false;
	private float coolDutyCycle;
	private float heatDutyCycle;
	MainActivity mainact;	// reference to the main activity
	private float batt_temp;

	public IOIO_thread(MainActivity main)
	{
		mainact = main;
	}

	@Override
	public void setup() throws ConnectionLostException 
	{
		try {
			
			input_ = ioio_.openAnalogInput(46);
			pwmOutput1_ = ioio_.openPwmOutput(6, 100000);
			pwmOutput2_ = ioio_.openPwmOutput(7, 100000);
			led_ = ioio_.openDigitalOutput(IOIO.LED_PIN, true);

		} catch (ConnectionLostException e) 
		{
			throw e;
		}
	}

	@Override
	public void loop() throws ConnectionLostException 
	{
		try 
		{
			final float windDir = input_.read();
			MainActivity.ioioData = windDir*360f;
			flash = !flash;
			batt_temp = mainact.myBatInfoReceiver.get_temp();
			if(batt_temp >= 350.0f){
				coolDutyCycle = 0.2f;
				heatDutyCycle = 0.0f;
			}
			else if(batt_temp <= 70.0f){
				coolDutyCycle = 0.0f;
				heatDutyCycle = 0.2f;
			}
			else if(batt_temp > 75.0f && batt_temp < 340.0f){
				coolDutyCycle = 0.0f;
				heatDutyCycle = 0.0f;
			}
			
			pwmOutput1_.setDutyCycle(java.lang.Math.min(coolDutyCycle,0.35f));
			pwmOutput2_.setDutyCycle(java.lang.Math.min(heatDutyCycle,0.35f));
			led_.write(flash);

			Thread.sleep(1000);
		} catch (InterruptedException e) {
			ioio_.disconnect();
		} catch (ConnectionLostException e) {
			throw e;
		}
	}
}