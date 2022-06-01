package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.bairro;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.ValidacaoException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.MunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidarCadastrarExisteCodigoMunicipio implements ValidacoesCadastrarBairro {

    @Autowired
    private MunicipioRepository municipioRepository;

    @Override
    public void validar(BairroDtoEntrada bairroDtoEntrada) {
        if(!municipioRepository.findById(bairroDtoEntrada.getCodigoMunicipio()).isPresent())
            throw new ValidacaoException("CodigoMunicipio - ".concat(MensagemPadrao.ID_NAO_ENCONTRADO));
    }
}
