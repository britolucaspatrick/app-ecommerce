package com.example.patri.refinado.Activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.example.patri.refinado.Adapter.EstadoAdapter
import com.example.patri.refinado.CamadaDados.Estado
import com.example.patri.refinado.CamadaNegocio.buscarUsuarioPedido
import com.example.patri.refinado.CamadaNegocio.salvarUsuario
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.Constantes.ID_USUARIO
import com.example.patri.refinado.Util.toastLong
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.app_bar_minha_conta.*
import kotlinx.android.synthetic.main.content_minha_conta.*
import java.util.*

class MinhaContaActivity : AppCompatActivity() {

    private var ID_ESTADO: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minha_conta)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Minha Conta"

        val usuario = buscarUsuarioPedido()
        txt_email_m.setText(usuario!!.email)
        txt_usuario_m.setText(usuario!!.usuario)
        txt_senha_m.setText(usuario!!.senha)
        if (usuario!!.pessoa != null){
            txt_nome_m.setText(usuario!!.pessoaP!!.nome)
            txt_sobrenome_m.setText(usuario!!.pessoaP!!.sobrenome)
            txt_cpf_m.setText(usuario!!.pessoaP!!.cpf)
            txt_logradouro_m.setText(usuario!!.endereco!!.enderedo)
            txt_complem_m.setText(usuario!!.endereco!!.complemento)
            txt_bairro_m.setText(usuario!!.endereco!!.bairro)
            txt_numero_m.setText(usuario!!.endereco!!.numero)
            txt_cep_m.setText(usuario!!.endereco!!.cep)
            txt_cidade_m.setText(usuario!!.endereco!!.cidade)
            txt_telefone_m.setText(usuario!!.endereco!!.telefone)
            txt_aniversario_m.setText(usuario!!.pessoaP!!.aniversario.toString())
        }

        spinnerEstadoM.adapter = null
        var adapterEstado = EstadoAdapter(this, SugarRecord.listAll(Estado::class.java) as ArrayList<Estado>)
        spinnerEstadoM?.adapter = adapterEstado
        adapterEstado?.notifyDataSetChanged()
        spinnerEstadoM?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (adapterEstado!!.count > 0)
                {
                    ID_ESTADO = adapterEstado.getItemId(position)
                }
            }
        }


        val c = Calendar.getInstance()
        val ano = c.get(Calendar.YEAR)
        val mes = c.get(Calendar.MONTH)
        val dia = c.get(Calendar.DAY_OF_MONTH)
        btn_datalancto_d.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val mes = monthOfYear + 1
                txt_aniversario_m.text = "$year/$mes/$dayOfMonth"
            }, ano, mes, dia)
            dpd.show()
        }

        btn_salvar_m.setOnClickListener {
            toastLong(salvarUsuario(ID_USUARIO,
                                    txt_email_m.text.toString(),
                                    txt_usuario_m.text.toString(),
                                    txt_nome_m.text.toString(),
                                    txt_sobrenome_m.text.toString(),
                                    txt_senha_m.text.toString(),
                                    txt_cpf_m.text.toString(),
                                    txt_logradouro_m.text.toString(),
                                    txt_complem_m.text.toString(),
                                    txt_bairro_m.text.toString(),
                                    txt_numero_m.text.toString(),
                                    txt_cep_m.text.toString(),
                                    txt_cidade_m.text.toString(),
                                    ID_ESTADO,
                                    txt_telefone_m.text.toString(),
                                    txt_aniversario_m.text.toString()), this)
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
