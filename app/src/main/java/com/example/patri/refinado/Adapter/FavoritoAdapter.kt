package com.example.patri.refinado.Adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.patri.refinado.CamadaDados.Carrinho
import com.example.patri.refinado.CamadaDados.Favorito
import com.example.patri.refinado.CamadaDados.Produto
import com.example.patri.refinado.CamadaNegocio.primeiroProdutoImagem
import com.example.patri.refinado.CamadaNegocio.saldoEstoqueProduto
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.getBitmapImage
import com.example.patri.refinado.Util.toastLong
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.item_favorito.view.*

class FavoritoAdapter (private var activity: Activity, private var items: ArrayList<Favorito>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var imagemProdutoF: ImageView? = null
        var txt_nome_f: TextView? = null
        var btn_carrinho_f: Button? = null
        var btn_remover_f: Button? = null
        var idproduto: Long? = null
        var idfavorito: Long? = null
        var position: Int? = null

        init {
            this.imagemProdutoF = row?.findViewById(R.id.imagemProdutoF)
            this.txt_nome_f = row?.findViewById(R.id.txt_nome_f)
            this.btn_carrinho_f = row?.findViewById(R.id.btn_carrinho_f)
            this.btn_remover_f = row?.findViewById(R.id.btn_remover_f)
        }
    }



    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder

        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_favorito, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var favorito = items[position]
        val produto = primeiroProdutoImagem(favorito.produto)

        viewHolder.idproduto = favorito.produto
        viewHolder.idfavorito = favorito.id
        viewHolder.position = position
        viewHolder.imagemProdutoF?.setImageBitmap(getBitmapImage(produto.listProdutoImagem!![0].imagem!!))
        viewHolder.txt_nome_f?.text = produto.nome

        viewHolder.btn_carrinho_f?.setOnClickListener {
            if (saldoEstoqueProduto(viewHolder.idproduto!!)){
                val carrinho = Carrinho(produto.id, 1, produto.preco)
                carrinho.save()
                toastLong("O produto possui saldo em estoque e foi adicionado ao carrinho.", activity)
                activity.finish()
            }else{
                toastLong("O produto não possui saldo em estoque no momento, não será possível adicionar no carrinho. ", activity)
            }
        }

        viewHolder.btn_remover_f?.setOnClickListener {
            val a = SugarRecord.findById(Favorito::class.java, viewHolder.idfavorito)
            SugarRecord.delete(a)
            items.removeAt(viewHolder.position!!)
            notifyDataSetChanged()
        }

        return view as View
    }

    override fun notifyDataSetChanged() {
        items = SugarRecord.listAll(Favorito::class.java) as ArrayList<Favorito>

        super.notifyDataSetChanged()
    }

    override fun getItem(position: Int): Favorito {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun getCount(): Int {
        return items.size
    }

    fun getFavorito(): ArrayList<Favorito>{
        return items
    }
}