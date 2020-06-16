package com.example.patri.refinado.Adapter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.patri.refinado.CamadaDados.Cor
import com.example.patri.refinado.R

class CorAdapter (private var activity: Activity, private var items: ArrayList<Cor>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var cor: ImageView? = null
        var hexadecimal: TextView? = null

        init {
            this.cor = row?.findViewById(R.id.cor_pr)
            this.hexadecimal = row?.findViewById(R.id.hexadecimal_pr)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.z_custom_list_cor_pr, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var cor = items[position]
        viewHolder.cor?.setBackgroundColor(Color.parseColor(cor.hexadecimal))
        viewHolder.hexadecimal?.text = cor.hexadecimal

        return view as View
    }

    override fun getItem(position: Int): Cor {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun getCount(): Int {
        return items.size
    }

}