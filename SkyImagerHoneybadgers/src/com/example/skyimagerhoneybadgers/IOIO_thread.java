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
	MainActivity the_gui;					// reference to the main activity

	public IOIO_thread(MainActivity gui)
	{
		the_gui = gui;
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
			final float reading = input_.read();
			MainActivity.ioioData = reading*360f;
			flash = !flash;

			pwmOutput1_.setDutyCycle(0.4f);
			pwmOutput2_.setDutyCycle(0.0f);
			led_.write(flash);

			Thread.sleep(100);
		} catch (InterruptedException e) {
			ioio_.disconnect();
		} catch (ConnectionLostException e) {
			throw e;
		}
	}
}