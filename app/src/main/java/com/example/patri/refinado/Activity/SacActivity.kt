package com.example.patri.refinado.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.patri.refinado.CamadaDados.Usuario
import com.example.patri.refinado.CamadaNegocio.gerarAtendimento
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.Constantes.ID_USUARIO
import com.example.patri.refinado.Util.toastLong
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.app_bar_sac.*
import kotlinx.android.synthetic.main.content_sac.*

class SacActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sac)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "SAC"


        btn_enviar_sac.setOnClickListener {
            var gerar = true
            if (edt_sac.text.length < 20){
                toastLong("O campo deve ter no minímo 20 caracteres.", this)
                gerar = false
            }

            if (gerar){
                toastLong(gerarAtendimento(edt_sac.text.toString()), this)
                this.finish()
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
