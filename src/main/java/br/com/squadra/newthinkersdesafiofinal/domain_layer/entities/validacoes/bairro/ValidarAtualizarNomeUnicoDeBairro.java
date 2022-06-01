package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.bairro;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.ValidacaoException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.BairroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidarAtualizarNomeUnicoDeBairro implements ValidacoesAtualizarBairro {

    @Autowired
    private BairroRepository bairroRepository;

    @Override
    public void validar(Long codigoBairro, BairroDtoEntrada bairroDtoEntrada) {

        var bairroDoDatabasePorCodigo = bairroRepository.findById(codigoBairro);
        if(!bairroDoDatabasePorCodigo.isPresent())
            throw new ValidacaoException("CodigoBairro - ".concat(MensagemPadrao.ID_NAO_ENCONTRADO));

        var bairroDoDatabasePorNome = bairroRepository.findByNome(bairroDtoEntrada.getNome());
        if(bairroDoDatabasePorNome.isPresent()) {
            if(bairroDoDatabasePorCodigo.get().getCodigoBairro() != bairroDoDatabasePorNome.get().getCodigoBairro()) {
                throw new ValidacaoException("Nome - ".concat(MensagemPadrao.VALOR_JA_EXISTE));
            }
        }
    }
}
