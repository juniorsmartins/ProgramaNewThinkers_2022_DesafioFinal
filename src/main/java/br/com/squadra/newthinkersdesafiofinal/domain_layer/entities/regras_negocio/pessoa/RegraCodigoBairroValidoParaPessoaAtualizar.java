package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.pessoa;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasPessoaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RecursoNaoEncontradoException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.BairroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class RegraCodigoBairroValidoParaPessoaAtualizar implements IRegrasPessoaAtualizar {

    @Autowired
    private BairroRepository bairroRepository;

    @Override
    public void validar(PessoaDtoEntradaAtualizar pessoaDtoEntradaAtualizar) {

        if(pessoaDtoEntradaAtualizar.getEnderecos() != null)
            pessoaDtoEntradaAtualizar.getEnderecos()
                    .stream()
                    .map(endereco -> {
                        if(!bairroRepository.findById(endereco.getCodigoBairro()).isPresent()) {
                            throw new RecursoNaoEncontradoException(MensagemPadrao.CODIGOBAIRRO_NAO_ENCONTRADO);
                        }
                        return endereco;
                    }).toList();
    }
}
