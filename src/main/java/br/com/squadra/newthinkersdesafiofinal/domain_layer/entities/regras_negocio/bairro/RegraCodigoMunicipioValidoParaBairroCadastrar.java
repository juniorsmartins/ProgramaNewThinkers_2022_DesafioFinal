package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.bairro;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasBairroCadastrar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RecursoNaoEncontradoException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.MunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegraCodigoMunicipioValidoParaBairroCadastrar implements IRegrasBairroCadastrar {

    @Autowired
    private MunicipioRepository municipioRepository;

    @Override
    public void validar(BairroDtoEntrada bairroDtoEntrada) {
        if(!municipioRepository.findById(bairroDtoEntrada.getCodigoMunicipio()).isPresent())
            throw new RecursoNaoEncontradoException(MensagemPadrao.CODIGOMUNICIPIO_NAO_ENCONTRADO);
    }
}
