package com.example.patri.refinado.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.patri.refinado.Adapter.CarrinhoAdapter
import com.example.patri.refinado.CamadaDados.Carrinho
import com.example.patri.refinado.CamadaNegocio.gerarPedidoPendente
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.Constantes.ID_PEDIDO
import com.example.patri.refinado.Util.toastLong
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.app_bar_carrinho.*
import kotlinx.android.synthetic.main.content_carrinho.*
import kotlinx.android.synthetic.main.item_carrinho.*

class CarrinhoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrinho)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Carrinho"

        val adapter = CarrinhoAdapter(this, SugarRecord.listAll(Carrinho::class.java) as ArrayList<Carrinho>)
        lvCarrinho.adapter = adapter
        adapter?.notifyDataSetChanged()

        btn_finalizar_ca.setOnClickListener {
            if (adapter.count > 0){
                val s = gerarPedidoPendente(adapter.getCarrinho())
                if (s != null && s!!.equals("")){
                    val intent = Intent(this, ConfirmacaoDadosActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    toastLong(s!!, this)
                }
            }else{
                toastLong("Não é possível finalizar o carrinho sem itens.", this)
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

    override fun onStart() {
        super.onStart()
        Log.v("CICLO", "onStart CarrinhoActivity")
    }

    override fun onResume() {
        super.onResume()
        Log.v("CICLO", "onResume CarrinhoActivity")
    }

    override fun onPause() {
        super.onPause()
        Log.v("CICLO", "onPause CarrinhoActivity")
    }

    override fun onStop() {
        super.onStop()
        Log.v("CICLO", "onStop CarrinhoActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("CICLO", "onDestroy CarrinhoActivity")
    }
}
