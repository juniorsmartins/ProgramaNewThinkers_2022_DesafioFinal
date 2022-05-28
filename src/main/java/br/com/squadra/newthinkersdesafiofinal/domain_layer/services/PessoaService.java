package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.PessoaDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities.Pessoa;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.PessoaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public final class PessoaService {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    // ---------- Atributos Injetados automaticamente
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
    public ResponseEntity<?> listar() {

        buscarTodasAsPessoasDoDatabase();
        converterListaDePessoasParaListaDePessoasDeSaida();

        return ResponseEntity.ok().body(listaDePessoasDeSaida);
    }

        private void buscarTodasAsPessoasDoDatabase() {
            listaDePessoasSalvas = pessoaRepository.findAll();
        }

        private void converterListaDePessoasParaListaDePessoasDeSaida() {
            listaDePessoasDeSaida = listaDePessoasSalvas.stream().map(PessoaDtoSaida::new).collect(Collectors.toList());
        }

    // ---------- Consultar
    public ResponseEntity<?> consultar(Long codigoPessoa) {

        var pessoaDoDatabase = pessoaRepository.findById(codigoPessoa);
        if(!pessoaDoDatabase.isPresent())
            return ResponseEntity.badRequest().body("Chave Identificadora não encontrada!");
        pessoaSalva = pessoaDoDatabase.get();

        converterPessoaParaPessoaDtoSaida();

        return ResponseEntity.ok().body(pessoaDeSaida);
    }

    // ---------- Atualizar
    public ResponseEntity<?> atualizar(Long codigoPessoa, PessoaDtoEntrada pessoaDtoEntrada) {
        pessoaDeEntrada = pessoaDtoEntrada;

        var pessoaDoDatabase = pessoaRepository.findById(codigoPessoa);
        if(!pessoaDoDatabase.isPresent())
            return ResponseEntity.badRequest().body("Chave Identificadora não encontrada!");
        pessoaSalva = pessoaDoDatabase.get();

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
            return ResponseEntity.badRequest().body("Chave Identificadora não encontrada!");

        pessoaRepository.deleteById(codigoPessoa);
        return ResponseEntity.ok().body("Deletada Pessoa com Chave Identificadora:" + codigoPessoa + ".");
    }
}
