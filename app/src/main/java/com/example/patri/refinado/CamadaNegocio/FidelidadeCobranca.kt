package com.example.patri.refinado.CamadaNegocio

import com.example.patri.refinado.CamadaDados.Fidelidade
import com.example.patri.refinado.CamadaDados.FidelidadeCobranca
import com.example.patri.refinado.CamadaDados.RegraCobranca
import java.sql.Timestamp

fun salvarFidelidadeCobranca(cobranca: Long, fidelidade: Long, unidade: String, valor: String): Unit {
    val FidelidadeCobranca = FidelidadeCobranca(cobranca, fidelidade, unidade, valor.toFloat(), Timestamp(System.currentTimeMillis()))
    FidelidadeCobranca.save()
}