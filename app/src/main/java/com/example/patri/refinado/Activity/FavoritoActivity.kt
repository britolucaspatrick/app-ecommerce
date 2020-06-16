package com.example.patri.refinado.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.patri.refinado.Adapter.FavoritoAdapter
import com.example.patri.refinado.CamadaDados.Favorito
import com.example.patri.refinado.R
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.favorito_app_bar.*
import kotlinx.android.synthetic.main.favorito_content.*

class FavoritoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorito)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Favorito"

        val adapter = FavoritoAdapter(this, SugarRecord.listAll(Favorito::class.java) as ArrayList<Favorito>)
        lvFavoritos.adapter = adapter
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
