package com.youtubes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.youtubes.listview_badge.DataModel
import com.youtubes.push_notification.MyFirebaseMessagingService
import kotlinx.android.synthetic.main.activity_main.*
import q.rorbin.badgeview.Badge
import q.rorbin.badgeview.QBadgeView


class MainActivity : AppCompatActivity() {


    private lateinit var context: Context
    private val receiver: BroadcastReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

                val s = intent!!.getIntExtra("key", 0)
                addBadgeAt(2, s)
            }
        }
    }

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val localBroadcastManager: LocalBroadcastManager by lazy { LocalBroadcastManager.getInstance(context) }
    private lateinit var mBadge: QBadgeView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this@MainActivity


        recyclerView()
        mBadge = QBadgeView(this)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)



        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);

        navigation.setIconSize(22f, 22f);
        navigation.setTextSize(10F);


        localBroadcastManager.registerReceiver(receiver, IntentFilter("Message_KEY"))


    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                //  message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                // message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {

                mBadge.hide(true)
                MyFirebaseMessagingService.NOTIFICATION_ID = 1

                //  message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dwonload -> {
                //   message.setText(R.string.title_dwonload)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_email -> {
                //  message.setText(R.string.title_email)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onPause() {
        super.onPause()
        localBroadcastManager.unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        localBroadcastManager.unregisterReceiver(receiver)
    }


    private fun addBadgeAt(position: Int, number: Int): Badge {
        // add badge
        return mBadge
                .setBadgeNumber(number)
                .setGravityOffset(12f, 2f, true)
                .bindTarget(navigation.getBottomNavigationItemView(position))
                .setOnDragStateChangedListener { dragState, badge, targetView ->
                    if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState)
                        Toast.makeText(this@MainActivity, "tips_badge_removed", Toast.LENGTH_SHORT).show()
                }
    }


    // recyclerView

    private fun recyclerView() {

         val data= ArrayList<DataModel>()

        data.add(DataModel("http://3.bp.blogspot.com/-ioAui798P6g/UCOsn5PFTDI/AAAAAAAABdw/1X3QFWFJGOo/s1600/Amazing+London+Bridge+facebook+1.jpg","Londan","32"))
        data.add(DataModel("http://www.randomlynew.com/wp-content/uploads/2014/11/ceramic-poppies-at-tower-of-londan-4.jpg","Parish","434"))
        data.add(DataModel("http://www.airphotona.com/stockimg/images/03112.jpg","Airport","423"))
        data.add(DataModel("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/City_of_London_skyline_from_London_City_Hall_-_Sept_2015_-_Crop_Aligned.jpg/1200px-City_of_London_skyline_from_London_City_Hall_-_Sept_2015_-_Crop_Aligned.jpg","City of Londan",""))
        data.add(DataModel("http://www.skalondon.com/uploaded/nmpt12dec3%201.jpg","SKA Lodan","343"))


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerAdapter(data)

    }

    internal inner class RecyclerAdapter(val list:ArrayList<DataModel>) : RecyclerView.Adapter<RecyclerAdapter.Holder>() {






        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false))
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val li:DataModel=list[position]
            holder.textView.text =li.name
            holder.tv_time.text =li.time
            holder.badge.badgeNumber = position
            Glide.with(context).load(li.avatar).into(holder.img);


        }

        override fun getItemCount(): Int {
            return list.size
        }

        internal inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var textView: TextView = itemView.findViewById(R.id.tv_content)
            var tv_time: TextView = itemView.findViewById(R.id.tv_time)
            var img: ImageView = itemView.findViewById(R.id.imageview)
            var badge: Badge = QBadgeView(this@MainActivity).bindTarget(itemView.findViewById(R.id.root))

            init {
                badge.badgeGravity = Gravity.CENTER or Gravity.END
                badge.setBadgeTextSize(14f, true)
                badge.setBadgePadding(6f, true)
                badge.setOnDragStateChangedListener { dragState, badge, targetView ->
                    if (dragState == Badge.OnDragStateChangedListener.STATE_SUCCEED) {
                        Toast.makeText(this@MainActivity, adapterPosition.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


}
