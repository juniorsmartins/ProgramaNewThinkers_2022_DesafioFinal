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
import java.util.List;
import java.util.stream.Collectors;

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
    // ---------- Regras de Negócio
    @Autowired
    private List<IRegrasMunicipioCadastrar> listaDeRegrasDeCadastro;
    @Autowired
    private List<IRegrasMunicipioAtualizar> listaDeRegrasDeAtualizar;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    @Override
    public List<MunicipioDtoSaida> cadastrar(MunicipioDtoEntrada municipioDtoEntrada) {
        municipioDeEntrada = municipioDtoEntrada;

        // Tratamento de regras de negócio
        listaDeRegrasDeCadastro.forEach(regra -> regra.validar(municipioDeEntrada));

        ufVerificada = ufRepository.findById(municipioDeEntrada.getCodigoUF()).get();

        converterMunicipioDtoEntradaParaMunicipio();
        salvarMunicipio();
        buscarTodosMunicipiosParaRetornar();
        converterMunicipioParaMunicipioDtoSaida();
        return listaDeMunicipiosDeSaida;
    }

        private void converterMunicipioDtoEntradaParaMunicipio() {
            municipioSalvo = new Municipio();
            municipioSalvo.setUf(ufVerificada);
            municipioSalvo.setNome(municipioDeEntrada.getNome());
            municipioSalvo.setStatus(municipioDeEntrada.getStatus());
        }

        private void salvarMunicipio() {
            municipioSalvo = municipioRepository.saveAndFlush(municipioSalvo);
        }

        private void buscarTodosMunicipiosParaRetornar() {
            listaDeMunicipiosSalvos = municipioRepository.findAll();
        }

        private void converterListaDeMunicipiosParaListaDeMunicipiosDeSaida() {
            listaDeMunicipiosDeSaida = listaDeMunicipiosSalvos.stream()
                    .map(MunicipioDtoSaida::new).collect(Collectors.toList());
        }

    // ---------- Listar
    @Override
    public ResponseEntity<?> listar(MunicipioDtoEntrada filtros) {

        if(filtros.getCodigoMunicipio() != null)
            return municipioRepository.findById(filtros.getCodigoMunicipio())
                    .map(municipio -> {
                        municipioSalvo = municipio;
                        converterMunicipioParaMunicipioDtoSaida();
                        return ResponseEntity.ok().body(municipioDeSaida);
                    }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao.CODIGOMUNICIPIO_NAO_ENCONTRADO));

        if(filtros.getNome() != null)
            return municipioRepository.findByNomeLike("%" + filtros.getNome() + "%")
                    .map(municipio -> {
                        municipioSalvo = municipio;
                        converterMunicipioParaMunicipioDtoSaida();
                        return ResponseEntity.ok().body(municipioDeSaida);
                    }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao.RECURSO_NAO_ENCONTRADO));

        if(filtros.getCodigoUF() != null) {
            var ufFiltro = modelMapper.map(filtros, Municipio.class);

            // ExampleMatcher - permite configurar condições para serem aplicadas nos filtros
            ExampleMatcher matcher = ExampleMatcher
                    .matching()
                    .withIgnoreCase() // Ignore caixa alta ou baixa - quando String
                    .withIgnoreNullValues()
                    .withStringMatcher(ExampleMatcher
                            .StringMatcher.CONTAINING); // permite encontrar palavras tipo Like com Containing

            // Example - pega campos populados para criar filtros
            Example example = Example.of(ufFiltro, matcher);

            var listaDeMunicipioDoDatabase = municipioRepository.findAll(example);
            if(listaDeMunicipioDoDatabase.isEmpty())
                throw new RecursoNaoEncontradoException(MensagemPadrao.RECURSO_NAO_ENCONTRADO);
            listaDeMunicipiosSalvos = listaDeMunicipioDoDatabase;

            converterListaDeMunicipiosParaListaDeMunicipiosDeSaida();
            return ResponseEntity.ok().body(listaDeMunicipiosDeSaida);
        }

        buscarTodosMunicipiosParaRetornar();
        converterListaDeMunicipiosParaListaDeMunicipiosDeSaida();
        return ResponseEntity.ok().body(listaDeMunicipiosDeSaida);
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

        // Tratamento de regras de negócio
        listaDeRegrasDeAtualizar.forEach(regra -> regra.validar(municipioDtoEntrada));

        return municipioRepository.findById(municipioDtoEntrada.getCodigoMunicipio())
                .map(municipio -> {
                    ufVerificada = ufRepository.findById(municipioDtoEntrada.getCodigoUF()).get();
                    municipio.setUf(ufVerificada);
                    municipio.setNome(municipioDtoEntrada.getNome());
                    municipio.setStatus(municipioDtoEntrada.getStatus());
                    municipioRepository.saveAndFlush(municipio);
                    buscarTodosMunicipiosParaRetornar();
                    converterListaDeMunicipiosParaListaDeMunicipiosDeSaida();
                    return listaDeMunicipiosDeSaida;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao.CODIGOMUNICIPIO_NAO_ENCONTRADO));
    }

        private void atualizarMunicipio() {
            municipioSalvo.setNome(municipioDeEntrada.getNome());
            municipioSalvo.setUf(ufVerificada);
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
