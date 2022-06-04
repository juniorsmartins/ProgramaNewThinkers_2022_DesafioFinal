package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntradaAtualizar;
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
    private ExampleMatcher matcher;
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
        converterListaDeBairrosParaListaDeBairrosDeSaida();
        return listaDeBairrosDeSaida;
    }

        private void converterBairroDtoEntradaParaBairro() {
            bairroSalvo = modelMapper.map(bairroDeEntrada, Bairro.class);
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

        criarExampleMatcherParaConfigurarFiltros();
        // Example - pega campos populados para criar filtros
        Example example = Example.of(modelMapper.map(filtros, Bairro.class), matcher);

        if(filtros.getCodigoBairro() != null) {
            var bairroDoDatabase = bairroRepository.findOne(example);
            if(!bairroDoDatabase.isPresent())
                throw new RecursoNaoEncontradoException(MensagemPadrao.CODIGOBAIRRO_NAO_ENCONTRADO);
            bairroSalvo = (Bairro) bairroDoDatabase.get();

            converterBairroParaBairroDtoSaida();
            return ResponseEntity.ok().body(bairroDeSaida);
        }

        listaDeBairrosSalvos = bairroRepository.findAll(example);
        converterListaDeBairrosParaListaDeBairrosDeSaida();
        return ResponseEntity.ok().body(listaDeBairrosDeSaida);
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
    @Override
    public List<BairroDtoSaida> atualizar(BairroDtoEntradaAtualizar bairroDtoEntrada) {
        bairroDeEntrada = modelMapper.map(bairroDtoEntrada, BairroDtoEntrada.class);

        // Tratamento de regras de negócio
        listaDeRegrasDeAtualizar.forEach(regra -> regra.validar(bairroDtoEntrada));

        return bairroRepository.findById(bairroDtoEntrada.getCodigoBairro())
                .map(bairro -> {
                    disponibilizarMunicipioVerificado();
                    atualizarBairro();
                    buscarTodosBairrosParaRetornar();
                    converterListaDeBairrosParaListaDeBairrosDeSaida();
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
                    bairroRepository.delete(bairro);
                    buscarTodosBairrosParaRetornar();
                    converterListaDeBairrosParaListaDeBairrosDeSaida();
                    return listaDeBairrosDeSaida;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao.CODIGOBAIRRO_NAO_ENCONTRADO));
    }
}
