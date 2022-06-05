package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.pessoa;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasPessoaCadastrar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RecursoNaoEncontradoException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.BairroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class RegraCodigoBairroValidoParaPessoaCadastrar implements IRegrasPessoaCadastrar {

    @Autowired
    private BairroRepository bairroRepository;

    @Override
    public void validar(PessoaDtoEntrada pessoaDtoEntrada) {

        if(pessoaDtoEntrada.getEnderecos() != null)
            pessoaDtoEntrada.getEnderecos()
                    .stream()
                    .map(enderecoDtoEntrada -> {
                        if(!bairroRepository.findById(enderecoDtoEntrada.getCodigoBairro()).isPresent()) {
                            throw new RecursoNaoEncontradoException(MensagemPadrao
                                    .CODIGOBAIRRO_NAO_ENCONTRADO);
                        }
                        return enderecoDtoEntrada;
                    }).toList();
    }
}
