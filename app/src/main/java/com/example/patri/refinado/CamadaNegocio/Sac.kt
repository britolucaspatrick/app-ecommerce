package com.example.patri.refinado.CamadaNegocio

import com.example.patri.refinado.CamadaDados.Atendimento
import com.example.patri.refinado.Util.Constantes.ID_USUARIO
import java.sql.Timestamp

fun gerarAtendimento(text: String): String {
    val atendimento = Atendimento(ID_USUARIO, text, "A", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()))
    atendimento.save()

    return "Atendimento fui enviado para a equipe de suporte."
}