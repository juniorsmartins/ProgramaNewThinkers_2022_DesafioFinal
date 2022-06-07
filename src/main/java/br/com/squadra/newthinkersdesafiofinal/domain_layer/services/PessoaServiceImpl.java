package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.EnderecoDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.PessoaDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.detalhado.PessoaDtoSaidaDetalhado;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasPessoaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasPessoaCadastrar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RecursoNaoEncontradoException;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.portas.PessoaService;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Endereco;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Pessoa;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ModelMapper modelMapper;
    // ---------- Atributos p/estilo pessoal de Clean Code
    private PessoaDtoEntrada pessoaDeEntrada;
    private Pessoa pessoaSalva;
    private PessoaDtoSaida pessoaDeSaida;
    private PessoaDtoSaidaDetalhado pessoaDeSaidaDetalhado;
    private List<Pessoa> listaDePessoasSalvas;
    private List<PessoaDtoSaida> listaDePessoasDeSaida;
    private Example exampleFiltro;
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
    public ResponseEntity<?> listar(PessoaDtoEntrada pessoaDtoEntrada) {
        pessoaDeEntrada = pessoaDtoEntrada;

        criarExampleConfiguradoPorExampleMatcher();
        listaDePessoasSalvas = pessoaRepository.findAll(exampleFiltro);

        if(listaDePessoasSalvas.isEmpty())
            return ResponseEntity.ok().body(new ArrayList<>());

        if(pessoaDeEntrada.getCodigoPessoa() != null) {
            var pessoaDoDatabase = pessoaRepository.findOne(exampleFiltro);
            pessoaSalva = (Pessoa) pessoaDoDatabase.get();
            converterPessoaParaPessoaDtoSaidaDetalhada();
            return ResponseEntity.ok().body(pessoaDeSaidaDetalhado);
        }

        if(pessoaDeEntrada.getLogin() != null) {
            var pessoaDoDatabase = pessoaRepository.findOne(exampleFiltro);
            pessoaSalva = (Pessoa) pessoaDoDatabase.get();
            convertePessoaParaPessoaDtoSaida();
            return ResponseEntity.ok().body(pessoaDeSaida);
        }

        if(pessoaDeEntrada.getNome() != null || pessoaDeEntrada.getSobrenome() != null
                || pessoaDeEntrada.getStatus() != null || pessoaDeEntrada.getIdade() != null) {
            converterListaDePessoasParaListaDePessoasDeSaida();
            return ResponseEntity.ok().body(listaDePessoasDeSaida);
        }

        buscarTodasPessoasParaRetornar();
        converterListaDePessoasParaListaDePessoasDeSaida();
        return ResponseEntity.ok().body(listaDePessoasDeSaida);
    }

        private void criarExampleConfiguradoPorExampleMatcher() {
            // ExampleMatcher - permite configurar condições para serem aplicadas nos filtros
            ExampleMatcher matcher = ExampleMatcher
                    .matching()
                    .withIgnoreCase() // Ignore caixa alta ou baixa - quando String
                    .withIgnoreNullValues()
                    .withStringMatcher(ExampleMatcher
                            .StringMatcher.STARTING); // permite encontrar palavras tipo Like com Containing
            // Example - pega campos populados para criar filtros
            exampleFiltro = Example.of(modelMapper.map(pessoaDeEntrada, Pessoa.class), matcher);
        }

        private void converterPessoaParaPessoaDtoSaidaDetalhada() {
            pessoaDeSaidaDetalhado = modelMapper.map(pessoaSalva, PessoaDtoSaidaDetalhado.class);
        }

        private void convertePessoaParaPessoaDtoSaida() {
            pessoaDeSaida = modelMapper.map(pessoaSalva, PessoaDtoSaida.class);
        }

    // ---------- Consultar
    @Override
    public PessoaDtoSaidaDetalhado consultar(Long codigoPessoa) {

        return pessoaRepository.findById(codigoPessoa)
                .map(pessoa -> {
                    pessoaSalva = pessoa;
                    converterPessoaParaPessoaDtoSaidaDetalhada();
                    return pessoaDeSaidaDetalhado;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao
                        .CODIGOPESSOA_NAO_ENCONTRADO));
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
                    atualizaEnderecos();
                    atualizarPessoa();
                    incluirCodigoPessoaEmEnderecos();
                    buscarTodasPessoasParaRetornar();
                    converterListaDePessoasParaListaDePessoasDeSaida();
                    return listaDePessoasDeSaida;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao
                        .CODIGOPESSOA_NAO_ENCONTRADO));
    }

    private void atualizaEnderecos() {
        List<Endereco> listaDeEnderecosNovosParaAdicionar = new ArrayList<>();
        List<Endereco> listaDeEnderecosVelhosParaDeletar = new ArrayList<>();

        for(Endereco enderecoDoDatabase : pessoaSalva.getEnderecos()) {
            var enderecoDescartado = true;
            for(EnderecoDtoEntrada enderecoAtual : pessoaDeEntrada.getEnderecos()) {
                if(enderecoDoDatabase.getCodigoEndereco() == enderecoAtual.getCodigoEndereco()
                        && enderecoAtual.getCodigoEndereco() != null) {
                    enderecoDoDatabase.setBairro(bairroRepository.findById(enderecoAtual.getCodigoBairro()).get());
                    enderecoDoDatabase.setCep(enderecoAtual.getCep());
                    enderecoDoDatabase.setNomeRua(enderecoAtual.getNomeRua());
                    enderecoDoDatabase.setNumero(enderecoAtual.getNumero());
                    enderecoDoDatabase.setComplemento(enderecoAtual.getComplemento());
                    enderecoDescartado = false;
                }
                if(enderecoAtual.getCodigoEndereco() == null) {
                    Endereco enderecoNovo = new Endereco();
                    enderecoNovo.setBairro(bairroRepository.findById(enderecoAtual.getCodigoBairro()).get());
                    enderecoNovo.setCep(enderecoAtual.getCep());
                    enderecoNovo.setNomeRua(enderecoAtual.getNomeRua());
                    enderecoNovo.setNumero(enderecoAtual.getNumero());
                    enderecoNovo.setComplemento(enderecoAtual.getComplemento());
                    enderecoRepository.saveAndFlush(enderecoNovo);
                    enderecoNovo.setPessoa(pessoaRepository.findById(pessoaDeEntrada.getCodigoPessoa()).get());
                    enderecoAtual.setCodigoEndereco(enderecoNovo.getCodigoEndereco());
                    listaDeEnderecosNovosParaAdicionar.add(enderecoNovo);
                }
            }
            if(enderecoDescartado == true) {
                listaDeEnderecosVelhosParaDeletar.add(enderecoDoDatabase);
            }
        }

        if(!listaDeEnderecosVelhosParaDeletar.isEmpty()) {
            listaDeEnderecosVelhosParaDeletar.forEach(enderecoVelho -> {
                pessoaSalva.getEnderecos().remove(enderecoVelho);
                enderecoRepository.delete(enderecoVelho);
            });
        }

        if(!listaDeEnderecosNovosParaAdicionar.isEmpty()) {
            listaDeEnderecosNovosParaAdicionar.forEach(enderecoNovo ->
                    pessoaSalva.getEnderecos().add(enderecoNovo));
            pessoaRepository.saveAndFlush(pessoaSalva);
        }
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
