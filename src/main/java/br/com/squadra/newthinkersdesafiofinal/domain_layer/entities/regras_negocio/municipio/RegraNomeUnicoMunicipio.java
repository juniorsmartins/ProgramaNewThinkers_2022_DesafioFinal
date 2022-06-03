package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.municipio;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasMunicipioCadastrar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RegrasDeNegocioVioladasException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.MunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegraNomeUnicoMunicipio implements IRegrasMunicipioCadastrar {

    @Autowired
    private MunicipioRepository municipioRepository;

    @Override
    public void validar(MunicipioDtoEntrada municipioDtoEntrada) {
        if(municipioRepository.findByNome(municipioDtoEntrada.getNome()).isPresent())
            throw new RegrasDeNegocioVioladasException(MensagemPadrao.NOME_NAO_UNICO);
    }
}
