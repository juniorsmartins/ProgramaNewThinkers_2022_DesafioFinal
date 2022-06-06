package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.MunicipioDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasMunicipioAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasMunicipioCadastrar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RecursoNaoEncontradoException;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.portas.MunicipioService;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Municipio;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Uf;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.MunicipioRepository;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.UfRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public final class MunicipioServiceImpl implements MunicipioService {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    // ---------- Atributos Injetados automaticamente
    @Autowired
    private MunicipioRepository municipioRepository;
    @Autowired
    private UfRepository ufRepository;
    @Autowired
    private ModelMapper modelMapper;
    // ---------- Atributos p/estilo pessoal de Clean Code
    private MunicipioDtoEntrada municipioDeEntrada;
    private Municipio municipioSalvo;
    private MunicipioDtoSaida municipioDeSaida;
    private Uf ufVerificada;
    private List<Municipio> listaDeMunicipiosSalvos;
    private List<MunicipioDtoSaida> listaDeMunicipiosDeSaida;
    private Example exampleFiltro;
    // ---------- Regras de Negócio
    @Autowired
    private List<IRegrasMunicipioCadastrar> listaDeRegrasDeCadastrar;
    @Autowired
    private List<IRegrasMunicipioAtualizar> listaDeRegrasDeAtualizar;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    @Override
    public List<MunicipioDtoSaida> cadastrar(MunicipioDtoEntrada municipioDtoEntrada) {
        municipioDeEntrada = municipioDtoEntrada;

        // Tratamento de regras de negócio
        listaDeRegrasDeCadastrar.forEach(regra -> regra.validar(municipioDeEntrada));

        converterMunicipioDtoEntradaParaMunicipio();
        salvarMunicipio();
        buscarTodosMunicipiosParaRetornar();
        converterListaDeMunicipiosParaListaDeMunicipiosDeSaida();
        return listaDeMunicipiosDeSaida;
    }

        private void converterMunicipioDtoEntradaParaMunicipio() {
            municipioSalvo = modelMapper.map(municipioDeEntrada, Municipio.class);
        }

        private void salvarMunicipio() {
            municipioRepository.saveAndFlush(municipioSalvo);
        }

        private void buscarTodosMunicipiosParaRetornar() {
            listaDeMunicipiosSalvos = municipioRepository.findAll()
                    .stream()
                    .sorted((m1, m2) -> m2.getCodigoMunicipio().compareTo(m1.getCodigoMunicipio()))
                    .toList();
        }

        private void converterListaDeMunicipiosParaListaDeMunicipiosDeSaida() {
            listaDeMunicipiosDeSaida = listaDeMunicipiosSalvos
                    .stream()
                    .map(MunicipioDtoSaida::new)
                    .toList();
        }

    // ---------- Listar
    @Override
    public ResponseEntity<?> listar(MunicipioDtoEntrada municipioDtoEntrada) {
        municipioDeEntrada = municipioDtoEntrada;

        criarExampleConfiguradoPorExampleMatcher();
        listaDeMunicipiosSalvos = municipioRepository.findAll(exampleFiltro);

        if(listaDeMunicipiosSalvos.isEmpty())
            return ResponseEntity.ok().body(new ArrayList<>());

        if(municipioDeEntrada.getCodigoMunicipio() != null) {
            converterListaDeMunicipiosParaListaDeMunicipiosDeSaida();
            return ResponseEntity.ok().body(listaDeMunicipiosDeSaida.get(0));
        }

        if(municipioDeEntrada.getCodigoUF() != null || municipioDeEntrada.getStatus() != null
                || municipioDeEntrada.getNome() != null) {
            converterListaDeMunicipiosParaListaDeMunicipiosDeSaida();
            return ResponseEntity.ok().body(listaDeMunicipiosDeSaida);
        }

        buscarTodosMunicipiosParaRetornar();
        converterListaDeMunicipiosParaListaDeMunicipiosDeSaida();
        return ResponseEntity.ok().body(listaDeMunicipiosDeSaida);
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
            exampleFiltro = Example.of(modelMapper.map(municipioDeEntrada, Municipio.class), matcher);
        }

        private void converterMunicipioParaMunicipioDtoSaida() {
            municipioDeSaida = modelMapper.map(municipioSalvo, MunicipioDtoSaida.class);
        }

    // ---------- Consultar
    @Override
    public MunicipioDtoSaida consultar(Long codigoMunicipio) {

        return municipioRepository.findById(codigoMunicipio)
                .map(municipioDoDatabase -> {
                    municipioSalvo = municipioDoDatabase;
                    converterMunicipioParaMunicipioDtoSaida();
                    return municipioDeSaida;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao.CODIGOMUNICIPIO_NAO_ENCONTRADO));
    }

    // ---------- Atualizar
    @Override
    public List<MunicipioDtoSaida> atualizar(MunicipioDtoEntradaAtualizar municipioDtoEntrada) {
        municipioDeEntrada = modelMapper.map(municipioDtoEntrada, MunicipioDtoEntrada.class);

        // Tratamento de regras de negócio
        listaDeRegrasDeAtualizar.forEach(regra -> regra.validar(municipioDtoEntrada));

        return municipioRepository.findById(municipioDtoEntrada.getCodigoMunicipio())
                .map(municipio -> {
                    municipioSalvo = municipio;
                    disponibilizarUfVerificada();
                    atualizarMunicipio();
                    buscarTodosMunicipiosParaRetornar();
                    converterListaDeMunicipiosParaListaDeMunicipiosDeSaida();
                    return listaDeMunicipiosDeSaida;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao.CODIGOMUNICIPIO_NAO_ENCONTRADO));
    }

        private void disponibilizarUfVerificada() {
            ufVerificada = ufRepository.findById(municipioDeEntrada.getCodigoUF()).get();
        }

        private void atualizarMunicipio() {
            municipioSalvo.setUf(ufVerificada);
            municipioSalvo.setNome(municipioDeEntrada.getNome());
            municipioSalvo.setStatus(municipioDeEntrada.getStatus());
        }

    // ---------- Deletar
    @Override
    public List<MunicipioDtoSaida> deletar(Long codigoMunicipio) {

        return municipioRepository.findById(codigoMunicipio)
                .map(municipio -> {
                    municipioRepository.delete(municipio);
                    buscarTodosMunicipiosParaRetornar();
                    converterListaDeMunicipiosParaListaDeMunicipiosDeSaida();
                    return listaDeMunicipiosDeSaida;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao.CODIGOMUNICIPIO_NAO_ENCONTRADO));
    }
}
