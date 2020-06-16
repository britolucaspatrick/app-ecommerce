package com.example.patri.refinado.CamadaNegocio

import com.example.patri.refinado.CamadaDados.RegraCobranca
import com.orm.SugarRecord
import java.sql.Timestamp

fun listarRegras(registro: String): ArrayList<RegraCobranca> {
    return if (registro.isEmpty()) SugarRecord.findAll(RegraCobranca::class.java) as ArrayList<RegraCobranca>
    else SugarRecord.find(RegraCobranca::class.java, "registro = ?", registro) as ArrayList<RegraCobranca>
}

fun salvarRegras(unidade: String, valor: String) : String{
    try {
        val validacao = validarRegra(unidade, valor)
        if (!validacao.equals(""))
            throw Exception (validacao)
        val regraCobranca = RegraCobranca(unidade, valor.toFloat(), "A", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()))

        regraCobranca.save()
        return "Salvo com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun deleteRegra(id: Long?): String{
    try {
        var regraCobranca = SugarRecord.findById(RegraCobranca::class.java, id)
        regraCobranca.alteracao = Timestamp(System.currentTimeMillis())
        regraCobranca.registro = "C"

        SugarRecord.save(regraCobranca)
        return "Salvo com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun atualizarRegra(id: Long, unidade: String?, valor: String?): String {
    try {
        val validacao = validarRegra(unidade, valor)
        if (!validacao.equals(""))
            throw Exception (validacao)

        var regraCobranca = SugarRecord.findById(RegraCobranca::class.java, id)
        regraCobranca.unidade = unidade
        regraCobranca.valor = valor!!.toFloat()
        regraCobranca.alteracao = Timestamp(System.currentTimeMillis())

        SugarRecord.save(regraCobranca)
        return  "Atualizado com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun validarRegra(unidade: String?, valor: String?): String {
    if (unidade == null || valor == null)
        return "Obrigatório infomar unidade e valor."
    else if (unidade.toString().isEmpty())
        return "Obrigatório infomar unidade."
    else if (!unidade.toString().length.equals(2))
        return "Campo unidade deve ter 2 caracteres."

    return ""
}
