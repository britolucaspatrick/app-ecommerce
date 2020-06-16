package com.example.patri.refinado.Activity

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ListView
import com.example.patri.refinado.Adapter.TransportadoraAdapter
import com.example.patri.refinado.CamadaNegocio.finalizarPedido
import com.example.patri.refinado.CamadaNegocio.listarTransportadoras
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.Constantes.ID_PEDIDO
import com.example.patri.refinado.Util.toastLong
import kotlinx.android.synthetic.main.activity_forma_entrega.*

class FormaEntregaActivity : AppCompatActivity() {

    private var ID_FRETE: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forma_entrega)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Frete"

        var adapter = TransportadoraAdapter(this, listarTransportadoras("A"))
        lvFormaEntrega?.adapter = adapter
        adapter?.notifyDataSetChanged()

        lvFormaEntrega.setOnItemClickListener { parent, view, position, id ->
            ID_FRETE = adapter.getItem(position).id
        }

        btn_finalizar_pe.setOnClickListener {
            if (finalizarPedido(ID_FRETE, ID_PEDIDO)){
                val intent = Intent(this, PedidoFinalizado::class.java)
                startActivity(intent)
            }else{
                toastLong("Para finalizar o pedido é necessário selecionar uma forma de entrega.", this)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home
            -> {
                //startActivity(Intent(this, PedidoFinalizado::class.java))
                finish()
            }
            else -> {
            }
        }
        return true
    }
}

