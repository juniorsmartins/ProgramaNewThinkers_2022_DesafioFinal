package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntradaListar;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.BairroDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasBairroAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasBairroCadastrar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RecursoNaoEncontradoException;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.portas.BairroService;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Bairro;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Municipio;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.BairroRepository;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.MunicipioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public final class BairroServiceImpl implements BairroService {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    // ---------- Atributos Injetados automaticamente
    @Autowired
    private BairroRepository bairroRepository;
    @Autowired
    private MunicipioRepository municipioRepository;
    @Autowired
    private ModelMapper modelMapper;
    // ---------- Atributos p/estilo pessoal de Clean Code
    private BairroDtoEntrada bairroDeEntrada;
    private Bairro bairroSalvo;
    private BairroDtoSaida bairroDeSaida;
    private Municipio municipioVerificado;
    private List<Bairro> listaDeBairrosSalvos;
    private List<BairroDtoSaida> listaDeBairrosDeSaida;
    private Example exampleFiltro;
    // ---------- Regras de Negócio
    @Autowired
    private List<IRegrasBairroCadastrar> listaDeRegrasDeCadastrar;
    @Autowired
    private List<IRegrasBairroAtualizar> listaDeRegrasDeAtualizar;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    @Override
    public List<BairroDtoSaida> cadastrar(BairroDtoEntrada bairroDtoEntrada) {
        bairroDeEntrada = bairroDtoEntrada;

        // Tratamento de regras de negócio
        listaDeRegrasDeCadastrar.forEach(regra -> regra.validar(bairroDeEntrada));

        converterBairroDtoEntradaParaBairro();
        salvarBairro();
        buscarTodosBairrosParaRetornar();
        converterListaDeUfsParaListaDeUfsDeSaidaOrdenada();
        return listaDeBairrosDeSaida;
    }

        private void converterBairroDtoEntradaParaBairro() {
            bairroSalvo = modelMapper.map(bairroDeEntrada, Bairro.class);
        }

        private void salvarBairro() {
            bairroRepository.saveAndFlush(bairroSalvo);
        }

        private void buscarTodosBairrosParaRetornar() {
            listaDeBairrosSalvos = bairroRepository.findAll();
        }

        private void converterListaDeUfsParaListaDeUfsDeSaidaOrdenada() {
            listaDeBairrosDeSaida = listaDeBairrosSalvos
                    .stream()
                    .map(BairroDtoSaida::new)
                    .sorted(Comparator.comparing(BairroDtoSaida::getCodigoBairro).reversed())
                    .toList();
        }

    // ---------- Listar
    @Override
    public ResponseEntity<?> listar(BairroDtoEntradaListar bairroDtoEntradaListar) {
        bairroDeEntrada = modelMapper.map(bairroDtoEntradaListar, BairroDtoEntrada.class);

        criarExampleConfiguradoPorExampleMatcher();
        listaDeBairrosSalvos = bairroRepository.findAll(exampleFiltro);

        if(listaDeBairrosSalvos.isEmpty())
            return ResponseEntity.ok().body(new ArrayList<>());

        converterListaDeUfsParaListaDeUfsDeSaidaOrdenada();

        if(bairroDeEntrada.getCodigoBairro() != null)
            return ResponseEntity.ok().body(listaDeBairrosDeSaida.stream().limit(1));

        return ResponseEntity.ok().body(listaDeBairrosDeSaida);
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
            exampleFiltro = Example.of(modelMapper.map(bairroDeEntrada, Bairro.class), matcher);
        }

    // ---------- Consultar
    @Override
    public BairroDtoSaida consultar(Long codigoBairro) {

        return bairroRepository.findById(codigoBairro)
                .map(bairro -> {
                    bairroSalvo = bairro;
                    converterBairroParaBairroDtoSaida();
                    return bairroDeSaida;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao.CODIGOBAIRRO_NAO_ENCONTRADO));
    }

        private void converterBairroParaBairroDtoSaida() {
            bairroDeSaida = modelMapper.map(bairroSalvo, BairroDtoSaida.class);
        }

    // ---------- Atualizar
    @Override
    public List<BairroDtoSaida> atualizar(BairroDtoEntradaAtualizar bairroDtoEntrada) {
        bairroDeEntrada = modelMapper.map(bairroDtoEntrada, BairroDtoEntrada.class);

        // Tratamento de regras de negócio
        listaDeRegrasDeAtualizar.forEach(regra -> regra.validar(bairroDtoEntrada));

        return bairroRepository.findById(bairroDtoEntrada.getCodigoBairro())
                .map(bairro -> {
                    bairroSalvo = bairro;
                    disponibilizarMunicipioVerificado();
                    atualizarBairro();
                    buscarTodosBairrosParaRetornar();
                    converterListaDeUfsParaListaDeUfsDeSaidaOrdenada();
                    return listaDeBairrosDeSaida;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao.CODIGOBAIRRO_NAO_ENCONTRADO));
    }

        private void disponibilizarMunicipioVerificado() {
            municipioVerificado = municipioRepository.findById(bairroDeEntrada.getCodigoMunicipio()).get();
        }

        private void atualizarBairro() {
            bairroSalvo.setMunicipio(municipioVerificado);
            bairroSalvo.setNome(bairroDeEntrada.getNome());
            bairroSalvo.setStatus(bairroDeEntrada.getStatus());
        }

    // ---------- Deletar
    public List<BairroDtoSaida> deletar(Long codigoBairro) {

        return bairroRepository.findById(codigoBairro)
                .map(bairro -> {
                    bairro.setStatus(2);
                    buscarTodosBairrosParaRetornar();
                    converterListaDeUfsParaListaDeUfsDeSaidaOrdenada();
                    return listaDeBairrosDeSaida;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao.CODIGOBAIRRO_NAO_ENCONTRADO));
    }
}
