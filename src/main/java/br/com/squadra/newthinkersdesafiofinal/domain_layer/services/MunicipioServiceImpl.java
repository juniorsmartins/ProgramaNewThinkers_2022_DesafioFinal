package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.MunicipioDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasMunicipioCadastrar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
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

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    @Override
    public List<MunicipioDtoSaida> cadastrar(MunicipioDtoEntrada municipioDtoEntrada) {
        municipioDeEntrada = municipioDtoEntrada;

        // Tratamento de regras de negócio
        listaDeRegrasDeCadastro.forEach(regra -> regra.validar(municipioDeEntrada));

        ufVerificada = ufRepository.findById(municipioDtoEntrada.getCodigoUF()).get();

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

        if(filtros.getCodigoMunicipio() != null || filtros.getNome() != null) {
            var municipioFiltro = modelMapper.map(filtros, Municipio.class);

            // ExampleMatcher - permite configurar condições para serem aplicadas nos filtros
            ExampleMatcher matcher = ExampleMatcher
                    .matching()
                    .withIgnoreCase() // Ignore caixa alta ou baixa - quando String
                    .withIgnoreNullValues()
                    .withStringMatcher(ExampleMatcher
                            .StringMatcher.CONTAINING); // permite encontrar palavras tipo Like com Containing

            // Example - pega campos populados para criar filtros
            Example example = Example.of(municipioFiltro, matcher);

            var municipioFiltrado = municipioRepository.findOne(example);
            if(!municipioFiltrado.isPresent())
                return ResponseEntity.notFound().build();
            municipioSalvo = (Municipio) municipioFiltrado.get();

            converterMunicipioParaMunicipioDtoSaida();
            return ResponseEntity.ok().body(municipioSalvo);
        }

        if(filtros.getCodigoUF() != null) {
            var listaDeMunicipiosPorCodigoUF = municipioRepository.findByCodigoUF(filtros.getCodigoUF());
            if(listaDeMunicipiosPorCodigoUF.isEmpty())
                return ResponseEntity.notFound().build();
            listaDeMunicipiosSalvos = listaDeMunicipiosPorCodigoUF;

            converterListaDeMunicipiosParaListaDeMunicipiosDeSaida();
            return ResponseEntity.ok().body(listaDeMunicipiosDeSaida);
        }

        buscarTodosMunicipiosParaRetornar();
        converterListaDeMunicipiosParaListaDeMunicipiosDeSaida();
        return ResponseEntity.ok().body(listaDeMunicipiosDeSaida);
    }

    // ---------- Consultar
    public ResponseEntity<?> consultar(Long codigoMunicipio) {

        var municipioDoDatabase = municipioRepository.findById(codigoMunicipio);
        if(!municipioDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        municipioSalvo = municipioDoDatabase.get();

        converterMunicipioParaMunicipioDtoSaida();

        return ResponseEntity.ok().body(municipioDeSaida);
    }

        private void converterMunicipioParaMunicipioDtoSaida() {
            municipioDeSaida = modelMapper.map(municipioSalvo, MunicipioDtoSaida.class);
        }

    // ---------- Atualizar
    public ResponseEntity<?> atualizar(Long codigoMunicipio, MunicipioDtoEntrada municipioDtoEntrada) {
        municipioDeEntrada = municipioDtoEntrada;

        var municipioDoDatabase = municipioRepository.findById(codigoMunicipio);
        if(!municipioDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        municipioSalvo = municipioDoDatabase.get();

        var ufDoDatabase = ufRepository.findById(municipioDeEntrada.getCodigoUF());
        if(!ufDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        ufVerificada = ufDoDatabase.get();

        atualizarMunicipio();
        converterMunicipioParaMunicipioDtoSaida();

        return ResponseEntity.ok().body(municipioDeSaida);
    }

        private void atualizarMunicipio() {
            municipioSalvo.setNome(municipioDeEntrada.getNome());
            municipioSalvo.setUf(ufVerificada);
        }

    // ---------- Deletar
    public ResponseEntity<?> deletar(Long codigoMunicipio) {

        var municipioDoDatabase = municipioRepository.findById(codigoMunicipio);
        if(!municipioDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);

        municipioRepository.deleteById(codigoMunicipio);
        return ResponseEntity.ok().body(MensagemPadrao.ID_DELETADO);
    }
}
