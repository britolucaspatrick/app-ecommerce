package com.example.patri.refinado.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import com.example.patri.refinado.CamadaDados.Categoria
import com.example.patri.refinado.R

class ExpandableCategoriaAdapter(var context : Context, var expandableListView: ExpandableListView, var header : ArrayList<Categoria>, var body :ArrayList<ArrayList<Categoria>>) : BaseExpandableListAdapter(){

    override fun getGroup(groupPosition: Int): String? {
        return header[groupPosition].nome
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        if (convertView == null){
            val inflate = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflate.inflate(R.layout.expandable_categoria_group, null)
        }
        val txt_titulo_root = convertView?.findViewById<TextView>(R.id.txt_titulo_root)
        txt_titulo_root?.text = getGroup(groupPosition)

        //Para expandir ao click
        txt_titulo_root?.setOnClickListener {
            if (expandableListView.isGroupExpanded(groupPosition)){
                expandableListView.collapseGroup(groupPosition)
            }else{
                expandableListView.expandGroup(groupPosition)
            }
        }

        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return body[groupPosition].size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): String? {
        return body[groupPosition][childPosition].nome
    }

    override fun getGroupId(groupPosition: Int): Long {
        return header[groupPosition].id
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        if (convertView == null){
            val inflate = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflate.inflate(R.layout.expandable_categoria_child, null)
        }
        val txt_titulo_child = convertView?.findViewById<TextView>(R.id.txt_titulo_child)
        txt_titulo_child?.text = getChild(groupPosition, childPosition)

        return convertView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return body[groupPosition][childPosition].id
    }

    override fun getGroupCount(): Int {
        return header.size
    }

}