package com.example.patri.refinado.Activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.example.patri.refinado.Adapter.EstadoAdapter
import com.example.patri.refinado.Adapter.TpPagamentoAdapter
import com.example.patri.refinado.CamadaDados.Estado
import com.example.patri.refinado.CamadaDados.TipoPagamento
import com.example.patri.refinado.CamadaNegocio.buscarUsuarioPedido
import com.example.patri.refinado.CamadaNegocio.confirmacaoDados
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.Constantes.ID_PEDIDO
import com.example.patri.refinado.Util.toastLong
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.app_bar_confirmacao_dados.*
import kotlinx.android.synthetic.main.content_confirmacao_dados.*
import kotlinx.android.synthetic.main.content_minha_conta.*
import java.util.*

class ConfirmacaoDadosActivity : AppCompatActivity() {

    private var ID_FORMA: Long? = null
    private var ID_ESTADO: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmacao_dados)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Confirmação Dados"

        spinnerEstado.adapter = null
        var adapterEstado = EstadoAdapter(this, SugarRecord.listAll(Estado::class.java) as ArrayList<Estado>)
        spinnerEstado?.adapter = adapterEstado
        adapterEstado?.notifyDataSetChanged()
        spinnerEstado?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        btn_datalancto_cf.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val mes = monthOfYear + 1
                edt_date_cf.text = "$year/$mes/$dayOfMonth"
            }, ano, mes, dia)
            dpd.show()
        }

        val usuario = buscarUsuarioPedido()
        edt_logradouro_cf?.setText(usuario?.endereco?.enderedo)
        edt_complemento_cf?.setText(usuario?.endereco?.complemento)
        edt_bairro_cf?.setText(usuario?.endereco?.bairro)
        edt_numero_cf?.setText(usuario?.endereco?.numero)
        edt_cep_cf?.setText(usuario?.endereco?.cep)
        edt_cidade_cf?.setText(usuario?.endereco?.cidade)
        edt_nome_cf?.setText(usuario?.pessoaP?.nome)
        edt_sobrenome_cf?.setText(usuario?.pessoaP?.sobrenome)
        edt_cpf_cf?.setText(usuario?.pessoaP?.cpf)
        edt_date_cf?.setText(usuario?.pessoaP?.aniversario.toString())

        spinnerFormasPagamento.adapter = null
        var adapterFormas = TpPagamentoAdapter(this, SugarRecord.listAll(TipoPagamento::class.java) as ArrayList<TipoPagamento>)
        spinnerFormasPagamento?.adapter = adapterFormas
        adapterFormas?.notifyDataSetChanged()
        spinnerFormasPagamento?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (adapterFormas!!.count > 0)
                {
                    ID_FORMA = adapterFormas.getItemId(position)
                }
            }
        }

        btn_finalizar_cf.setOnClickListener {
            val a = confirmacaoDados(usuario,
                                            edt_logradouro_cf.text.toString(),
                                            edt_complemento_cf.text.toString(),
                                            edt_bairro_cf.text.toString(),
                                            edt_numero_cf.text.toString(),
                                            edt_cep_cf.text.toString(),
                                            edt_cidade_cf.text.toString(),
                                            ID_ESTADO,
                                            ID_FORMA,
                                            ID_PEDIDO,
                                            edt_nome_cf.text.toString(),
                                            edt_sobrenome_cf.text.toString(),
                                            edt_date_cf.text.toString(),
                                            edt_cpf_cf.text.toString()
            )
            if (a.equals("")){
                val intent = Intent(this, FormaEntregaActivity::class.java)
                startActivity(intent)
            }else{
                toastLong(a, this)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home
            -> {
                startActivity(Intent(this, PedidosActivity::class.java))
                finish()
            }
            else -> {
            }
        }
        return true
    }
}
