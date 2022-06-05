package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.pessoa;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasPessoaCadastrar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RegrasDeNegocioVioladasException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegraLoginUnicoCadastrar implements IRegrasPessoaCadastrar {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public void validar(PessoaDtoEntrada pessoaDtoEntrada) {
        if(pessoaRepository.findByLogin(pessoaDtoEntrada.getLogin()).isPresent())
            throw new RegrasDeNegocioVioladasException(MensagemPadrao.LOGIN_NAO_UNICO);
    }
}
