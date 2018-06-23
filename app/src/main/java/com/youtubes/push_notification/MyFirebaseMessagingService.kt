package com.youtubes.push_notification


import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import android.text.Html
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.youtubes.listview_badge.RecyclerViewActivity
import com.youtubes.R
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL




class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        Log.d(TAG, "From: " + remoteMessage!!.from!!)


        if (remoteMessage.data.isNotEmpty()) {


            val title = remoteMessage.data["title"]
            val message = remoteMessage.data["message"]
            val clickAction = remoteMessage.data["clickAction"]
            val action_url = remoteMessage.data["action_url"]

            val avatarUrl = remoteMessage.data["avatarUrl"]
            val appSetPackage = remoteMessage.data["appSetPackage"]
            val youtubeQuery = remoteMessage.data["youtubeQuery"]

            val ride_id = remoteMessage.data["ride_id"]




            // open webSite & google play
            if (clickAction == "1") {
                openGooglePlay(title!!, message!!, "https://play.google.com/store/apps/details?id=$action_url")
            }

            //open youtube
            if (clickAction == "2") {
                //Website
                openGooglePlay(title!!, message!!, action_url!!)

            }

            // open youtube with image
            if (clickAction == "3") {
                val bitmap = getBitmapFromURL(avatarUrl!!)
                openYoutubeWithImage(bitmap, title!!, message!!,appSetPackage!!, youtubeQuery!!)
            }

            //open Activity With Messgae
            if (clickAction == "4") {
                openActivityWithMessgae(title!!,message!!,ride_id!!)
            }

            // open Activity With Image
            if (clickAction == "5") {
                val bitmap = getBitmapFromURL(avatarUrl!!)
                openActivityWithImage(bitmap,title!!,message!!,ride_id!!)
            }

        }
    }

    // open webSite & google play
    private fun openGooglePlay(title: String, message: String, link: String) {
        val pendingIntent: PendingIntent
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.data = Uri.parse(link)


        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mNotifyBuilder = NotificationCompat.Builder(this, "")
                .setAutoCancel(false)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri)
                .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
                .setSmallIcon(com.youtubes.R.drawable.ic_stat_name)
                .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_noti_big))
                .setContentText(message)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (NOTIFICATION_ID > 1073741824) {
            NOTIFICATION_ID = 0
        }
        notificationManager.notify(NOTIFICATION_ID++, mNotifyBuilder.build())
    }

    //open youtube with image
    private fun openYoutubeWithImage(bitmap: Bitmap?,title: String, message: String, setPackage: String, query: String) {

        val pendingIntent: PendingIntent
        val intent = Intent(Intent.ACTION_SEARCH)
        intent.`package` = setPackage
        intent.putExtra("query", query)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)

        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        val bigPictureStyle = NotificationCompat.BigPictureStyle()
        bigPictureStyle.setBigContentTitle(title)
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString())
        bigPictureStyle.bigPicture(bitmap)
        val mNotifyBuilder = NotificationCompat.Builder(this, "")
                .setAutoCancel(false)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri)
                .setStyle(bigPictureStyle)
                .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
                .setSmallIcon(R.drawable.ic_stat_name)
                .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_noti_big))
                .setContentText(message)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (NOTIFICATION_ID > 1073741824) {
            NOTIFICATION_ID = 0
        }
        notificationManager.notify(NOTIFICATION_ID++, mNotifyBuilder.build())
    }


    // open Activity With Messgae
    private fun openActivityWithMessgae(title: String, message: String, ride_id: String) {

        val intent = Intent(this, RecyclerViewActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("ride_id", ride_id)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mNotifyBuilder = NotificationCompat.Builder(this, "")
                .setAutoCancel(false)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri)
                .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
                .setSmallIcon(R.drawable.ic_stat_name)
                .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_email))
                .setContentText(message)


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (NOTIFICATION_ID > 1073741824) {
            NOTIFICATION_ID = 1
        }



        val i=Intent("Message_KEY")
        i.putExtra("key",NOTIFICATION_ID)
        LocalBroadcastManager.getInstance(this).sendBroadcast(i)


        notificationManager.notify(NOTIFICATION_ID++, mNotifyBuilder.build())



    }


    // open Activity With Image
    private fun openActivityWithImage(bitmap: Bitmap?,title: String, message: String, ride_id: String) {

        val intent = Intent(this, RecyclerViewActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val bigPictureStyle = NotificationCompat.BigPictureStyle()
        bigPictureStyle.setBigContentTitle(title)
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString())
        bigPictureStyle.bigPicture(bitmap)
        val mNotifyBuilder = NotificationCompat.Builder(this, "")
                .setAutoCancel(false)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri)
                .setStyle(bigPictureStyle)
                .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
                .setSmallIcon(R.drawable.ic_stat_name)
                .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_noti_big))
                .setContentText(message)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (NOTIFICATION_ID > 1073741824) {
            NOTIFICATION_ID = 0
        }
        notificationManager.notify(NOTIFICATION_ID++, mNotifyBuilder.build())
    }


    private fun getBitmapFromURL(strURL: String): Bitmap? {
        try {
            val url = URL(strURL)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }

    companion object {
        var NOTIFICATION_ID = 1



        private val TAG = "FirebaseMessageService"
    }



}
