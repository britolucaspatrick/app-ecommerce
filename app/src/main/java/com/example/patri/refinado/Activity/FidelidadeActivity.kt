package com.example.patri.refinado.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.patri.refinado.CamadaDados.Fidelidade
import com.example.patri.refinado.CamadaDados.Pedido
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.Constantes.ID_USUARIO
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.app_bar_fidelidade.*
import kotlinx.android.synthetic.main.content_fidelidade.*

class FidelidadeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fidelidade)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Fidelidade"

        val tot = SugarRecord.find(Pedido::class.java, "usuario = ?", ID_USUARIO.toString())
        if (tot!!.size.equals(0)){
            txt_fidelidade.setText("Ops, você ainda não comprou nenhum produto para ganhar pontos de fidelidade. Com pontos de fidelidade é possível ganhar desconto na próxima compra.")
        }else{
            var pontos = 0
            tot.forEach {
                val f = SugarRecord.findById(Fidelidade::class.java, it.id)
                if (f != null)
                    pontos += f.pontos!!
            }
            txt_fidelidade.setText("Você possui $pontos pontos! Com pontos de fidelidade é possível ganhar desconto na próxima compra.")
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
