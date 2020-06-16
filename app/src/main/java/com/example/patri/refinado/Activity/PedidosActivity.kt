package com.example.patri.refinado.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import com.example.patri.refinado.Adapter.PedidosAdapter
import com.example.patri.refinado.Adapter.PedidosFinaAdapter
import com.example.patri.refinado.CamadaDados.Pedido
import com.example.patri.refinado.CamadaNegocio.countPedidosPorStatus
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.Constantes.GERAR_CONFIRMACAO
import com.example.patri.refinado.Util.Constantes.ID_PEDIDO
import com.example.patri.refinado.Util.toastLong
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.content_pedidos.*

class PedidosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Pedidos"

        btn_pedido_abertos.setOnClickListener {
            val i = countPedidosPorStatus("P")
            if (i > 0){
                val adaptera = PedidosAdapter(this, SugarRecord.find(Pedido::class.java, "registro = ?", "P") as ArrayList<Pedido>)
                lvPedidos.adapter = adaptera
                adaptera?.notifyDataSetChanged()
            }else{
                toastLong("Nenhum pedido encontrado para o status informado.", this)
            }
        }

        btn_pedido_finalizados.setOnClickListener {
            val i = countPedidosPorStatus("F")
            if (i > 0){
                val adapter = PedidosFinaAdapter(this, SugarRecord.find(Pedido::class.java, "registro = ?", "F") as ArrayList<Pedido>)
                lvPedidos.adapter = adapter
                adapter?.notifyDataSetChanged()
            }else{
                toastLong("Nenhum pedido encontrado para o status informado.", this)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home
            -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else -> {
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()

        if (ID_PEDIDO != null && GERAR_CONFIRMACAO){
            val intent = Intent(this, ConfirmacaoDadosActivity::class.java)
            startActivity(intent)
            GERAR_CONFIRMACAO = false
        }
    }
}
