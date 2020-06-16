package com.example.patri.refinado.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.example.patri.refinado.CamadaDados.Usuario
import com.example.patri.refinado.CamadaNegocio.logarUsuario
import com.example.patri.refinado.CamadaNegocio.validarLogin
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.Constantes.ID_USUARIO
import com.example.patri.refinado.Util.toastLong
import com.orm.SugarContext
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        btn_entrar.setOnClickListener{
            val s = logarUsuario(txt_usuario.text.trim().toString(), txt_senha.text.trim().toString())
            if (s.equals("")){
                val a = SugarRecord.find(Usuario::class.java, "usuario = ?", txt_usuario.text.trim().toString())
                ID_USUARIO = a[0].id

                val inte = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(inte)
                finish()
            }else{
                toastLong(s, this)
            }
        }

        btn_cadastrar.setOnClickListener {
            var inte: Intent = Intent(this@LoginActivity, CadastroActivity::class.java)
            startActivity(inte)
            finish()
        }

        txt_esqueceusenha.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.custom_dialog_esqueceusenha,null)
            var email = dialogView.findViewById<TextInputEditText>(R.id.input_email)
            var enviar = dialogView.findViewById<Button>(R.id.btn_enviar)
            var cancelar = dialogView.findViewById<Button>(R.id.btn_cancelar)
            dialog.setView(dialogView)
            dialog.setCancelable(true)
            val customDialog = dialog.create()
            customDialog.show()

            enviar.setOnClickListener {
                //todo implementacao do envio de email
                customDialog.dismiss()
            }

            cancelar.setOnClickListener {
                customDialog.dismiss()
            }
        }

        Log.v("CICLO", "onCreate LoginActivity")

    }



    override fun onStart() {
        super.onStart()
        Log.v("CICLO", "onStart LoginActivity")
    }

    override fun onResume() {
        super.onResume()
        Log.v("CICLO", "onResume LoginActivity")
    }

    override fun onPause() {
        super.onPause()
        Log.v("CICLO", "onPause LoginActivity")
    }

    override fun onStop() {
        super.onStop()
        Log.v("CICLO", "onStop LoginActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("CICLO", "onDestroy LoginActivity")
    }
}

