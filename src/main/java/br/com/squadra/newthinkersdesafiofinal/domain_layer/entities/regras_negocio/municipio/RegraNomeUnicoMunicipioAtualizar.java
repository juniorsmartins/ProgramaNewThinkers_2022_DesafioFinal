package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.municipio;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasMunicipioAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RecursoNaoEncontradoException;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RegrasDeNegocioVioladasException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.MunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class RegraNomeUnicoMunicipioAtualizar implements IRegrasMunicipioAtualizar {

    @Autowired
    private MunicipioRepository municipioRepository;

    @Override
    public void validar(MunicipioDtoEntradaAtualizar municipioDtoEntrada) {
        var municipioPorCodigo = municipioRepository.findById(municipioDtoEntrada.getCodigoMunicipio());
        if(!municipioPorCodigo.isPresent())
            throw new RecursoNaoEncontradoException(MensagemPadrao.CODIGOMUNICIPIO_NAO_ENCONTRADO);

        var municipioPorNome = municipioRepository.findByNome(municipioDtoEntrada.getNome());
        if(municipioPorNome.isPresent())
            if(municipioPorNome.get().getCodigoMunicipio() != municipioPorCodigo.get().getCodigoMunicipio())
                throw new RegrasDeNegocioVioladasException(MensagemPadrao.NOME_NAO_UNICO);
    }
}
