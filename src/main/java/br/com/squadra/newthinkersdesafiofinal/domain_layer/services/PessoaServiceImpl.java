package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.PessoaDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.portas.PessoaService;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Pessoa;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.BairroRepository;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.MunicipioRepository;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.PessoaRepository;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.UfRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public final class PessoaServiceImpl implements PessoaService {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    // ---------- Injetados automaticamente
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private UfRepository ufRepository;
    @Autowired
    private MunicipioRepository municipioRepository;
    @Autowired
    private BairroRepository bairroRepository;
    @Autowired
    private ModelMapper modelMapper;
    // ---------- Atributos p/estilo pessoal de Clean Code
    private PessoaDtoEntrada pessoaDeEntrada;
    private Pessoa pessoaSalva;
    private PessoaDtoSaida pessoaDeSaida;
    private List<Pessoa> listaDePessoasSalvas;
    private List<PessoaDtoSaida> listaDePessoasDeSaida;
    private ExampleMatcher matcher;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    public List<PessoaDtoSaida> cadastrar(PessoaDtoEntrada pessoaDtoEntrada) {
        pessoaDeEntrada = pessoaDtoEntrada;

        // Tratamento de regras de negócio
        // ???? inserir regras

        converterPessoaDtoEntradaParaPessoa();
        salvarPessoa();
        buscarTodasPessoasParaRetornar();
        converterListaDePessoasParaListaDePessoasDeSaida();

        return listaDePessoasDeSaida;
    }

        private void converterPessoaDtoEntradaParaPessoa() {
            pessoaSalva = modelMapper.map(pessoaDeEntrada, Pessoa.class);
        }

        private void salvarPessoa() {
            pessoaRepository.saveAndFlush(pessoaSalva);
        }

        private void buscarTodasPessoasParaRetornar() {
            listaDePessoasSalvas = pessoaRepository.findAll()
                    .stream()
                    .sorted((pes1, pes2) -> pes2.getCodigoPessoa().compareTo(pes1.getCodigoPessoa()))
                    .toList();
        }

        private void converterListaDePessoasParaListaDePessoasDeSaida() {
            listaDePessoasDeSaida = listaDePessoasSalvas
                    .stream()
                    .map(PessoaDtoSaida::new)
                    .toList();
        }

    // ---------- Listar
    public ResponseEntity<?> listar(PessoaDtoEntrada filtros) {

        criarExampleMatcherParaConfigurarFiltros();
        // Example - pega campos populados para criar filtros
        Example example = Example.of(modelMapper.map(filtros, Pessoa.class), matcher);

        listaDePessoasSalvas = pessoaRepository.findAll(example);
        converterListaDePessoasParaListaDePessoasDeSaida();

        return ResponseEntity.ok().body(listaDePessoasDeSaida);
    }

        private void criarExampleMatcherParaConfigurarFiltros() {
            // ExampleMatcher - permite configurar condições para serem aplicadas nos filtros
            matcher = ExampleMatcher
                    .matching()
                    .withIgnoreCase() // Ignore caixa alta ou baixa - quando String
                    .withIgnoreNullValues()
                    .withStringMatcher(ExampleMatcher
                            .StringMatcher.CONTAINING); // permite encontrar palavras tipo Like com Containing
        }

    // ---------- Consultar
    public PessoaDtoSaida consultar(Long codigoPessoa) {

        var pessoaDoDatabase = pessoaRepository.findById(codigoPessoa);
        if(!pessoaDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        pessoaSalva = pessoaDoDatabase.get();

        converterPessoaParaPessoaDtoSaida();

        return ResponseEntity.ok().body(pessoaDeSaida);
    }

        private void converterPessoaParaPessoaDtoSaida() {
            pessoaDeSaida = modelMapper.map(pessoaSalva, PessoaDtoSaida.class);
        }

    // ---------- Atualizar
    public List<PessoaDtoSaida> atualizar(PessoaDtoEntrada pessoaDtoEntrada) {
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
    public List<PessoaDtoSaida> deletar(Long codigoPessoa) {

        var pessoaDoDatabase = pessoaRepository.findById(codigoPessoa);
        if(!pessoaDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);

        pessoaRepository.deleteById(codigoPessoa);
        return ResponseEntity.ok().body(MensagemPadrao.ID_DELETADO);
    }
}
