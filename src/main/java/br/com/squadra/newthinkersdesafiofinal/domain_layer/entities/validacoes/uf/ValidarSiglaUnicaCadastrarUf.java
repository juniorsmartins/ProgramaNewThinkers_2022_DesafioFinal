package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.uf;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.ValidacaoException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.UfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class ValidarSiglaUnicaCadastrarUf implements ValidacoesCadastrarUf {

    @Autowired
    private UfRepository ufRepository;

    @Override
    public void validar(UfDtoEntrada ufDtoEntrada) {
            var ufDoDatabase = ufRepository.findBySigla(ufDtoEntrada.getSigla());
            if(ufDoDatabase.isPresent())
                throw new ValidacaoException("Sigla - " + MensagemPadrao.VALOR_JA_EXISTE);
    }
}
