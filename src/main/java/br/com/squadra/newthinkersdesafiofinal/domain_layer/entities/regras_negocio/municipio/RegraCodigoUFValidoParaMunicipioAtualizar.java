package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.municipio;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasMunicipioAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RegrasDeNegocioVioladasException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.UfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegraCodigoUFValidoParaMunicipioAtualizar implements IRegrasMunicipioAtualizar {

    @Autowired
    private UfRepository ufRepository;

    @Override
    public void validar(MunicipioDtoEntradaAtualizar municipioDtoEntrada) {
        if(!ufRepository.findById(municipioDtoEntrada.getCodigoUF()).isPresent())
            throw new RegrasDeNegocioVioladasException(MensagemPadrao.CODIGOUF_NAO_ENCONTRADO);
    }
}
