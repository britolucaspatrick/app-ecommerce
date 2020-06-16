package com.example.patri.refinado.CamadaNegocio

import com.example.patri.refinado.CamadaDados.Transportadora
import com.orm.SugarRecord
import java.sql.Timestamp

fun listarTransportadoras(registro: String): ArrayList<Transportadora> {
    return if (registro.isEmpty()) SugarRecord.listAll(Transportadora::class.java) as ArrayList<Transportadora>
    else SugarRecord.find(Transportadora::class.java, "registro = ?", registro) as ArrayList<Transportadora>
}

fun salvarTransportadora(nome: String) : String{
    try {
        val validacao = validarTransportadora(nome)
        if (!validacao.equals(""))
            throw Exception (validacao)

        val transportadora = Transportadora(nome, "A", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()))
        transportadora.save()
        return "Salvo com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun deleteTransportadora(id: Long?): String{
    try {
        var transportadora = SugarRecord.findById(Transportadora::class.java, id)
        transportadora.alteracao = Timestamp(System.currentTimeMillis())
        transportadora.registro = "C"

        SugarRecord.save(transportadora)
        return "Salvo com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun atualizarTransportadora(id: Long?, nome: String): String {
    try {
        val validacao = validarTransportadora(nome)
        if (!validacao.equals(""))
            throw Exception (validacao)

        var transportadora = SugarRecord.findById(Transportadora::class.java, id)
        transportadora.nome = nome
        transportadora.alteracao = Timestamp(System.currentTimeMillis())

        SugarRecord.save(transportadora)
        return  "Atualizado com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun validarTransportadora(nome: String?): String {
    if (nome == null || nome.toString().isEmpty())
        return "Obrigatório infomar nome."
    else if (nome.toString().length < 3 || nome.toString().length > 15)
        return "Campo nome deve estar entre 3 e 15 caracteres."
    else if (existTransportadora(nome.toString()))
        return "Nome já existe cadastrado."

    return ""
}

fun existTransportadora(nome: String): Boolean{
    val param = nome
    var a = SugarRecord.find(Transportadora::class.java, "nome = ?", "param")
    if(a.size.equals(0))
        return false

    return true
}
