package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.uf;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.ValidacaoException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.UfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class ValidarAtualizarNomeUnicoDeUf implements ValidacoesAtualizarUf {

    @Autowired
    private UfRepository ufRepository;

    @Override
    public void validar(Long codigoUf, UfDtoEntrada ufDtoEntrada) {

        var ufDoDatabasePorCodigo = ufRepository.findById(codigoUf);
        if(!ufDoDatabasePorCodigo.isPresent())
            throw new ValidacaoException("CodigoUf - " + MensagemPadrao.ID_NAO_ENCONTRADO);

        var ufDoDatabasePorNome = ufRepository.findByNome(ufDtoEntrada.getNome());
        if(ufDoDatabasePorNome.isPresent())
            throw new ValidacaoException("Nome - " + MensagemPadrao.VALOR_JA_EXISTE);
    }
}
