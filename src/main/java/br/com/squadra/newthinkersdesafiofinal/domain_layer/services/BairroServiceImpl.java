package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.BairroDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.MunicipioDtoSaida;
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
import java.util.List;
import java.util.stream.Collectors;

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
    private ExampleMatcher matcher;
    // ---------- Regras de Negócio
    @Autowired
    private List<IRegrasBairroCadastrar> listaDeRegrasDeCadastro;
    @Autowired
    private List<IRegrasBairroAtualizar> listaDeRegrasDeCadastro;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    @Override
    public List<BairroDtoSaida> cadastrar(BairroDtoEntrada bairroDtoEntrada) {
        bairroDeEntrada = bairroDtoEntrada;

        // Tratamento de regras de negócio
        // ?? incluir padrão de tratamento de exceção

        municipioVerificado = municipioRepository.findById(bairroDeEntrada.getCodigoMunicipio()).get();
        converterBairroDtoEntradaParaBairro();
        salvarBairro();
        buscarTodosBairrosParaRetornar();
        converterListaDeBairrosParaListaDeBairrosDeSaida();
        return listaDeBairrosDeSaida;
    }

        private void converterBairroDtoEntradaParaBairro() {
            bairroSalvo = modelMapper.map(bairroSalvo, Bairro.class);
        }

        private void salvarBairro() {
            bairroRepository.saveAndFlush(bairroSalvo);
        }

        private void buscarTodosBairrosParaRetornar() {
            listaDeBairrosSalvos = bairroRepository.findAll()
                    .stream()
                    .sorted((b1, b2) -> b2.getCodigoBairro().compareTo(b1.getCodigoBairro()))
                    .toList();
        }

        private void converterListaDeBairrosParaListaDeBairrosDeSaida() {
            listaDeBairrosDeSaida = listaDeBairrosSalvos
                    .stream()
                    .map(BairroDtoSaida::new)
                    .toList();
        }

    // ---------- Listar
    @Override
    public ResponseEntity<?> listar(BairroDtoEntrada filtros) {
        var bairroFiltro = modelMapper.map(filtros, Bairro.class);

        // ExampleMatcher - permite configurar condições para serem aplicadas nos filtros
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                        .withIgnoreCase() // Ignore caixa alta ou baixa - quando String
                                .withStringMatcher(ExampleMatcher
                                        .StringMatcher.CONTAINING); // permite encontrar palavras tipo Like com Containing

        // Example - pega campos populados para criar filtros
        Example example = Example.of(bairroFiltro, matcher);

        listaDeBairrosSalvos = bairroRepository.findAll(example);
        converterListaDeBairrosParaListaDeBairrosDeSaida();

        return ResponseEntity.ok().body(listaDeBairrosDeSaida);
    }

        private void converterBairroParaBairroDtoSaida() {
            bairroDeSaida = modelMapper.map(bairroSalvo, BairroDtoSaida.class);
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

    // ---------- Atualizar
    public List<BairroDtoSaida> atualizar(BairroDtoEntradaAtualizar bairroDtoEntrada) {

        municipioVerificado = municipioRepository.findById(bairroDtoEntrada.getCodigoMunicipio()).get();

        return bairroRepository.findById(codigoBairro)
                .map(bairroDoDatabase -> {
                    bairroDoDatabase.setNome(bairroDtoEntrada.getNome());
                    bairroDoDatabase.setMunicipio(municipioVerificado);
                    bairroDoDatabase.setStatus(bairroDtoEntrada.getStatus());
                    listaDeBairrosSalvos = bairroRepository.findAll();
                    listaDeBairrosDeSaida = listaDeBairrosSalvos.stream().map(BairroDtoSaida::new).collect(Collectors.toList());
                    return ResponseEntity.ok().body(listaDeBairrosDeSaida);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

        private void atualizarBairro() {
            bairroSalvo.setNome(bairroDeEntrada.getNome());
            bairroSalvo.setMunicipio(municipioVerificado);
            bairroSalvo.setStatus(bairroDeEntrada.getStatus());
        }

    // ---------- Deletar
    public List<BairroDtoSaida> deletar(Long codigoBairro) {

        var bairroDoDatabase = bairroRepository.findById(codigoBairro);
        if(!bairroDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);

        bairroRepository.deleteById(codigoBairro);
        return ResponseEntity.ok().body(MensagemPadrao.ID_DELETADO);
    }
}
