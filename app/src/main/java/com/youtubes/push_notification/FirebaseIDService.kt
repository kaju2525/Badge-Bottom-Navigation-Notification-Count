package com.youtubes.push_notification

import android.util.Log

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class FirebaseIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)
        storeToken(refreshedToken)
    }

    private fun storeToken(token: String?) {
        //saving the token on shared preferences
        //  SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);
    }

    companion object {
        private val TAG = "MyFirebaseIIDService"
    }
}