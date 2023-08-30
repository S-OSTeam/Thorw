package com.example.throw_fornt.feature.store.register.address

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.throw_fornt.R
import com.example.throw_fornt.data.model.response.DocumentResponse
import com.example.throw_fornt.data.model.response.StoreModel
import org.w3c.dom.Text

class AddressAdapter(
    private var items: List<DocumentResponse>,
    val onClick: (DocumentResponse)->Unit
): RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.addree_list_item, parent,false)
        return AddressViewHolder(view)
    }

    //list_view_item.xml과 연동된 TextView의 값을 리스트 마다 지정해줌
    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.address.text =
            if(items[position].address!=null && items[position].address?.address.isNullOrEmpty().not())
                items[position].address?.address
            else "지번 주소"
        holder.roadAddress.text =
            if(items[position].roadAddress!=null && items[position].roadAddress?.address.isNullOrEmpty().not())
                items[position].roadAddress?.address
            else "도로명 주소"
        holder.zoneNo.text =
            if(items[position].roadAddress!=null && items[position].roadAddress?.zoneNo.isNullOrEmpty().not())
                items[position].roadAddress?.zoneNo
            else "우편번호"
        holder.itemView.setOnClickListener{
            onClick(items[position])
        }
    }

    fun updateList(list: ArrayList<DocumentResponse>){
        items = list
        notifyDataSetChanged()
    }

    //items의 갯수를 반환하여 RecyclerView에 나타나는 총 갯수를 보여줌
    override fun getItemCount(): Int {
        return items.size
    }

    //list_view_item.xml의 TextView를 연동시켜줌
    inner class AddressViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val address = itemView.findViewById<TextView>(R.id.address)
        val roadAddress = itemView.findViewById<TextView>(R.id.roadAddress)
        val zoneNo = itemView.findViewById<TextView>(R.id.zoneNo)
    }
}