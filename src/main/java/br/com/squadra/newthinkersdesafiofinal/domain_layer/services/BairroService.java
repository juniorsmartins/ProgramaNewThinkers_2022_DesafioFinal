package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.BairroDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.ValidacaoException;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.bairro.ValidacoesAtualizarBairro;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.bairro.ValidacoesCadastrarBairro;
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
public final class BairroService {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    // ---------- Atributos Injetados automaticamente
    @Autowired
    private BairroRepository bairroRepository;
    @Autowired
    private MunicipioRepository municipioRepository;
    @Autowired
    private ModelMapper modelMapper;
    // ---------- Padrão de Projeto
    @Autowired
    private List<ValidacoesCadastrarBairro> listaCadastrarDeValidacoesDeBairro;
    @Autowired
    private List<ValidacoesAtualizarBairro> listaAtualizarDeValidacoesDeBairro;
    // ---------- Atributos p/estilo pessoal de Clean Code
    private BairroDtoEntrada bairroDeEntrada;
    private Bairro bairroSalvo;
    private BairroDtoSaida bairroDeSaida;
    private Municipio municipioVerificado;
    private List<Bairro> listaDeBairrosSalvos;
    private List<BairroDtoSaida> listaDeBairrosDeSaida;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    public ResponseEntity<?> cadastrar(BairroDtoEntrada bairroDtoEntrada) {
        bairroDeEntrada = bairroDtoEntrada;

        // Design Pattern comportamental
        try{
            listaCadastrarDeValidacoesDeBairro.forEach(regraDeNegocio -> regraDeNegocio.validar(bairroDeEntrada));
        }catch(ValidacaoException validacaoException){
            return ResponseEntity.badRequest().body(validacaoException.getMessage());
        }

        municipioVerificado = municipioRepository.findById(bairroDeEntrada.getCodigoMunicipio()).get();

        converterBairroDtoEntradaParaBairro();
        salvarBairro();
        buscarTodosBairrosParaRetornar();
        converterListaDeBairrosParaListaDeBairrosDeSaida();

        return ResponseEntity.ok().body(listaDeBairrosDeSaida);
    }

        private void converterBairroDtoEntradaParaBairro() {
            bairroSalvo = new Bairro();
            bairroSalvo.setNome(bairroDeEntrada.getNome());
            bairroSalvo.setStatus(bairroDeEntrada.getStatus());
            bairroSalvo.setMunicipio(municipioVerificado);
        }

        private void salvarBairro() {
            bairroSalvo = bairroRepository.saveAndFlush(bairroSalvo);
        }

        private void buscarTodosBairrosParaRetornar() {
            listaDeBairrosSalvos = bairroRepository.findAll();
        }

        private void converterListaDeBairrosParaListaDeBairrosDeSaida() {
            listaDeBairrosDeSaida = listaDeBairrosSalvos.stream().map(BairroDtoSaida::new).collect(Collectors.toList());
        }

    // ---------- Listar
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

    // ---------- Consultar
    public ResponseEntity<?> consultar(Long codigoBairro) {
        var bairroDoDatabase = bairroRepository.findById(codigoBairro);
        if(!bairroDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        bairroSalvo = bairroDoDatabase.get();

        converterBairroParaBairroDtoSaida();

        return ResponseEntity.ok().body(bairroDeSaida);
    }

        private void converterBairroParaBairroDtoSaida() {
            bairroDeSaida = modelMapper.map(bairroSalvo, BairroDtoSaida.class);
        }

    // ---------- Atualizar
    public ResponseEntity<?> atualizar(Long codigoBairro, BairroDtoEntrada bairroDtoEntrada) {

        // Design Pattern comportamental
        try{
            listaAtualizarDeValidacoesDeBairro.forEach(regraDeNegocio -> regraDeNegocio.validar(codigoBairro, bairroDtoEntrada));
        }catch(ValidacaoException validacaoException){
            return ResponseEntity.badRequest().body(validacaoException.getMessage());
        }

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
    public ResponseEntity<?> deletar(Long codigoBairro) {

        var bairroDoDatabase = bairroRepository.findById(codigoBairro);
        if(!bairroDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);

        bairroRepository.deleteById(codigoBairro);
        return ResponseEntity.ok().body(MensagemPadrao.ID_DELETADO);
    }
}
