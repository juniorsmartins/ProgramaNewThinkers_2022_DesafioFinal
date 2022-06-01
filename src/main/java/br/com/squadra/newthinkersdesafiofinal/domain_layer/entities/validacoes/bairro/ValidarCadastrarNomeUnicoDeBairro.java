package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.bairro;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.ValidacaoException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.BairroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidarCadastrarNomeUnicoDeBairro implements ValidacoesCadastrarBairro {

    @Autowired
    private BairroRepository bairroRepository;

    @Override
    public void validar(BairroDtoEntrada bairroDtoEntrada) {
        if(bairroRepository.findByNome(bairroDtoEntrada.getNome()).isPresent())
            throw new ValidacaoException("Nome - ".concat(MensagemPadrao.VALOR_JA_EXISTE));
    }
}
