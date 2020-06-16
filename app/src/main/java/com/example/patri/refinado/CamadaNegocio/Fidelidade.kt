package com.example.patri.refinado.CamadaNegocio

import com.example.patri.refinado.CamadaDados.Fidelidade
import com.example.patri.refinado.CamadaDados.RegraCobranca
import com.orm.SugarRecord
import java.sql.Timestamp

fun listarFidelidade(registro: String): ArrayList<Fidelidade> {
    return if (registro.isEmpty()) SugarRecord.findAll(Fidelidade::class.java) as ArrayList<Fidelidade>
    else SugarRecord.find(Fidelidade::class.java, "registro = ?", registro) as ArrayList<Fidelidade>
}

fun salvarFidelidade(regraCobranca: Long?, pontos: Int?) : String{
    try {
        val validacao = validarFidelidade(regraCobranca, pontos)
        if (!validacao.equals(""))
            throw Exception (validacao)

        val fidelidade = Fidelidade(regraCobranca, pontos, "A", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()))
        fidelidade.save()
        return "Salvo com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun deleteFidelidade(id: Long?): String{
    try {
        var fidelidade = SugarRecord.findById(Fidelidade::class.java, id)
        fidelidade.alteracao = Timestamp(System.currentTimeMillis())
        fidelidade.registro = "C"

        SugarRecord.save(fidelidade)
        return "Salvo com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun atualizarFidelidade(id: Long?, regraCobranca: Long?, pontos: Int?): String {
    try {
        val validacao = validarFidelidade(regraCobranca, pontos)
        if (!validacao.equals(""))
            throw Exception (validacao)

        var fidelidade = SugarRecord.findById(Fidelidade::class.java, id)
        fidelidade.regraCobranca = regraCobranca
        fidelidade.pontos = pontos
        fidelidade.alteracao = Timestamp(System.currentTimeMillis())

        SugarRecord.save(fidelidade)

        return  "Atualizado com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun validarFidelidade(regraCobranca: Long?, pontos: Int?): String {
    if (regraCobranca == null || pontos == null)
        return "Obrigatório infomar regra Cobrança e pontos."

    return ""
}
