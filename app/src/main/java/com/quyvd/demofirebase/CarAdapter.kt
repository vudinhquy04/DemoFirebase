package com.quyvd.demofirebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CarAdapter(var context: Context, var carModelList: List<CarModel>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return carModelList.size
    }

    override fun getItem(i: Int): Any {
        return carModelList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(position: Int, view: View, viewGroup: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.item_user, viewGroup, false)

        val tvID = rowView.findViewById<View>(R.id.tvId) as TextView
        val imgAvatar = rowView.findViewById<View>(R.id.imgAvatatr) as ImageView
        val tvName = rowView.findViewById<View>(R.id.tvName) as TextView

        val tvNamSX = rowView.findViewById<View>(R.id.tvNamSX) as TextView

        val tvHang = rowView.findViewById<View>(R.id.tvHang) as TextView

        val tvGia = rowView.findViewById<View>(R.id.tvGia) as TextView

        //        String imageUrl = mList.get(position).getThumbnailUrl();
//        Picasso.get().load(imageUrl).into(imgAvatar);
////        imgAvatar.setImageResource(imageId[position]);
        tvName.text = carModelList[position].ten.toString()

        tvNamSX.text = carModelList[position].namSX.toString()

        tvHang.text = carModelList[position].hang.toString()

        tvGia.text = carModelList[position].gia.toString()

        return rowView
    }
}
