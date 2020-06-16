package com.example.patri.refinado.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import com.example.patri.refinado.Adapter.ProdutoAdapter
import com.example.patri.refinado.CamadaDados.Produto
import com.example.patri.refinado.CamadaNegocio.listarTodosProdutos
import com.example.patri.refinado.R
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_buscar.*


class BuscarActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private var adapter: ProdutoAdapter? = null

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        atribuicao(newText)
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Buscar"

        atribuicao(null)

        gridBuscar.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, ProdutoDetalhadoActivity::class.java)

            var bundle = Bundle()
            bundle.putLong("produtoselecionado", adapter!!.getItemId(position))

            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }

        search_produto_br.setOnQueryTextListener(this)
    }

    private fun atribuicao(text: String?) {
        if (text == null){
            adapter = ProdutoAdapter(this, listarTodosProdutos(null, "A"))
            gridBuscar.adapter = adapter
        }else{
            /*tentado dessa forma, porém n funciona, problema com sugar
            val param = "'%${text.trim()}%'"
            val query = "SELECT * FROM Produto WHERE nome LIKE ?"
            val t = SugarRecord.find(Produto::class.java, query, param)*/
            var t = ArrayList<Produto>()
            val lProdutos = listarTodosProdutos(null, "A")
            lProdutos.forEach {
                var x = it.nome!!.toString().toUpperCase().split(text.trim().toUpperCase())
                if (x.size > 1){
                    t.add(it)
                }
            }
            adapter = ProdutoAdapter(this, t)
            gridBuscar.adapter = adapter
        }

        adapter?.notifyDataSetChanged()
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
