package com.example.patri.refinado.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.patri.refinado.Adapter.ExpandableCategoriaAdapter
import com.example.patri.refinado.CamadaDados.Categoria
import com.example.patri.refinado.CamadaNegocio.listarCategoria
import com.example.patri.refinado.R
import com.orm.SugarRecord
import com.orm.query.Condition
import com.orm.query.Select
import kotlinx.android.synthetic.main.app_bar_categoria.*
import kotlinx.android.synthetic.main.content_categoria.*

class CategoriaActivity : AppCompatActivity() {

    var header : ArrayList<Categoria> = ArrayList()
    var body : ArrayList<ArrayList<Categoria>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categoria)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Categoria"

        val param = arrayOf("A", "true")

        //listar todas categorias principais
        val x= SugarRecord.listAll(Categoria::class.java)
        var categoria = ArrayList<Categoria>()
        x.forEach {
            if (it.root!! && it.registro.equals("A"))
                categoria.add(it)
        }

        //para cada categoria principal busco as parentes
        categoria.forEach {
            header.add(it)
            val param = it.id.toString()
            val parent =
                    SugarRecord.find(Categoria::class.java, "categoria = ?", param) as ArrayList<Categoria>
            body.add(parent)
        }


        val adapter = ExpandableCategoriaAdapter(this, expand_categoria, header, body)
        expand_categoria.setAdapter(adapter)

        expand_categoria.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            var id = adapter.getChildId(groupPosition, childPosition)
            var intent = Intent(this, ListagemDinamicActivity::class.java)
            var bundle = Bundle()
            bundle.putLong("idcategoriadin", adapter.getGroupId(groupPosition))
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
            true}
        expand_categoria.setOnGroupClickListener { parent, v, groupPosition, id ->
            var id = adapter.getGroupId(groupPosition)
            var intent = Intent(this, ListagemDinamicActivity::class.java)
            var bundle = Bundle()
            bundle.putLong("idcategoriadin", adapter.getGroupId(groupPosition))
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
            true
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
