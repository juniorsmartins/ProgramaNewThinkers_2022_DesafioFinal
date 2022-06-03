package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.municipio;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasMunicipioCadastrar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RegrasDeNegocioVioladasException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.UfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegraCodigoUFValidoParaMunicipio implements IRegrasMunicipioCadastrar {

    @Autowired
    private UfRepository ufRepository;

    @Override
    public void validar(MunicipioDtoEntrada municipioDtoEntrada) {
        if(!ufRepository.findById(municipioDtoEntrada.getCodigoUF()).isPresent())
            throw new RegrasDeNegocioVioladasException(MensagemPadrao.CODIGOUF_NAO_ENCONTRADO);
    }
}
