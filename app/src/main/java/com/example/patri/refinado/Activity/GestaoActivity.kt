package com.example.patri.refinado.Activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.*
import android.graphics.ColorSpace
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.ColorLong
import android.support.annotation.ColorRes
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.patri.refinado.Adapter.*
import com.example.patri.refinado.CamadaDados.*
import com.example.patri.refinado.CamadaNegocio.*
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.toastLong
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_gestao.*

class GestaoActivity : AppCompatActivity() {

    var idItemSelecionado: Long? = null
    var pais: Pais? = null
    var categoria: Categoria? = null
    var categoriaParente: Categoria? = null
    var regraCobranca: RegraCobranca? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestao)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeButtonEnabled(true);
        supportActionBar?.title = "Gestão";

        btn_produto_g.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.z_custom_dialog_produto,null)

            var excluir = dialogView.findViewById<Button>(R.id.btn_excluir_g)
            var alterar = dialogView.findViewById<Button>(R.id.btn_alterar_g)

            var listProdutos = dialogView.findViewById<ListView>(R.id.lvProdutosG)
            var adapter = ProdutoAdapter(this, listarTodosProdutos(topImagem = null, registro = "A"))
            listProdutos?.adapter = adapter
            adapter?.notifyDataSetChanged()
            dialog.setView(dialogView)
            dialog.setCancelable(true)
            dialog.setOnItemSelectedListener(listProdutos.onItemSelectedListener)
            val customDialog = dialog.create()
            customDialog.show()

            excluir.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                else{
                    toastLong(deletarProduto(idItemSelecionado), this)
                    customDialog.dismiss()
                }
            }

            alterar.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                else{
                    var intent = Intent(this, ProdutoActivity::class.java)
                    var bundle = Bundle()
                    bundle.putLong("id_produto", idItemSelecionado!!)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }

            listProdutos.setOnItemClickListener { parent, view, position, id ->
                idItemSelecionado = adapter.getItem(position).id
                toastLong("Selecionado item.Id $idItemSelecionado da lista.", this)
            }
        }

        btn_pais.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.z_custom_dialog_pais,null)

            var nome = dialogView.findViewById<EditText>(R.id.edt_nome)
            var prefixo = dialogView.findViewById<EditText>(R.id.edt_prefixo)

            var salvar = dialogView.findViewById<Button>(R.id.btn_salvar)
            var excluir = dialogView.findViewById<Button>(R.id.btn_excluir)
            var alterar = dialogView.findViewById<Button>(R.id.btn_alterar)

            var listPais = dialogView.findViewById<ListView>(R.id.lvItensPais)
            var adapter = PaisAdapter(this, listarPaises("A"))
            listPais?.adapter = adapter
            adapter?.notifyDataSetChanged()

            dialog.setView(dialogView)
            dialog.setCancelable(true)
            dialog.setOnItemSelectedListener(listPais.onItemSelectedListener)
            val customDialog = dialog.create()
            customDialog.show()

            salvar.setOnClickListener{
                try{
                    if(!alterar.isEnabled){
                        toastLong(atualizarPais(idItemSelecionado,
                                                nome.text.toString(),
                                                prefixo.text.toString()),this)
                    }else{
                        toastLong(salvarPais(nome.text.toString(),
                                             prefixo.text.toString()), this)
                    }
                    customDialog.dismiss()

                }catch(ex: Exception){
                    toastLong("Erro: " + ex.message.toString(), this)
                }
            }

            excluir.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                else{
                    toastLong(deletePais(idItemSelecionado), this)
                    customDialog.dismiss()
                }
            }

            alterar.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                else{
                    alterar.isEnabled = false
                    excluir.isEnabled = false
                    toastLong("Informe o novo nome e prefixo", this)
                }
            }

            listPais.setOnItemClickListener { parent, view, position, id ->
                idItemSelecionado = adapter.getItem(position).id
                toastLong("Selecionado item.Id $idItemSelecionado da lista.", this)
            }
        }

        btn_estado.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.z_custom_dialog_estado,null)

            var nome = dialogView.findViewById<EditText>(R.id.edt_nome_e)
            var sigla = dialogView.findViewById<EditText>(R.id.edt_sigla_e)
            var spinner = dialogView.findViewById<Spinner>(R.id.spinnerPais)

            var salvar = dialogView.findViewById<Button>(R.id.btn_salvar_e)
            var excluir = dialogView.findViewById<Button>(R.id.btn_excluir_e)
            var alterar = dialogView.findViewById<Button>(R.id.btn_alterar_e)

            //ListView do estado
            var listEstado = dialogView.findViewById<ListView>(R.id.lvItensEstado)
            var adapter_e = EstadoAdapter(this, listarEstados("A"))
            listEstado?.adapter = adapter_e
            adapter_e?.notifyDataSetChanged()

            //Spinner do pais e atribuição
            var adapterPais = PaisAdapter(this, listarPaises("A"))
            spinner?.adapter = adapterPais
            adapterPais?.notifyDataSetChanged()

            dialog.setView(dialogView)
            dialog.setCancelable(true)
            val customDialog = dialog.create()
            customDialog.show()

            spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    if (adapterPais!!.count > 0)
                        pais = SugarRecord.findById(Pais::class.java, adapterPais.getItemId(position))
                }
            }

            salvar.setOnClickListener{
                try{
                    if(!alterar.isEnabled){
                        toastLong(atualizarEstado(idItemSelecionado, nome.text.toString(), sigla.text.toString(), pais?.id),this)
                    }else{
                        toastLong(salvarEstado(nome.text.toString(), sigla.text.toString(), pais?.id), this)
                    }
                    customDialog.dismiss()

                }catch(ex: Exception){
                    toastLong("Erro: " + ex.message.toString(), this)
                }
            }

            excluir.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                else{
                    toastLong(deleteEstado(idItemSelecionado), this)
                    customDialog.dismiss()
                }
            }

            alterar.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                else{
                    alterar.isEnabled = false
                    excluir.isEnabled = false
                    toastLong("Informe o novo nome, sigla e país", this)
                }
            }

            listEstado.setOnItemClickListener { parent, view, position, id ->
                idItemSelecionado = adapter_e.getItem(position).id
                toastLong("Selecionado item.Id $idItemSelecionado da lista.", this)
            }
        }

        btn_categoria.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.z_custom_dialog_categoria,null)

            var nome = dialogView.findViewById<EditText>(R.id.txt_nome_cat)
            var rbSim = dialogView.findViewById<RadioButton>(R.id.rbSim)
            var listCategoria = dialogView.findViewById<ListView>(R.id.lvItensCategoria)
            var spinnerCategoria = dialogView.findViewById<Spinner>(R.id.spinnerCategoria)

            var salvar = dialogView.findViewById<Button>(R.id.btn_salvar_c)
            var excluir = dialogView.findViewById<Button>(R.id.btn_excluir_c)
            var alterar = dialogView.findViewById<Button>(R.id.btn_alterar_c)

            //Listagem de categorias cadastradas
            var adapter = CategoriaAdapter(this, listarCategoria("A"))
            listCategoria?.adapter = adapter
            adapter?.notifyDataSetChanged()

            //Spinner do categoria parente e atribuição
            var adapterParentCategoria = CategoriaAdapter(this, listarCategoria("A"))
            spinnerCategoria ?.adapter = adapterParentCategoria
            adapterParentCategoria?.notifyDataSetChanged()

            dialog.setView(dialogView)
            dialog.setCancelable(true)
            val customDialog = dialog.create()
            customDialog.show()

            spinnerCategoria?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    if (adapterParentCategoria!!.count > 0)
                        categoriaParente = SugarRecord.findById(Categoria::class.java, adapterParentCategoria.getItemId(position))
                }
            }

            salvar.setOnClickListener{
                try{
                    if(!alterar.isEnabled){
                        toastLong(atualizarCategoria(idItemSelecionado,
                                                     nome.text.toString(),
                                                     rbSim.isChecked,
                                                     categoriaParente),this)
                    }else{
                        toastLong(salvarCategoria(nome.text.toString(),
                                                  rbSim.isChecked,
                                                  categoriaParente?.id),this)
                    }
                    customDialog.dismiss()

                }catch(ex: Exception){
                    toastLong("Erro: " + ex.message.toString(), this)
                }
            }

            excluir.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                deleteCategoria(idItemSelecionado)
                toastLong("Excluído com sucesso", this)
                customDialog.dismiss()
            }

            alterar.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                else{
                    alterar.isEnabled = false
                    excluir.isEnabled = false
                    toastLong("Informe os dados para alteracao", this)
                }
            }

            listCategoria.setOnItemClickListener { parent, view, position, id ->
                idItemSelecionado = adapter.getItem(position).id
                toastLong("Selecionado item.Id $idItemSelecionado da lista.", this)
            }
        }

        btn_cor.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.z_custom_dialog_cor,null)

            var red = dialogView.findViewById<SeekBar>(R.id.seekRed)
            var blue = dialogView.findViewById<SeekBar>(R.id.seekBlue)
            var green = dialogView.findViewById<SeekBar>(R.id.seekGreen)
            var numred = dialogView.findViewById<TextView>(R.id.numero_red)
            var numblue = dialogView.findViewById<TextView>(R.id.numero_blue)
            var numgreen = dialogView.findViewById<TextView>(R.id.numero_green)
            var imagemselecionada = dialogView.findViewById<ImageView>(R.id.imagemselecionada)

            var nome = dialogView.findViewById<TextView>(R.id.txt_nome_cor)
            var hexa = dialogView.findViewById<TextView>(R.id.txt_hexadecimal)

            var salvar = dialogView.findViewById<Button>(R.id.btn_salvar_cor)
            var excluir = dialogView.findViewById<Button>(R.id.btn_excluir_cor)
            var alterar = dialogView.findViewById<Button>(R.id.btn_alterar_cor)

            var listCores = dialogView.findViewById<ListView>(R.id.lvItensCor)
            var adapter = CorAdapter(this, listarCor("A"))
            listCores?.adapter = adapter
            adapter?.notifyDataSetChanged()

            dialog.setView(dialogView)
            dialog.setCancelable(true)
            val customDialog = dialog.create()
            customDialog.show()

            red.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    hexa.text = calculaHexadecimal(progress, blue.progress, green.progress)
                    numred.text = progress.toString()
                    imagemselecionada.setBackgroundColor(Color.parseColor(hexa.text.toString()))
                }
            })
            blue.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    hexa.text = calculaHexadecimal(red.progress, progress, green.progress);
                    numblue.text = progress.toString()
                    imagemselecionada.setBackgroundColor(Color.parseColor(hexa.text.toString()))
                }
            })
            green.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    hexa.text = calculaHexadecimal(red.progress, blue.progress, progress)
                    numgreen.text = progress.toString()
                    imagemselecionada.setBackgroundColor(Color.parseColor(hexa.text.toString()))
                }
            })

            salvar.setOnClickListener{
                try{
                    if(!alterar.isEnabled){
                        toastLong(atualizarCor(idItemSelecionado, nome.text.toString(), hexa.text.toString()),this)
                    }else{
                        toastLong(salvarCor(nome.text.toString(), hexa.text.toString()), this)
                    }
                    customDialog.dismiss()

                }catch(ex: Exception){
                    toastLong("Erro: " + ex.message.toString(), this)
                }
            }

            excluir.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                else{
                    toastLong(deleteCor(idItemSelecionado), this)
                    customDialog.dismiss()
                }
            }

            alterar.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                else{
                    alterar.isEnabled = false
                    excluir.isEnabled = false
                    toastLong("Informe o novo nome para a cor", this)
                }
            }

            listCores.setOnItemClickListener { parent, view, position, id ->
                idItemSelecionado = adapter.getItem(position).id
                toastLong("Selecionado item.Id $idItemSelecionado da lista.", this)
            }
        }

        btn_regracobranca.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.z_custom_dialog_regracobranca,null)

            var unidade = dialogView.findViewById<EditText>(R.id.edt_unidade_r)
            var valor = dialogView.findViewById<EditText>(R.id.edt_valor_r)

            var salvar = dialogView.findViewById<Button>(R.id.btn_salvar_r)
            var excluir = dialogView.findViewById<Button>(R.id.btn_excluir_r)
            var alterar = dialogView.findViewById<Button>(R.id.btn_alterar_r)

            var listRegras = dialogView.findViewById<ListView>(R.id.lvItensRegraCobranca)
            var adapter = RegrasCobrancaAdapter(this, listarRegras("A"))
            listRegras?.adapter = adapter
            adapter?.notifyDataSetChanged()

            dialog.setView(dialogView)
            dialog.setCancelable(true)
            val customDialog = dialog.create()
            customDialog.show()

            salvar.setOnClickListener{
                try{
                    if(!alterar.isEnabled){
                        toastLong(atualizarRegra(idItemSelecionado!!, unidade.text.toString(), valor.text.toString()),this)
                    }else{
                        toastLong(salvarRegras(unidade.text.toString(), valor.text.toString()), this)
                    }
                    customDialog.dismiss()

                }catch(ex: Exception){
                    toastLong("Erro: " + ex.message.toString(), this)
                }
            }

            excluir.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                deleteRegra(idItemSelecionado)
                toastLong("Excluído com sucesso", this)
                customDialog.dismiss()
            }

            alterar.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                else{
                    alterar.isEnabled = false
                    excluir.isEnabled = false
                    toastLong("Informe o novo nome e prefixo", this)
                }
            }

            listRegras.setOnItemClickListener { parent, view, position, id ->
                idItemSelecionado = adapter.getItem(position).id
                toastLong("Selecionado item.Id $idItemSelecionado da lista.", this)
            }

        }

        btn_tppagamento.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.z_custom_dialog_tppagamento,null)

            var nome = dialogView.findViewById<EditText>(R.id.edt_tipopagaqmento_tp)

            var salvar = dialogView.findViewById<Button>(R.id.btn_salvar_tp)
            var excluir = dialogView.findViewById<Button>(R.id.btn_excluir_tp)
            var alterar = dialogView.findViewById<Button>(R.id.btn_alterar_tp)


            var listTpPagamento = dialogView.findViewById<ListView>(R.id.lvItensTipoPagamento)
            var adapter = TpPagamentoAdapter(this, SugarRecord.listAll(TipoPagamento::class.java) as ArrayList<TipoPagamento>)
            listTpPagamento?.adapter = adapter
            adapter?.notifyDataSetChanged()

            dialog.setView(dialogView)
            dialog.setCancelable(true)
            dialog.setOnItemSelectedListener(listTpPagamento.onItemSelectedListener)
            val customDialog = dialog.create()
            customDialog.show()

            salvar.setOnClickListener{
                try{
                    if(!alterar.isEnabled){
                        toastLong(atualizarTpPagamento(idItemSelecionado!!, nome.text.toString()),this)
                    }else{
                        toastLong(salvarTpPagamento(nome.text.toString()), this)
                    }
                    customDialog.dismiss()

                }catch(ex: Exception){
                    toastLong("Erro: " + ex.message.toString(), this)
                }
            }

            excluir.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                deletarTpPagamento(idItemSelecionado)
                toastLong("Excluído com sucesso", this)
                customDialog.dismiss()
            }

            alterar.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                else{
                    alterar.isEnabled = false
                    excluir.isEnabled = false
                    toastLong("Informe a nova forma", this)
                }
            }

            listTpPagamento.setOnItemClickListener { parent, view, position, id ->
                idItemSelecionado = adapter.getItem(position).id
                toastLong("Selecionado item.Id $idItemSelecionado da lista.", this)
            }
        }

        btn_fidelidade.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.z_custom_dialog_fidelidade,null)

            var edt_pontos_f = dialogView.findViewById<EditText>(R.id.edt_pontos_f)
            var spinnerRegraCobranca = dialogView.findViewById<Spinner>(R.id.spinnerRegraCobranca)

            var salvar = dialogView.findViewById<Button>(R.id.btn_salvar_f)
            var excluir = dialogView.findViewById<Button>(R.id.btn_excluir_f)
            var alterar = dialogView.findViewById<Button>(R.id.btn_alterar_f)

            var listFidelidade = dialogView.findViewById<ListView>(R.id.lvItensFidelidade)
            var adapter = FidelidadeAdapter(this, listarFidelidade("A"))
            listFidelidade?.adapter = adapter
            adapter?.notifyDataSetChanged()

            //Spinner do regra cobranca e atribuição
            var adapterRegra = RegrasCobrancaAdapter(this, listarRegras("A"))
            spinnerRegraCobranca ?.adapter = adapterRegra
            adapterRegra?.notifyDataSetChanged()

            dialog.setView(dialogView)
            dialog.setCancelable(true)
            dialog.setOnItemSelectedListener(listFidelidade.onItemSelectedListener)
            val customDialog = dialog.create()
            customDialog.show()

            spinnerRegraCobranca?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    if (adapterRegra!!.count > 0)
                        regraCobranca = SugarRecord.findById(RegraCobranca::class.java, adapterRegra.getItemId(position))
                }
            }

            salvar.setOnClickListener{
                try{
                    if(!alterar.isEnabled){
                        toastLong(atualizarFidelidade(idItemSelecionado, regraCobranca!!.id, edt_pontos_f.text.toString().toInt()),this)
                    }else{
                        toastLong(salvarFidelidade(regraCobranca!!.id, edt_pontos_f.text.toString().toInt()), this)
                    }
                    customDialog.dismiss()

                }catch(ex: Exception){
                    toastLong("Erro: " + ex.message.toString(), this)
                }
            }

            excluir.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                deleteFidelidade(idItemSelecionado)
                toastLong("Excluído com sucesso", this)
                customDialog.dismiss()
            }

            alterar.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                else{
                    alterar.isEnabled = false
                    excluir.isEnabled = false
                    toastLong("Informe o novo nome e prefixo", this)
                }
            }

            listFidelidade.setOnItemClickListener { parent, view, position, id ->
                idItemSelecionado = adapter.getItem(position).id
                toastLong("Selecionado item.Id $idItemSelecionado da lista.", this)
            }
        }

        btn_transportadora.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.z_custom_dialog_transportadora,null)

            var nome = dialogView.findViewById<EditText>(R.id.edt_nome_tr)

            var salvar = dialogView.findViewById<Button>(R.id.btn_salvar_tr)
            var excluir = dialogView.findViewById<Button>(R.id.btn_excluir_tr)
            var alterar = dialogView.findViewById<Button>(R.id.btn_alterar_tr)

            var listTransportadora = dialogView.findViewById<ListView>(R.id.lvItensTransportadora)
            var adapter = TransportadoraAdapter(this, listarTransportadoras("A"))
            listTransportadora?.adapter = adapter
            adapter?.notifyDataSetChanged()

            dialog.setView(dialogView)
            dialog.setCancelable(true)
            val customDialog = dialog.create()
            customDialog.show()

            salvar.setOnClickListener{
                try{
                    if(!alterar.isEnabled){
                        toastLong(atualizarTransportadora(idItemSelecionado, nome.text.toString()),this)
                    }else{
                        toastLong(salvarTransportadora(nome.text.toString()), this)
                    }
                    customDialog.dismiss()

                }catch(ex: Exception){
                    toastLong("Erro: " + ex.message.toString(), this)
                }
            }

            excluir.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                deleteTransportadora(idItemSelecionado)
                toastLong("Excluído com sucesso", this)
                customDialog.dismiss()
            }

            alterar.setOnClickListener{
                if (idItemSelecionado == null)
                    toastLong("Selecione algum item na lista", this)
                else{
                    alterar.isEnabled = false
                    excluir.isEnabled = false
                    toastLong("Informe o novo nome", this)
                }
            }

            listTransportadora.setOnItemClickListener { parent, view, position, id ->
                idItemSelecionado = adapter.getItem(position).id
                toastLong("Selecionado item.Id $idItemSelecionado da lista.", this)
            }
        }

        btn_produto.setOnClickListener {
            var intent = Intent(this, ProdutoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun calculaHexadecimal(red: Int, blue: Int, green: Int): String? {
        return  "#" + Integer.toHexString(rgb(red, green, blue)).toUpperCase()
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



