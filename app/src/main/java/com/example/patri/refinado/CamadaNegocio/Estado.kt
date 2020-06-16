package com.example.patri.refinado.CamadaNegocio

import com.example.patri.refinado.CamadaDados.Estado
import com.example.patri.refinado.CamadaDados.Pais
import com.orm.SugarRecord
import java.sql.Timestamp

fun listarEstados(registro: String): ArrayList<Estado> {
    return if (registro.isEmpty()) SugarRecord.listAll(Estado::class.java) as ArrayList<Estado>
    else SugarRecord.find(Estado::class.java, "registro = ?", registro) as ArrayList<Estado>
}

fun salvarEstado(nome: String, sigla: String, pais:Long??) : String{
    try {
        val validacao = validarEstado(nome, sigla, pais)
        if (!validacao.equals(""))
            throw Exception (validacao)

        val estado: Estado = Estado(nome, sigla, "A", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()), pais)
        estado.save()

        return "Salvo com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun deleteEstado(id: Long?): String{
    try {
        var estado: Estado = SugarRecord.findById(Estado::class.java, id)
        estado.alteracao = Timestamp(System.currentTimeMillis())
        estado.registro = "C"

        SugarRecord.save(estado)

        return "Excluído com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun atualizarEstado(id: Long?, nome: String, sigla: String, pais: Long?): String {
    try {
        val validacao = validarEstado(nome, sigla, pais)
        if (!validacao.equals(""))
            throw Exception (validacao)

        var estado: Estado = SugarRecord.findById(Estado::class.java, id)
        estado.nome = nome
        estado.sigla = sigla
        estado.alteracao = Timestamp(System.currentTimeMillis())

        SugarRecord.update(estado)

        return  "Atualizado com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun validarEstado(nome: String, sigla: String, pais: Long?): String{
    if (nome.equals(null) || sigla.equals(null))
        return "Obrigatório infomar nome e sigla."
    else if (nome.isEmpty() || sigla.isEmpty())
        return "Obrigatório infomar nome e sigla."
    else if (nome.length < 3 || nome.length > 15)
        return "Campo nome deve estar entre 3 e 15 caracteres."
    else if (!sigla.length.equals(2))
        return "Campo sigla deve conter 2 caracteres."
    else if (existEstado(nome, sigla))
        return "Nome/ Sigla já existe cadastrado."

    else if (pais == null)
        return "Obrigatório informar pais para o estado corrente."


    return ""
}

fun existEstado(nome: String, sigla: String): Boolean{
    var param = nome
    var a = SugarRecord.find(Estado::class.java, "nome = ?", "param")
    if(a.size.equals(0))
        return false

    param = sigla
    a = SugarRecord.find(Estado::class.java, "sigla = ?", "param")
    if (a.size.equals(0))
        return false


    return true
}
