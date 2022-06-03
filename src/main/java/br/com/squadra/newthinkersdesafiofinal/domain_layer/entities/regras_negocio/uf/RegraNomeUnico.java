package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.uf;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasUfCadastrar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RegrasDeNegocioVioladasException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.UfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegraNomeUnico implements IRegrasUfCadastrar {

    @Autowired
    private UfRepository ufRepository;

    @Override
    public void validar(UfDtoEntrada ufDtoEntrada) {
        if(ufRepository.findByNome(ufDtoEntrada.getNome()).isPresent())
            throw new RegrasDeNegocioVioladasException(MensagemPadrao.NOME_NAO_UNICO);
    }
}
