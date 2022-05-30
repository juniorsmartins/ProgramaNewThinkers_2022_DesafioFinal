package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.pessoa;

import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.ValidacaoException;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidarLoginUnicoDePessoa implements ValidacoesPessoa {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public void validar(Long codigoId, PessoaDtoEntrada pessoaDtoEntrada) {
            var pessoaDoDatabase = pessoaRepository.findByLogin(pessoaDtoEntrada.getLogin());
            if(pessoaDoDatabase.isPresent())
                throw new ValidacaoException("Login - " + MensagemPadrao.VALOR_JA_EXISTE);
    }
}
