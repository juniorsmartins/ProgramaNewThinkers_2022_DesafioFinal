package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.PessoaDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.detalhado.PessoaDtoSaidaDetalhado;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasPessoaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasPessoaCadastrar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RecursoNaoEncontradoException;
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
    private PessoaDtoSaidaDetalhado pessoaDeSaidaDetalhado;
    private List<Pessoa> listaDePessoasSalvas;
    private List<PessoaDtoSaida> listaDePessoasDeSaida;
    private ExampleMatcher matcher;
    // ---------- Regras de Negócio
    @Autowired
    private List<IRegrasPessoaCadastrar> listaDeRegrasDeCadastrar;
    @Autowired
    private List<IRegrasPessoaAtualizar> listaDeRegrasDeAtualizar;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    public List<PessoaDtoSaida> cadastrar(PessoaDtoEntrada pessoaDtoEntrada) {
        pessoaDeEntrada = pessoaDtoEntrada;

        // Tratamento de regras de negócio
        listaDeRegrasDeCadastrar.forEach(regra -> regra.validar(pessoaDeEntrada));

        converterPessoaDtoEntradaParaPessoa();
        salvarPessoa();
        incluirCodigoPessoaEmEnderecos();
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

        private void incluirCodigoPessoaEmEnderecos() {
            if(pessoaSalva.getEnderecos() != null)
                pessoaSalva.getEnderecos().forEach(endereco -> endereco.setPessoa(pessoaSalva));
        }

        private void buscarTodasPessoasParaRetornar() {
            listaDePessoasSalvas = pessoaRepository.findAll();
        }

        private void converterListaDePessoasParaListaDePessoasDeSaida() {
            listaDePessoasDeSaida = (listaDePessoasSalvas
                    .stream()
                    .map(PessoaDtoSaida::new)
                    .toList())
                    .stream()
                    .sorted((pes1, pes2) -> pes2.getCodigoPessoa().compareTo(pes1.getCodigoPessoa()))
                    .toList();
        }

    // ---------- Listar
    public ResponseEntity<?> listar(PessoaDtoEntrada filtros) {

        criarExampleMatcherParaConfigurarFiltros();
        // Example - pega campos populados para criar filtros
        Example example = Example.of(modelMapper.map(filtros, Pessoa.class), matcher);

        if(filtros.getCodigoPessoa() != null || filtros.getLogin() != null) {
            var pessoaDoDatabase = pessoaRepository.findOne(example);
            if(!pessoaDoDatabase.isPresent())
                throw new RecursoNaoEncontradoException(MensagemPadrao.RECURSO_NAO_ENCONTRADO);
            pessoaSalva = (Pessoa) pessoaDoDatabase.get();

            converterPessoaParaPessoaDtoSaidaDetalhada();
            return ResponseEntity.ok().body(pessoaDeSaidaDetalhado);
        }

        if(filtros.getNome() != null || filtros.getSobrenome() != null
                || filtros.getStatus() != null || filtros.getIdade() != null) {
            listaDePessoasSalvas = pessoaRepository.findAll(example);
            if(listaDePessoasSalvas.isEmpty())
                throw new RecursoNaoEncontradoException(MensagemPadrao.RECURSO_NAO_ENCONTRADO);
            converterListaDePessoasParaListaDePessoasDeSaida();
            return ResponseEntity.ok().body(listaDePessoasDeSaida);
        }

        buscarTodasPessoasParaRetornar();
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

        private void converterPessoaParaPessoaDtoSaidaDetalhada() {
            pessoaDeSaidaDetalhado = modelMapper.map(pessoaSalva, PessoaDtoSaidaDetalhado.class);
        }

    // ---------- Consultar
    @Override
    public PessoaDtoSaida consultar(Long codigoPessoa) {

        return pessoaRepository.findById(codigoPessoa)
                .map(pessoa -> {
                    pessoaSalva = pessoa;
                    converterPessoaParaPessoaDtoSaida();
                    return pessoaDeSaida;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao
                        .CODIGOPESSOA_NAO_ENCONTRADO));
    }

        private void converterPessoaParaPessoaDtoSaida() {
            pessoaDeSaida = modelMapper.map(pessoaSalva, PessoaDtoSaida.class);
        }

    // ---------- Atualizar
    @Override
    public List<PessoaDtoSaida> atualizar(PessoaDtoEntradaAtualizar pessoaDtoEntrada) {
        pessoaDeEntrada = modelMapper.map(pessoaDtoEntrada, PessoaDtoEntrada.class);

        // Tratamento de regras de negócio
        listaDeRegrasDeAtualizar.forEach(regra -> regra.validar(pessoaDtoEntrada));

        return pessoaRepository.findById(pessoaDtoEntrada.getCodigoPessoa())
                .map(pessoa -> {
                    pessoaSalva = pessoa;
                    atualizarPessoa();
                    buscarTodasPessoasParaRetornar();
                    converterListaDePessoasParaListaDePessoasDeSaida();
                    return listaDePessoasDeSaida;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao
                        .CODIGOPESSOA_NAO_ENCONTRADO));
    }

        private void atualizarPessoa() {
            pessoaSalva.setNome(pessoaDeEntrada.getNome());
            pessoaSalva.setSobrenome(pessoaDeEntrada.getSobrenome());
            pessoaSalva.setIdade(pessoaDeEntrada.getIdade());
            pessoaSalva.setLogin(pessoaDeEntrada.getLogin());
            pessoaSalva.setSenha(pessoaDeEntrada.getSenha());
            pessoaSalva.setStatus(pessoaDeEntrada.getStatus());
        }

    // ---------- Deletar
    @Override
    public List<PessoaDtoSaida> deletar(Long codigoPessoa) {

        return pessoaRepository.findById(codigoPessoa)
                .map(pessoa -> {
                    pessoaRepository.delete(pessoa);
                    buscarTodasPessoasParaRetornar();
                    converterListaDePessoasParaListaDePessoasDeSaida();
                    return listaDePessoasDeSaida;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao
                        .CODIGOPESSOA_NAO_ENCONTRADO));
    }
}
