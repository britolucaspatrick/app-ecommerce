package com.example.patri.refinado.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CheckBox
import com.example.patri.refinado.CamadaDados.Configuracao
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.Constantes.ID_USUARIO
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_configuracoes.*


class ConfiguracoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracoes)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Configurações"

        var config= SugarRecord.findById(Configuracao::class.java, ID_USUARIO) as Configuracao
        cbx_logarauto_co?.isChecked = config?.logarautomaticamente.equals("S")

        cbx_logarauto_co.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                config.logarautomaticamente = "S"
                config.save()
            }else{
                config.logarautomaticamente = "N"
                config.save()
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

}
