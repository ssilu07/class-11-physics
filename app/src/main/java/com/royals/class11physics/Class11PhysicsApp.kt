package com.royals.class11physics

import android.app.Application
import com.google.android.gms.ads.MobileAds

class Class11PhysicsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }
}
