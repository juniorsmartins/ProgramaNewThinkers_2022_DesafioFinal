package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.pessoa;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasPessoaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RegrasDeNegocioVioladasException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class RegraLoginUnicoPessoaAtualizar implements IRegrasPessoaAtualizar {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public void validar(PessoaDtoEntradaAtualizar pessoaDtoEntradaAtualizar) {
        var pessoaDoDatabase = pessoaRepository.findByLogin(pessoaDtoEntradaAtualizar.getLogin());
        if(pessoaDoDatabase.isPresent()) {
            if(pessoaDoDatabase.get().getCodigoPessoa() != pessoaDtoEntradaAtualizar.getCodigoPessoa())
                throw new RegrasDeNegocioVioladasException(MensagemPadrao.LOGIN_NAO_UNICO);
        }
    }
}
