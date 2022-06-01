package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.EnderecoDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.EnderecoDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Bairro;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Endereco;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Pessoa;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.BairroRepository;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.EnderecoRepository;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.PessoaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public final class EnderecoService {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    // ---------- Atributos Injetados automaticamente
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private BairroRepository bairroRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ModelMapper modelMapper;
    // ---------- Atributos p/estilo pessoal de Clean Code
    private EnderecoDtoEntrada enderecoDeEntrada;
    private Endereco enderecoSalvo;
    private EnderecoDtoSaida enderecoDeSaida;
    private Bairro bairroVerificado;
    private Pessoa pessoaVerificada;
    private List<Endereco> listaDeEnderecosSalvos;
    private List<EnderecoDtoSaida> listaDeEnderecosDeSaida;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    public ResponseEntity<?> cadastrar(EnderecoDtoEntrada enderecoDtoEntrada) {
        enderecoDeEntrada = enderecoDtoEntrada;

        var pessoaDoDatabase = pessoaRepository.findById(enderecoDeEntrada.getCodigoPessoa());
        if(!pessoaDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        pessoaVerificada = pessoaDoDatabase.get();

        var bairroDoDatabase = bairroRepository.findById(enderecoDeEntrada.getCodigoBairro());
        if(!bairroDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        bairroVerificado = bairroDoDatabase.get();

        converterEnderecoDtoEntradaParaEndereco();
        salvarEndereco();
        buscarTodosEnderecosParaRetornar();
        converterListaDeEnderecosParaListaDeEnderecosDeSaida();

        return ResponseEntity.ok().body(listaDeEnderecosDeSaida);
    }

        private void converterEnderecoDtoEntradaParaEndereco() {
            enderecoSalvo = new Endereco();
            enderecoSalvo.setCep(enderecoDeEntrada.getCep());
            enderecoSalvo.setNomeRua(enderecoDeEntrada.getNomeRua());
            enderecoSalvo.setNumero(enderecoDeEntrada.getNumero());
            enderecoSalvo.setComplemento(enderecoDeEntrada.getComplemento());
            enderecoSalvo.setBairro(bairroVerificado);
            enderecoSalvo.setPessoa(pessoaVerificada);
            enderecoSalvo.setStatus(enderecoDeEntrada.getStatus());
        }

        private void salvarEndereco() {
            enderecoSalvo = enderecoRepository.saveAndFlush(enderecoSalvo);
        }

        private void buscarTodosEnderecosParaRetornar() {
            listaDeEnderecosSalvos = enderecoRepository.findAll();
        }

        private void converterListaDeEnderecosParaListaDeEnderecosDeSaida() {
            listaDeEnderecosDeSaida = listaDeEnderecosSalvos.stream().map(EnderecoDtoSaida::new).collect(Collectors.toList());
        }

    // ---------- Listar
    public ResponseEntity<?> listar(EnderecoDtoEntrada filtros) {
        var enderecoFiltro = modelMapper.map(filtros, Endereco.class);

        // ExampleMatcher - permite configurar condições para serem aplicadas nos filtros
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase() // Ignore caixa alta ou baixa - quando String
                .withStringMatcher(ExampleMatcher
                        .StringMatcher.CONTAINING); // permite encontrar palavras tipo Like com Containing

        // Example - pega campos populados para criar filtros
        Example example = Example.of(enderecoFiltro, matcher);

        listaDeEnderecosSalvos = enderecoRepository.findAll(example);
        converterListaDeEnderecosParaListaDeEnderecosDeSaida();

        return ResponseEntity.ok().body(listaDeEnderecosDeSaida);
    }

    // ---------- Consultar
    public ResponseEntity<?> consultar(Long codigoEndereco) {

        var enderecoDoDatabase = enderecoRepository.findById(codigoEndereco);
        if(!enderecoDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        enderecoSalvo = enderecoDoDatabase.get();

        converterEnderecoParaEnderecoDtoSaida();

        return ResponseEntity.ok().body(enderecoDeSaida);
    }

        private void converterEnderecoParaEnderecoDtoSaida() {
            enderecoDeSaida = modelMapper.map(enderecoSalvo, EnderecoDtoSaida.class);
        }

    // ---------- Atualizar
    public ResponseEntity<?> atualizar(Long codigoEndereco, EnderecoDtoEntrada enderecoDtoEntrada) {
        enderecoDeEntrada = enderecoDtoEntrada;

        var enderecoDoDatabase = enderecoRepository.findById(codigoEndereco);
        if(!enderecoDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        enderecoSalvo = enderecoDoDatabase.get();

        var bairroDoDatabase = bairroRepository.findById(enderecoDeEntrada.getCodigoBairro());
        if(!bairroDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        bairroVerificado = bairroDoDatabase.get();

        atualizarEndereco();
        converterEnderecoParaEnderecoDtoSaida();

        return ResponseEntity.ok().body(enderecoDeSaida);
    }

        private void atualizarEndereco() {
            enderecoSalvo.setCep(enderecoDeEntrada.getCep());
            enderecoSalvo.setNomeRua(enderecoDeEntrada.getNomeRua());
            enderecoSalvo.setNumero(enderecoDeEntrada.getNumero());
            enderecoSalvo.setComplemento(enderecoDeEntrada.getComplemento());
            enderecoSalvo.setBairro(bairroVerificado);
        }

    // ---------- Deletar
    public ResponseEntity<?> deletar(Long codigoEndereco) {

        var enderecoDoDatabase = enderecoRepository.findById(codigoEndereco);
        if(!enderecoDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);

        enderecoRepository.deleteById(codigoEndereco);
        return ResponseEntity.ok().body(MensagemPadrao.ID_DELETADO);
    }
}
