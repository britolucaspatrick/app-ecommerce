package com.example.patri.refinado.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import com.example.patri.refinado.CamadaDados.Configuracao
import com.example.patri.refinado.CamadaDados.Usuario
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.Constantes
import com.example.patri.refinado.Util.Constantes.ID_USUARIO
import com.orm.SugarContext
import com.orm.SugarRecord

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SugarContext.init(this)

        supportActionBar?.hide()
        window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        Handler().postDelayed(object:Runnable {
            override fun run() {
                var a = SugarRecord.first(Usuario::class.java)
                var c = SugarRecord.find(Configuracao::class.java, "logarautomaticamente = ?", "S")

                if (c != null && c.size > 0){
                    val login = Intent(this@SplashActivity, MainActivity::class.java)
                    Constantes.ID_USUARIO = c[0].id
                    startActivity(login)
                    finish()
                }
                else if(a == null){
                    val login = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(login)
                    finish()
                }else{
                    val b = SugarRecord.find(Configuracao::class.java, "usuario = ?", a.id.toString())
                    if (b != null && b.size > 0 && b[0].logarautomaticamente.equals("S")){
                        val login = Intent(this@SplashActivity, MainActivity::class.java)
                        Constantes.ID_USUARIO = a.id
                        startActivity(login)
                        finish()
                    }else{
                        val login = Intent(this@SplashActivity, LoginActivity::class.java)
                        startActivity(login)
                        finish()
                    }
                }
            }
        }, 3000)

        Log.v("CICLO", "onCreate SplashActivity")
    }

    override fun onStart() {
        super.onStart()
        Log.v("CICLO", "onStart SplashActivity")
    }

    override fun onResume() {
        super.onResume()
        Log.v("CICLO", "onResume SplashActivity")
    }

    override fun onPause() {
        super.onPause()
        Log.v("CICLO", "onPause SplashActivity")
    }

    override fun onStop() {
        super.onStop()
        Log.v("CICLO", "onStop SplashActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("CICLO", "onDestroy SplashActivity")
    }
}
