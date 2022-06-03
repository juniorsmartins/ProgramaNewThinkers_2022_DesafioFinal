package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.uf;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasUfAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RegrasDeNegocioVioladasException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.UfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegraNomeUnicoAtualizar implements IRegrasUfAtualizar {

    @Autowired
    private UfRepository ufRepository;

    @Override
    public void validar(UfDtoEntradaAtualizar ufDtoEntradaAtualizar) {
        var ufPorCodigo = ufRepository.findById(ufDtoEntradaAtualizar.getCodigoUF());
        if(!ufPorCodigo.isPresent())
            throw new RegrasDeNegocioVioladasException(MensagemPadrao.CODIGOUF_NAO_ENCONTRADO);

        var ufPorNome = ufRepository.findByNome(ufDtoEntradaAtualizar.getNome());
        if(ufPorNome.isPresent()) {
            if(ufPorNome.get().getCodigoUF() != ufPorCodigo.get().getCodigoUF()) {
                throw new RegrasDeNegocioVioladasException(MensagemPadrao.NOME_NAO_UNICO);
            }
        }
    }
}
