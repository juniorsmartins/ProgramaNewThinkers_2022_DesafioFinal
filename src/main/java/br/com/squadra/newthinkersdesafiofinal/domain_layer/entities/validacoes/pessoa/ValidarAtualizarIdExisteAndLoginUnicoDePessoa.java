package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.pessoa;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.ValidacaoException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class ValidarAtualizarIdExisteAndLoginUnicoDePessoa implements ValidacoesAtualizarPessoa {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public void validar(Long codigoId, PessoaDtoEntrada pessoaDtoEntrada) {

        var pessoaDoDatabasePorCodigo = pessoaRepository.findById(codigoId);
        if(!pessoaDoDatabasePorCodigo.isPresent())
            throw new ValidacaoException("codigoPessoa - " + MensagemPadrao.ID_NAO_ENCONTRADO);

        var pessoaDoDatabasePorLogin = pessoaRepository.findByLogin(pessoaDtoEntrada.getLogin());
        if(pessoaDoDatabasePorLogin.isPresent()) {
            if(pessoaDoDatabasePorCodigo.get().getCodigoPessoa() != pessoaDoDatabasePorLogin.get().getCodigoPessoa()) {
                throw new ValidacaoException("Login - " + MensagemPadrao.VALOR_JA_EXISTE);
            }
        }
    }
}
