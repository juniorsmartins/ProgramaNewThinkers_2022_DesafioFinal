package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.pessoa;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasPessoaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RecursoNaoEncontradoException;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RegrasDeNegocioVioladasException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.EnderecoRepository;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class RegraCodigoEnderecoValidoPessoaAtualizar implements IRegrasPessoaAtualizar {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    private int contadorDeCodigoDiferente;

    @Override
    public void validar(PessoaDtoEntradaAtualizar pessoaDtoEntradaAtualizar) {

        if(!pessoaRepository.findById(pessoaDtoEntradaAtualizar.getCodigoPessoa()).isPresent())
            throw new RecursoNaoEncontradoException(MensagemPadrao.CODIGOPESSOA_NAO_ENCONTRADO);

        if(pessoaDtoEntradaAtualizar.getEnderecos() != null) {

            pessoaDtoEntradaAtualizar.getEnderecos().forEach(enderecoNovo -> {
                if(enderecoNovo.getCodigoEndereco() != null) {
                    if (!enderecoRepository.findById(enderecoNovo.getCodigoEndereco()).isPresent())
                        throw new RecursoNaoEncontradoException(MensagemPadrao.CODIGOENDERECO_NAO_ENCONTRADO);
                }
            });

            var pessoaDoDatabase = pessoaRepository.findById(pessoaDtoEntradaAtualizar.getCodigoPessoa()).get();
            pessoaDtoEntradaAtualizar.getEnderecos()
                    .stream()
                    .map(enderecoParaAtualizar -> {
                        contadorDeCodigoDiferente = 0;
                        pessoaDoDatabase.getEnderecos().forEach(pesData -> {
                            if(enderecoParaAtualizar.getCodigoEndereco() != pesData.getCodigoEndereco()
                                    && enderecoParaAtualizar.getCodigoEndereco() != null)
                                contadorDeCodigoDiferente++;
                        });
                        if(contadorDeCodigoDiferente == pessoaDoDatabase.getEnderecos().size())
                            throw new RegrasDeNegocioVioladasException(MensagemPadrao
                                    .CODIGOENDERECO_INVALIDO_PRA_PESSOA_X);
                        return null;
                    }).toList();
        }
    }
}
