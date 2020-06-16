package com.example.patri.refinado.CamadaNegocio

import com.example.patri.refinado.CamadaDados.Pais
import com.orm.SugarRecord
import java.sql.Timestamp

fun listarPaises(registro: String): ArrayList<Pais> {
    return if (registro.isEmpty()) SugarRecord.findAll(Pais::class.java) as ArrayList<Pais>
    else SugarRecord.find(Pais::class.java, "registro = ?", registro) as ArrayList<Pais>
}

fun salvarPais(nome: String, prefixo: String) : String{
    try {
        val validacao = validarPais(nome, prefixo)
        if (!validacao.equals(""))
            throw Exception (validacao)

        val pais = Pais(prefixo.toInt(), nome, "A", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()))
        pais.save()
        return "Salvo com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun deletePais(id: Long?): String{
    try {
        var pais: Pais = SugarRecord.findById(Pais::class.java, id)
        pais.alteracao = Timestamp(System.currentTimeMillis())
        pais.registro = "C"

        SugarRecord.save(pais)
        return "Excluído com sucesso!"

    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun atualizarPais(id: Long?, nome: String, prefixo: String): String {
    try {
        val validacao = validarPais(nome, prefixo)
        if (!validacao.equals(""))
            throw Exception (validacao)

        var pais: Pais = SugarRecord.findById(Pais::class.java, id)
        pais.nome = nome
        pais.prefixo = prefixo.toInt()
        pais.alteracao = Timestamp(System.currentTimeMillis())

        SugarRecord.update(pais)

        return  "Atualizado com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun validarPais(nome: String?, prefixo: String?): String {
    if (nome == null || prefixo == null)
        return "Obrigatório infomar nome e prefixo."

    else if (nome.toString().isEmpty() || prefixo.toString().isEmpty())
        return "Obrigatório infomar nome e prefixo."

    else if (nome.toString().length < 3 || nome.toString().length > 15)
        return "Campo nome deve estar entre 3 e 15 caracteres."

    else if (!prefixo.toString().length.equals(2))
        return "Campo prefixo deve ter 2 caracteres."

    else if (existPais(nome.toString()))
        return "Nome já existe cadastrado."

    else if (existPrefixo(prefixo.toString()))
        return "Prefixo já existe cadastrado."

    return ""
}

fun existPais(nome: String): Boolean{
    val param = nome
    var a = SugarRecord.find(Pais::class.java, "nome = ?", "param")
    if(a.size.equals(0))
        return false

    return true
}

fun existPrefixo(nome: String): Boolean{
    val param = nome
    val a = SugarRecord.find(Pais::class.java, "prefixo = ?", "param")
    if(a.size.equals(0))
        return false

    return true
}