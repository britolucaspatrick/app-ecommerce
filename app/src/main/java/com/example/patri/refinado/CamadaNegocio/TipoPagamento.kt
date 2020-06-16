package com.example.patri.refinado.CamadaNegocio

import com.example.patri.refinado.CamadaDados.Pedido
import com.example.patri.refinado.CamadaDados.TipoPagamento
import com.orm.SugarRecord

fun salvarTpPagamento(nome: String?): String {
    if (nome == null || nome.isEmpty())
        return "Obrigatório informar forma."

    val tipoPagamento = TipoPagamento(nome)
    tipoPagamento.save()

    return "Salvo com sucesso!"
}

fun deletarTpPagamento(id: Long?): String {
    //validar utilizacao do registro pela tabela pedido
    val pedido = SugarRecord.findById(Pedido::class.java, id)
    if (pedido != null)
        return "Não será possível excluir o registro pois está sendo utilizado pelo pedido ${pedido.id}"

    var tp = SugarRecord.findById(TipoPagamento::class.java, id)
    tp.delete()

    return "Excluído com sucesso"
}

fun atualizarTpPagamento(id: Long?, text: String?): String {
    if (id == null)
        return "Obrigatório selecionar item."
    else if (text.isNullOrEmpty())
        return "Obrigatório informar text."
    else if (existTpPagamento(text))
        return "Tipo de pagamento informado já existe cadastrado."

    var tp = SugarRecord.findById(TipoPagamento::class.java, id)
    tp.forma = text
    tp.save()
    return "Alterado com sucesso."
}

fun existTpPagamento(text: String?): Boolean {
    val param = text!!
    var exist = false
    val lTpPag = SugarRecord.listAll(TipoPagamento::class.java)
    lTpPag.forEach {
        if (it != null && it.forma!!.equals(param)) {
            exist = true
        }
    }

    return exist
}
