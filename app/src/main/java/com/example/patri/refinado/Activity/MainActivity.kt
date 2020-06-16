package com.example.patri.refinado.Activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.patri.refinado.Adapter.ProdutoAdapter
import com.example.patri.refinado.Backup.IOHelper
import com.example.patri.refinado.Backup.IOHelper.stringFromStream
import com.example.patri.refinado.CamadaDados.Pais
import com.example.patri.refinado.CamadaNegocio.listarTodosProdutos
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.Constantes.DATANAME
import com.example.patri.refinado.Util.Constantes.DATAPATH
import com.example.patri.refinado.Util.toastLong
import com.google.gson.Gson
import com.orm.SugarDb
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private var diretorio: File? = null
    private val nomeDiretorio: String = "paisJsonObj"
    private var diretorioApp: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        val adapter = ProdutoAdapter(this@MainActivity, listarTodosProdutos(null, "A"))
        gridProdutos.adapter = adapter
        adapter?.notifyDataSetChanged()

        gridProdutos.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, ProdutoDetalhadoActivity::class.java)

            var bundle = Bundle()
            bundle.putLong("produtoselecionado", adapter.getItemId(position))

            intent.putExtras(bundle)
            startActivity(intent)
        }

        Log.v("CICLO", "onCreate MainActivity")

        DATAPATH = SugarDb(this).db.path
        DATANAME = SugarDb(this).databaseName

    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_buscar -> {
                val intent = Intent(this, BuscarActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
            R.id.menu_backup ->{
                serializeClassGSON()
                return true
            }
            R.id.menu_import -> {
                unserializeClassGSON()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun serializeClassGSON() {
        diretorioApp = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/"+nomeDiretorio+"/";
        diretorio = File(diretorioApp);
        diretorio!!.mkdirs();
        val gson = Gson()
        val pais = SugarRecord.first(Pais::class.java)
        if (pais != null){
            val jsonString = gson.toJson(pais)
            IOHelper.writeToFile( "paisJsonObj.txt", jsonString, diretorioApp!!)
            SugarRecord.deleteAll(Pais::class.java)
            toastLong("Backup realizado com sucesso.", this)
        }else{
            toastLong("Nenhum dado de país para realizar backup.", this)
        }
    }

    fun unserializeClassGSON() {
        val gson = Gson()
        try {
            diretorioApp = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/"+nomeDiretorio+"/";
            val fileExt = File(diretorioApp, "paisJsonObj.txt")
            var fosExt: FileInputStream? = null
            fosExt = FileInputStream(fileExt)
            val result = String(fosExt.readBytes())

            val pais = gson.fromJson<Pais>(result, Pais::class.java!!)
            pais.save()
            fosExt.close()
            toastLong("Importado com sucesso.", this)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 2){

            if (grantResults.isNotEmpty() &&
                    PackageManager.PERMISSION_GRANTED == grantResults[0]){
            }else{
            }
            return
        }
    }

    private fun permissionAccessSdRead() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        2)
            }
        }
    }

    private fun permissionAccessSd() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        2)
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_carrinho -> {
                val intent = Intent(this, CarrinhoActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_categoria -> {
                val intent = Intent(this, CategoriaActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_pedido -> {
                val intent = Intent(this, PedidosActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_favoritos -> {
                val intent = Intent(this, FavoritoActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_fidelidade -> {
                val intent = Intent(this, FidelidadeActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_minha_conta -> {
                val intent = Intent(this, MinhaContaActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_configuracao -> {
                val intent = Intent(this, ConfiguracoesActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_sac -> {
                val intent = Intent(this, SacActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_gestao -> {
                val intent = Intent(this, GestaoActivity::class.java)
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onStart() {
        super.onStart()
        Log.v("CICLO", "onStart MainActivity")
    }

    override fun onResume() {
        super.onResume()
        Log.v("CICLO", "onResume MainActivity")
    }

    override fun onPause() {
        super.onPause()
        Log.v("CICLO", "onPause MainActivity")
    }

    override fun onStop() {
        super.onStop()
        Log.v("CICLO", "onStop MainActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("CICLO", "onDestroy MainActivity")
    }
}

