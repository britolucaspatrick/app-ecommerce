package com.example.patri.refinado.Activity

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.patri.refinado.CamadaNegocio.novoUsuario
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.toastLong
import com.orm.SugarContext
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlin.Exception

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        SugarContext.init(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Cadastro"

        btn_cadastro_c.setOnClickListener {
            var s = ""
            try {
                s = novoUsuario(edt_usuario_c.text.trim().toString(),
                        edt_senha_c.text.trim().toString(),
                        edt_confsenha_c.text.trim().toString(),
                        edt_email_c.text.trim().toString())
                toastLong(s, this)
                if (s.equals("Cadastro efetuado com sucesso.")) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } catch (ex: Exception) {
                toastLong("Erro: $s ${ex.message}", this)
            }
        }

        Log.v("CICLO", "onCreate CadastroActivity")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home
            -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            else -> {
            }
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        Log.v("CICLO", "onStart CadastroActivity")
    }

    override fun onResume() {
        super.onResume()
        Log.v("CICLO", "onResume CadastroActivity")
    }

    override fun onPause() {
        super.onPause()
        Log.v("CICLO", "onPause CadastroActivity")
    }

    override fun onStop() {
        super.onStop()
        Log.v("CICLO", "onStop CadastroActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("CICLO", "onDestroy CadastroActivity")
    }
}
