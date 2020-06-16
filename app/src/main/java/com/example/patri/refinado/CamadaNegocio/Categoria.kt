package com.example.patri.refinado.CamadaNegocio

import com.example.patri.refinado.CamadaDados.Categoria
import com.orm.SugarRecord
import java.sql.Timestamp


fun listarCategoria(registro: String): ArrayList<Categoria> {
    return if (registro.isEmpty()) SugarRecord.findAll(Categoria::class.java) as ArrayList<Categoria>
    else SugarRecord.find(Categoria::class.java, "registro = ?", registro) as ArrayList<Categoria>
}

fun salvarCategoria(nome: String, rbSim: Boolean, categoria: Long?) : String{
    try {
        val validacao = validarCategoria(nome, rbSim, categoria)
        if (!validacao.equals(""))
            throw Exception (validacao)

        var i = 0

        var a = categoria
        if (rbSim == true)
        {
            a = null
        }


        val categoria = Categoria(a, rbSim, nome, i, "A", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()))
        categoria.save()

        return "Salvo com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun deleteCategoria(id: Long?): String{
    try {
        var categoria: Categoria = SugarRecord.findById(Categoria::class.java, id)
        categoria.alteracao = Timestamp(System.currentTimeMillis())
        categoria.registro = "C"

        SugarRecord.save(categoria)

        return "Salvo com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun atualizarCategoria(id: Long?, nome: String, rbSim: Boolean, categoria: Categoria?): String {
    try {
        //val validacao = validarEstado(nome, sigla, pais)
        //if (!validacao.equals(""))
            //throw Exception (validacao)

        var categoria: Categoria = SugarRecord.findById(Categoria::class.java, id)
        categoria.nome = nome
        categoria.root = rbSim
        categoria.alteracao = Timestamp(System.currentTimeMillis())

        SugarRecord.save(categoria)

        return  "Atualizado com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun validarCategoria(nome: String, rbSim: Boolean, categoria: Long?): String{
    if (nome == null || nome.isEmpty())
        return "Obrigatório infomar nome"
    else if (nome.length < 3 || nome.length > 15)
        return "Campo nome deve estar entre 3 e 15 caracteres."
    else if (rbSim == null)
        return "Obrigatório infomar root"
    else if (existCategoria(nome))
        return "Nome já existe cadastrado."

    return ""
}

fun existCategoria(nome: String): Boolean{
    var param = nome
    var a = SugarRecord.find(Categoria::class.java, "nome = ?", "param")
    if(a.size.equals(0))
        return false

    return true
}
