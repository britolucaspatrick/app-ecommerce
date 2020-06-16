package com.example.patri.refinado.CamadaNegocio

import com.example.patri.refinado.CamadaDados.*
import com.example.patri.refinado.Util.Constantes.ID_PEDIDO
import com.example.patri.refinado.Util.Constantes.ID_USUARIO
import com.orm.SugarRecord
import java.sql.Timestamp
import kotlin.Exception

fun buscarUsuarioPedido() : Usuario?{
    val usuario = SugarRecord.findById(Usuario::class.java, ID_USUARIO)

    if (usuario!!.pessoa != null){
        usuario.pessoaP = SugarRecord.findById(Pessoa::class.java, usuario.pessoa)
        usuario.endereco = SugarRecord.findById(Endereco::class.java, usuario.pessoa)
        usuario.estado = SugarRecord.findById(Estado::class.java, usuario.endereco?.estado)
        usuario.pais = SugarRecord.findById(Pais::class.java, usuario.estado?.pais)
    }

    return usuario
}

fun confirmacaoDados(usuario: Usuario?, edt_logradouro_cf: String?, edt_complemento_cf: String?, edt_bairro_cf: String?, edt_numero_cf: String?, edt_cep_cf: String?, edt_cidade_cf: String?, edt_estado_cf: Long?, formaPagam: Long?, idPedido: Long?, nome:String?, sobrenome: String?, aniversario: String?, cpf: String?) : String{
    try{
        val validacao = validarConfirmacao(usuario, edt_logradouro_cf, edt_complemento_cf, edt_bairro_cf, edt_numero_cf, edt_cep_cf, edt_cidade_cf, edt_estado_cf, formaPagam, idPedido, nome, sobrenome, aniversario, cpf)
        if (!validacao.equals(""))
            throw Exception (validacao)

        //CASO NÃO HAJA PESSOA CADASTRADA
        val usuario = buscarUsuarioPedido()
        var idPessoa: Long?
        if (usuario!!.pessoa == null){
            idPessoa = Pessoa(nome, sobrenome, cpf, formataAniversario(aniversario!!), "A", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())).save()
            usuario!!.pessoa = idPessoa
        }else{

            //ATUALIZAR JÁ CADASTRADO
            usuario!!.pessoaP = Pessoa(nome, sobrenome, cpf, formataAniversario(aniversario!!), Timestamp(System.currentTimeMillis()))
            usuario!!.pessoaP!!.save()
        }

        //CASO NÃO HAJA ENDERECO CADASTRADO
        var idEndereco: Long?
        if (usuario!!.endereco == null){
            idEndereco = Endereco(usuario!!.pessoa, edt_estado_cf, edt_cidade_cf, null, edt_bairro_cf, edt_numero_cf, edt_complemento_cf, edt_logradouro_cf, edt_cep_cf, "A", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())).save()
        }else{

            //ATUALIZAR JÁ CADASTRADO
            usuario!!.endereco = Endereco(edt_estado_cf, edt_cidade_cf, edt_bairro_cf, edt_numero_cf, edt_complemento_cf, edt_logradouro_cf, edt_cep_cf, Timestamp(System.currentTimeMillis()))
            usuario!!.endereco!!.save()
        }

        return ""
    }catch (ex: Exception){
        return "Erro: ${ex.message}"
    }
}

fun validarConfirmacao(usuario: Usuario?, edt_logradouro_cf: String?, edt_complemento_cf: String?,
                       edt_bairro_cf: String?, edt_numero_cf: String?, edt_cep_cf: String?,
                       edt_cidade_cf: String?, edt_estado_cf: Long?, formaPagam: Long?,
                       idPedido: Long?, nome: String?, sobrenome: String?, aniversario: String?, cpf: String?): String {
    if (edt_logradouro_cf!!.length < 3)
        return "O campo logradouro deve ter no mínimo 3 caracteres."
    else if (!edt_cep_cf!!.length.equals(8))
        return "O campo CEP deve ter 8 caractres."
    else if (edt_bairro_cf!!.length < 3)
        return "O campo bairro deve ter no mínimo 3 caracteres."
    else if (edt_cidade_cf!!.length < 3)
        return "O campo cidade deve ter no mínimo 3 caracteres."
    else if (edt_estado_cf == null)
        return "O campo estado deve ser informado."
    else if (formaPagam == null)
        return "Deve ser informado uma forma de pagamento."
    else if (nome!!.length < 3)
        return "O nome deve ter no minímo 3 caracteres."
    else if (sobrenome!!.length < 3)
        return "O sobrenome deve ter no minímo 3 caracteres."
    else if (!aniversario!!.length.equals(10))
        return "A data de nascimento deve ser enquadrada na máscara aaaa/MM/dd."
    else if (edt_numero_cf.isNullOrEmpty())
        return "O campo número deve ser informado."
    else if (idPedido == null)
        return "Id. Pedido não informado corretamente."
    else if (cpf.isNullOrEmpty() || !cpf!!.length.equals(11))
        return "O campo CPF está inválido, informe 11 caracteres, apenas numéricos."
    else if (idPedido == null)
        return "Não é possível finalizar o carrinho sem itens."
    return ""
}

fun gerarPedidoPendente(adapter: ArrayList<Carrinho>): String?{
    try{
        //validar carrinho
        val validacao = validarCarrinho(adapter)
        if (!validacao.equals(""))
            throw Exception (validacao)

        val idpedido = Pedido(ID_USUARIO, "P", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())).save()
        adapter.forEach {
            //gerar pedidos produtos
            val idpedidoP = PedidoProdutos(idpedido, it.produto, null, it.preco, it.preco!! *  it.quantidade!!, it.quantidade, "P", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())).save()

            //excluir produtos do carrinho
            SugarRecord.delete(it)
        }

        ID_PEDIDO = idpedido
        return ""
    }catch (ex: Exception){
        return ex.message
    }
}

fun validarCarrinho(adapter: ArrayList<Carrinho>): String {
    var retur = ""
    adapter.forEach {
        if (it.quantidade == null)
            retur = "Número não pode ser menor que zero."
        else if (!intervaloQuantidadeDisponivel(it))
            retur = "A quantidade deve estar no intervalo mínimo e máximo estabelecidos."
    }

    return retur
}

fun intervaloQuantidadeDisponivel(it: Carrinho): Boolean {
    var produto = SugarRecord.findById(Produto::class.java, it.produto)
    if (it.quantidade!! > produto.quantidade!!)
        return false

    return true
}

fun intervaloQuantidadeDisponivelProd(id: Long?, quantidade: Int?): Boolean {
    var produto = SugarRecord.findById(Produto::class.java, id)
    if (quantidade == null)
        return false
    else if (quantidade.equals(0))
        return false
    else if (quantidade!! > produto.quantidade!!)
        return false

    return true
}

fun finalizarPedido(idFrete: Long?, idPedido: Long?): Boolean {
    if (idFrete == null)
        return false

    val pp = SugarRecord.find(PedidoProdutos::class.java, "pedido = ?", idPedido.toString())
    var vltotal  = 0.0
    if (pp != null)
    {
        try {
            pp.forEach {
                vltotal += it.vlunitario!! * it.quantidade!!

                //Abatimento da quantidade disponivel do produto
                val pr = SugarRecord.findById(Produto::class.java, it.produto)
                if (pr != null){
                    pr.quantidade = pr.quantidade!! - it.quantidade!!
                    pr.save()
                }
            }
        }catch (ex: Exception){}
    }
    var s = SugarRecord.findById(Pedido::class.java, idPedido)

    //gerar pontos de fidelidade para pedido
    try{
    val regra = SugarRecord.find(RegraCobranca::class.java, "registro = ?", "A")
    if (regra != null && regra.size > 0 && vltotal > 0){
        var mod = regra[0].valor!!.mod(vltotal)
        if (mod > 0){
            val pontos = (vltotal * mod).div(regra[0].unidade!!.toFloat()).div(100)
            val fidelidade = Fidelidade(regra[0].id, pontos.toInt(), "A", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())).save()
            s.fidelidade = fidelidade
            val fidelidadeCobranca = FidelidadeCobranca(regra[0].id, fidelidade, regra[0].unidade, regra[0].valor, Timestamp(System.currentTimeMillis())).save()
        }
    }}catch (ex:Exception){}

    s.registro = "F"
    s.vltotalitens = vltotal.toFloat()
    s.vlsubtotal = vltotal.toFloat()
    s.vlfrete = (0).toFloat()
    s.vldesconto = (0).toFloat()
    s.save()


    return true
}

fun countPedidosPorStatus(status: String): Long{
    return SugarRecord.count<Pedido>(Pedido::class.java, "registro = ?", arrayOf(status))
}

fun cancelarPedido(id: Long?): String {
    var s = SugarRecord.findById(Pedido::class.java, id)
    s.registro = "C"
    s.save()

    return "Cancelado com sucesso!"
}

