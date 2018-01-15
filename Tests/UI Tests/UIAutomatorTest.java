package com.example.matan.library;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Pair;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.uiautomator.UiDevice.getInstance;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by matan on 15/01/2018.
 */
@RunWith(AndroidJUnit4.class)

public class UIAutomatorTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);


    @Before
    public void setupComponents(){

    }

    @Test
    public void testPress(){
        getInstance(InstrumentationRegistry.getInstrumentation());

    }

    @Test
    public void testUI() throws RemoteException{
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        if(device.isScreenOn()){
            device.setOrientationLeft();
            device.openNotification();
            device.openQuickSettings();
            device.pressHome();
        }
    }


}