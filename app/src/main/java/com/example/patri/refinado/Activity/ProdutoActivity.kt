package com.example.patri.refinado.Activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.PermissionRequest
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import com.example.patri.refinado.Adapter.CategoriaAdapter
import com.example.patri.refinado.Adapter.CorAdapter
import com.example.patri.refinado.CamadaDados.Categoria
import com.example.patri.refinado.CamadaDados.Cor
import com.example.patri.refinado.CamadaDados.Produto
import com.example.patri.refinado.CamadaNegocio.listarCategoria
import com.example.patri.refinado.CamadaNegocio.listarCor
import com.example.patri.refinado.CamadaNegocio.listarProdUnicoCompleto
import com.example.patri.refinado.CamadaNegocio.salvarProduto
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.Constantes.ID_PRODUTO
import com.example.patri.refinado.Util.getBitmapImage
import com.example.patri.refinado.Util.toastLong
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_produto.*
import kotlinx.android.synthetic.main.z_custom_dialog_pais.*
import org.apache.commons.io.FileUtils
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.util.jar.Manifest

class ProdutoActivity : AppCompatActivity() {

    private var listCategorias = ArrayList<Categoria>()
    private var listCores = ArrayList<Cor>()
    private var listImagens = ArrayList<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Gestão Produto"


        //Para alteração de produto
        try {
            val intent = intent
            val bundle = intent.extras
            val id_produto = bundle.getLong("id_produto")
            if (id_produto != null)
                atribuicao(id_produto)
        }catch (ex :Exception){}

        //permissão para acessar fotos
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        2)
            }
        }

        btn_listcategoria_pr.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.z_custom_dialog_categoria_pr, null)

            var listCategoria = dialogView.findViewById<ListView>(R.id.lvItensCategoriaPr)

            var adapter = CategoriaAdapter(this, listarCategoria("A"))
            listCategoria?.adapter = adapter
            adapter?.notifyDataSetChanged()

            dialog.setView(dialogView)
            dialog.setCancelable(true)
            val customDialog = dialog.create()
            customDialog.show()

            listCategoria.onItemClickListener = object : AdapterView.OnItemClickListener {
                override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    //se id selecionado não existe na lista private
                    //adiciono e altero a cor
                    val a = listCategorias.find { i: Categoria -> i.id == adapter.getItemId(position) }
                    if (a == null) {
                        listCategorias.add(adapter.getItem(position))
                        view?.setBackgroundColor(Color.GRAY)
                    } else {
                        listCategorias.remove(adapter.getItem(position))
                        view?.setBackgroundColor(Color.WHITE)
                    }
                    //caso contrario altero
                    //volto para cor padrao e retiro da listagem
                }
            }


        }

        btn_cancelarcate_pr.setOnClickListener {
            listCategorias.clear()
            toastLong("Removido", this)
        }

        btn_listcores_pr.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.z_custom_dialog_cor_pr, null)

            var listCor = dialogView.findViewById<ListView>(R.id.lvItensCorPr)

            var adapter = CorAdapter(this, listarCor("A"))
            listCor?.adapter = adapter
            adapter.notifyDataSetChanged()

            dialog.setView(dialogView)
            dialog.setCancelable(true)
            val customDialog = dialog.create()
            customDialog.show()

            listCor.onItemClickListener = object : AdapterView.OnItemClickListener {
                override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    //se id selecionado não existe na lista private
                    //adiciono e altero a cor
                    var a: Cor? = null
                    try {
                        listCores.toList().forEach {
                            if (it.id == adapter.getItemId(position)) {
                                a = it
                            }
                        }
                    }catch (ex: Exception){}

                    if (a == null) {
                        listCores.add(adapter.getItem(position))
                        view?.setBackgroundColor(Color.GRAY)
                    } else {
                        listCores.remove(adapter.getItem(position))
                        view?.setBackgroundColor(Color.WHITE)
                    }
                    //caso contrario altero
                    //volto para cor padrao e retiro da listagem
                }
            }
        }

        btn_cancelarcor_pr.setOnClickListener {
            listCores.clear()
            toastLong("Removido", this)
        }

        btn_listimagens_pr.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        }

        btn_cancelarimagem_pr.setOnClickListener {
            listImagens.clear()
            toastLong("Removido", this)
        }

        btn_salvar_pr.setOnClickListener {
            try {
                toastLong(salvarProduto(ID_PRODUTO,
                        edt_nome_pr.text.toString(),
                        edt_desc_pr.text.toString(),
                        edt_ean_pr.text.toString(),
                        edt_referencia_pr.text.toString(),
                        edt_quantidade_pr.text.toString(),
                        edt_preco_pr.text.toString(),
                        edt_unidade_pr.text.toString(),
                        edt_largura_pr.text.toString(),
                        edt_altura_pr.text.toString(),
                        edt_profundidade_pr.text.toString(),
                        edt_peso_pr.text.toString(),
                        listCategorias,
                        listCores,
                        listImagens), this)
                this.finish()
            } catch (ex: Exception) {
                toastLong("Erro: " + ex.message.toString(), this)
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

    private fun atribuicao(id_produto: Long) {
        ID_PRODUTO = id_produto
        val p = listarProdUnicoCompleto(ID_PRODUTO!!)
        edt_nome_pr.setText(p?.nome)
        edt_desc_pr.setText(p?.descricao)
        edt_ean_pr.setText(p?.ean)
        edt_referencia_pr.setText(p?.referencia)
        edt_quantidade_pr.setText(p?.quantidade.toString())
        edt_preco_pr.setText(p?.preco.toString())
        edt_unidade_pr.setText(p?.unidade)
        edt_largura_pr.setText(p?.largura.toString())
        edt_altura_pr.setText(p?.altura.toString())
        edt_profundidade_pr.setText(p?.profundidade.toString())
        edt_peso_pr.setText(p?.peso.toString())
        //listCategorias = p?.listProdutoCategoria as ArrayList<Categoria>
        //listCores = p?.listProdutoCor as ArrayList<Cor>
        p?.listProdutoImagem?.forEach {
            listImagens.add(getBitmapImage(it.imagem!!))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            var uri = data.data
            var filepath = arrayOf(MediaStore.Images.Media.DATA)
            var cursor = contentResolver.query(uri, filepath, null, null, null)
            cursor.moveToFirst()
            var columnIndex = cursor.getColumnIndex(filepath[0])
            var picturePath = cursor.getString(columnIndex)
            cursor.close()

            try{
                val imagem = BitmapFactory.decodeFile(picturePath)
                listImagens.add(imagem)
            }catch (ex: Exception){
                toastLong("Imagem muito grande", this)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 2) {

            if (grantResults.isNotEmpty() &&
                    PackageManager.PERMISSION_GRANTED == grantResults[0]) {

            } else {

            }
            return
        }
    }

}


