package com.youtubes.listview_badge

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.youtubes.R

class RecyclerViewActivity : AppCompatActivity() {


    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)


       // recyclerView.layoutManager = LinearLayoutManager(this)
      //  recyclerView.adapter = RecyclerAdapter()
    }


  /*  internal inner class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {

        private val data: List<String> = DataSupport().data

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(LayoutInflater.from(this@RecyclerViewActivity).inflate(R.layout.item_view, parent, false))
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.textView.text = data[position]
            holder.badge.badgeNumber = position
        }

        override fun getItemCount(): Int {
            return data.size
        }

        internal inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var textView: TextView = itemView.findViewById(R.id.tv_content)
            var badge: Badge = QBadgeView(this@RecyclerViewActivity).bindTarget(itemView.findViewById(R.id.root))

            init {
                badge.badgeGravity = Gravity.CENTER or Gravity.END
                badge.setBadgeTextSize(14f, true)
                badge.setBadgePadding(6f, true)
                badge.setOnDragStateChangedListener { dragState, badge, targetView ->
                    if (dragState == Badge.OnDragStateChangedListener.STATE_SUCCEED) {
                        Toast.makeText(this@RecyclerViewActivity, adapterPosition.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
*/}