/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Goran Mrzljak
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.turbogerm.helljump;

import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.turbogerm.helljump.HellJump;
import com.turbogerm.helljump.init.InitData;

public class MainActivity extends AndroidApplication {
    
//    private static final String ADMOB_PUBLISHER_ID = "a151d9baa31e05a";
//    
//    private static final String[] TEST_DEVICE_IDS;
//    
//    static {
//        TEST_DEVICE_IDS = new String[] {
//                "353167050265271",
//                "G5S6RC9341004500"
//        };
//    }
//    
//    private InterstitialAd mInterstitialAd;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        cfg.useAccelerometer = true;
        cfg.useCompass = false;
        
        InitData initData = new InitData();
        
//        mInterstitialAd = new InterstitialAd(this, ADMOB_PUBLISHER_ID);
//        mInterstitialAd.loadAd(getAdRequest());
//        mInterstitialAd.setAdListener(getInterstitionAdListener());
//        
//        setWindowFeatures();
//        
//        RelativeLayout layout = new RelativeLayout(this);
//        View gameView = initializeForView(new HellJump(initData), cfg.useGL20);
//        
//        AdView adView = new AdView(this, AdSize.SMART_BANNER, ADMOB_PUBLISHER_ID);
//        adView.loadAd(getAdRequest());
        
        initialize(new HellJump(initData), cfg);
    }
    
//    private static AdRequest getAdRequest() {
//        AdRequest adRequest = new AdRequest();
//        adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
//        for (String testDeviceId : TEST_DEVICE_IDS) {
//            adRequest.addTestDevice(testDeviceId);
//        }
//        
//        return adRequest;
//    }
//    
//    private void setWindowFeatures() {
//        try {
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
//        } catch (Exception ex) {
//            log("AndroidApplication", "Content already displayed, cannot request FEATURE_NO_TITLE", ex);
//        }
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//    }
//    
//    private AdListener getInterstitionAdListener() {
//        return new AdListener() {
//            
//            @Override
//            public void onReceiveAd(Ad ad) {
//                Logger.debug("Recieved ad");
//                mInterstitialAd.show();
//            }
//            
//            @Override
//            public void onPresentScreen(Ad ad) {
//            }
//            
//            @Override
//            public void onLeaveApplication(Ad ad) {
//            }
//            
//            @Override
//            public void onFailedToReceiveAd(Ad ad, ErrorCode error) {
//                Logger.debug("Failed to recieve ad");
//            }
//            
//            @Override
//            public void onDismissScreen(Ad ad) {
//            }
//        };
//    }
}