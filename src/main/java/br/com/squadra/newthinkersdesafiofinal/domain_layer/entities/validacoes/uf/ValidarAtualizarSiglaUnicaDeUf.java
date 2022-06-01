package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.uf;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.ValidacaoException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.UfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class ValidarAtualizarSiglaUnicaDeUf implements ValidacoesAtualizarUf {

    @Autowired
    private UfRepository ufRepository;

    @Override
    public void validar(Long codigoUF, UfDtoEntrada ufDtoEntrada) {

        var ufDoDatabasePorCodigo = ufRepository.findById(codigoUF);
        if(!ufDoDatabasePorCodigo.isPresent())
            throw new ValidacaoException("CodigoUf - " + MensagemPadrao.ID_NAO_ENCONTRADO);

        var ufDoDatabasePorSigla = ufRepository.findBySigla(ufDtoEntrada.getSigla());
        if(ufDoDatabasePorSigla.isPresent()) {
            if(ufDoDatabasePorCodigo.get().getCodigoUF() != ufDoDatabasePorSigla.get().getCodigoUF()) {
                throw new ValidacaoException("Sigla - " + MensagemPadrao.VALOR_JA_EXISTE);
            }
        }
    }
}
