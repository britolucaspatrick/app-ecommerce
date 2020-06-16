package com.example.patri.refinado.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.patri.refinado.Activity.ConfirmacaoDadosActivity
import com.example.patri.refinado.CamadaDados.Pedido
import com.example.patri.refinado.CamadaNegocio.cancelarPedido
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.Constantes.GERAR_CONFIRMACAO
import com.example.patri.refinado.Util.Constantes.ID_PEDIDO
import com.example.patri.refinado.Util.toastLong
import com.orm.SugarRecord

class PedidosAdapter (private var activity: Activity, private var items: ArrayList<Pedido>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txt_numero_pe: TextView? = null
        var txt_totitens_pe: TextView? = null
        var txt_vlsub_pe: TextView? = null
        var txt_status_pe: TextView? = null
        var txt_data_pe: TextView? = null
        var btn_cancelar_c: Button? = null
        var btn_finalizar_c: Button? = null
        var position: Int? = null
        var idPedido: Long? = null


        init {
            this.txt_numero_pe = row?.findViewById(R.id.txt_numero_pe)
            this.txt_totitens_pe = row?.findViewById(R.id.txt_totitens_pe)
            this.txt_vlsub_pe = row?.findViewById(R.id.txt_vlsub_pe)
            this.txt_status_pe = row?.findViewById(R.id.txt_status_pe)
            this.txt_data_pe = row?.findViewById(R.id.txt_data_pe)
            this.btn_cancelar_c = row?.findViewById(R.id.btn_cancelar_c)
            this.btn_finalizar_c = row?.findViewById(R.id.btn_finalizar_c)

        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_pedidoaberto, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var pedido = items[position]
        viewHolder.position = position
        viewHolder.idPedido = pedido.id
        viewHolder.txt_numero_pe?.text = pedido.id.toString()
        viewHolder.txt_totitens_pe?.text = pedido.vltotalitens.toString()
        viewHolder.txt_vlsub_pe?.text = pedido.vlsubtotal.toString()
        viewHolder.txt_status_pe?.text = "Aberto"
        viewHolder.txt_data_pe?.text = pedido.dtpedido.toString()

        viewHolder.btn_finalizar_c!!.setOnClickListener {
            ID_PEDIDO = viewHolder.txt_numero_pe?.text.toString().toLong()
            GERAR_CONFIRMACAO = true
            activity.finish()
        }

        viewHolder.btn_cancelar_c!!.setOnClickListener {
            toastLong(cancelarPedido(viewHolder.idPedido), activity)
            notifyDataSetChanged()
        }
        return view as View
    }

    override fun notifyDataSetChanged() {
        items = SugarRecord.find(Pedido::class.java, "registro = ?", "P") as ArrayList<Pedido>
        super.notifyDataSetChanged()
    }

    override fun getItem(position: Int): Pedido {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun getCount(): Int {
        return items.size
    }

}