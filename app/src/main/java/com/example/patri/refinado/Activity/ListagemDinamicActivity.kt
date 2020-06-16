package com.example.patri.refinado.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.patri.refinado.Adapter.ProdutoAdapter
import com.example.patri.refinado.CamadaNegocio.listarProdutosPorCategoria
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.toastLong
import kotlinx.android.synthetic.main.activity_listagem_dinamic.*

class ListagemDinamicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_dinamic)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Listagem por Categoria"

        val intent = intent
        val bundle = intent.extras
        val id = bundle.getLong("idcategoriadin")

        val adapter = ProdutoAdapter(this, listarProdutosPorCategoria(id))
        gridProdutosC.adapter = adapter
        adapter?.notifyDataSetChanged()

        if (adapter.count.equals(0))
            toastLong("Não existe produtos para serem listados.", this)

        gridProdutosC.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, ProdutoDetalhadoActivity::class.java)

            var bundle = Bundle()
            bundle.putLong("produtoselecionado", adapter.getItemId(position))

            intent.putExtras(bundle)
            startActivity(intent)
            finish()
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
}
