package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.PessoaDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Pessoa;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.PessoaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public final class PessoaService {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    // ---------- Injetados automaticamente
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ModelMapper modelMapper;
    // ---------- Atributos p/estilo pessoal de Clean Code
    private PessoaDtoEntrada pessoaDeEntrada;
    private Pessoa pessoaSalva;
    private PessoaDtoSaida pessoaDeSaida;
    private List<Pessoa> listaDePessoasSalvas;
    private List<PessoaDtoSaida> listaDePessoasDeSaida;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    public ResponseEntity<?> cadastrar(PessoaDtoEntrada pessoaDtoEntrada, UriComponentsBuilder uriComponentsBuilder) {
        pessoaDeEntrada = pessoaDtoEntrada;

        converterPessoaDtoEntradaParaPessoa();
        setarStatusAtivado();
        salvarPessoa();
        converterPessoaParaPessoaDtoSaida();

        URI uri = uriComponentsBuilder.path("/pessoas/{codigoPessoa}").buildAndExpand(pessoaDeSaida.getCodigoPessoa()).toUri();
        return ResponseEntity.created(uri).body(pessoaDeSaida);
    }

        private void converterPessoaDtoEntradaParaPessoa() {
            pessoaSalva = modelMapper.map(pessoaDeEntrada, Pessoa.class);
        }

        private void setarStatusAtivado() {
            pessoaSalva.setStatus(1);
        }

        private void salvarPessoa() {
            pessoaSalva = pessoaRepository.saveAndFlush(pessoaSalva);
        }

        private void converterPessoaParaPessoaDtoSaida() {
            pessoaDeSaida = modelMapper.map(pessoaSalva, PessoaDtoSaida.class);
        }

    // ---------- Listar
    public ResponseEntity<?> listar(PessoaDtoEntrada filtros) {
        var pessoaFiltro = modelMapper.map(filtros, Pessoa.class);

        // ExampleMatcher - permite configurar condições para serem aplicadas nos filtros
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase() // Ignore caixa alta ou baixa - quando String
                .withStringMatcher(ExampleMatcher
                        .StringMatcher.CONTAINING); // permite encontrar palavras tipo Like com Containing

        // Example - pega campos populados para criar filtros
        Example example = Example.of(pessoaFiltro, matcher);

        listaDePessoasSalvas = pessoaRepository.findAll(example);
        converterListaDePessoasParaListaDePessoasDeSaida();

        return ResponseEntity.ok().body(listaDePessoasDeSaida);
    }

        private void converterListaDePessoasParaListaDePessoasDeSaida() {
            listaDePessoasDeSaida = listaDePessoasSalvas.stream().map(PessoaDtoSaida::new).collect(Collectors.toList());
        }

    // ---------- Consultar
    public ResponseEntity<?> consultar(Long codigoPessoa) {

        var pessoaDoDatabase = pessoaRepository.findById(codigoPessoa);
        if(!pessoaDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        pessoaSalva = pessoaDoDatabase.get();

        converterPessoaParaPessoaDtoSaida();

        return ResponseEntity.ok().body(pessoaDeSaida);
    }

    // ---------- Atualizar
    public ResponseEntity<?> atualizar(Long codigoPessoa, PessoaDtoEntrada pessoaDtoEntrada) {
        pessoaDeEntrada = pessoaDtoEntrada;

        pessoaSalva = pessoaRepository.findById(codigoPessoa).get();

        atualizarPessoa();
        converterPessoaParaPessoaDtoSaida();

        return ResponseEntity.ok().body(pessoaDeSaida);
    }

        private void atualizarPessoa() {
            pessoaSalva.setNome(pessoaDeEntrada.getNome());
            pessoaSalva.setSobrenome(pessoaDeEntrada.getSobrenome());
            pessoaSalva.setIdade(pessoaDeEntrada.getIdade());
            pessoaSalva.setLogin(pessoaDeEntrada.getLogin());
            pessoaSalva.setSenha(pessoaDeEntrada.getSenha());
        }

    // ---------- Deletar
    public ResponseEntity<?> deletar(Long codigoPessoa) {

        var pessoaDoDatabase = pessoaRepository.findById(codigoPessoa);
        if(!pessoaDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);

        pessoaRepository.deleteById(codigoPessoa);
        return ResponseEntity.ok().body(MensagemPadrao.ID_DELETADO);
    }
}
