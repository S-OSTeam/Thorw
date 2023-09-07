package com.example.throw_fornt.feature.mileage

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.throw_fornt.R
import com.example.throw_fornt.data.model.response.DocumentResponse
import com.example.throw_fornt.data.model.response.MileageResponse
import com.example.throw_fornt.feature.store.register.address.AddressAdapter

class MileageAdapter(
    private var items: List<MileageResponse>,
    val onClick: (MileageResponse)->Unit
): RecyclerView.Adapter<MileageAdapter.MileageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MileageViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rank_list_item, parent,false)
        return MileageViewHolder(view)
    }

    //list_view_item.xml과 연동된 TextView의 값을 리스트 마다 지정해줌
    override fun onBindViewHolder(holder: MileageViewHolder, position: Int) {
        if(items[position].ranking.toInt()==1) {
            holder.rankNumber.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#E8BB4F"))
            holder.rankText.setTextColor(Color.parseColor("#F8EBBA"))
        }
        else if(items[position].ranking.toInt()==2) {
            holder.rankNumber.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#B3D3F0"))
            holder.rankText.setTextColor(Color.parseColor("#E4F2FE"))
        }
        else if(items[position].ranking.toInt()==3) {
            holder.rankNumber.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#E2A179"))
            holder.rankText.setTextColor(Color.parseColor("#EDC3B5"))
        }
        holder.rankText.text = items[position].ranking.toString()
        holder.userText.text = items[position].userName
        holder.mileageText.text = items[position].mileage.toString()

        holder.itemView.setOnClickListener{
            onClick(items[position])
        }
    }

    fun updateList(list: ArrayList<MileageResponse>){
        items = list
        notifyDataSetChanged()
    }

    //items의 갯수를 반환하여 RecyclerView에 나타나는 총 갯수를 보여줌
    override fun getItemCount(): Int {
        return items.size
    }

    //list_view_item.xml의 TextView를 연동시켜줌
    inner class MileageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val rank = itemView.findViewById<LinearLayout>(R.id.rank)
        val rankNumber = itemView.findViewById<FrameLayout>(R.id.rank_number)
        val rankText = itemView.findViewById<TextView>(R.id.rank_text)
        val userText = itemView.findViewById<TextView>(R.id.user_text)
        val mileageText = itemView.findViewById<TextView>(R.id.mileage_text)
    }
}