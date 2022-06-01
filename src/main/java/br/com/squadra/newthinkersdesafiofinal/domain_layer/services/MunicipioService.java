package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.MunicipioDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.MensagemPadrao;
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
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public final class MunicipioService {

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
    List<Municipio> listaDeMunicipiosSalvos;
    List<MunicipioDtoSaida> listaDeMunicipiosDeSaida;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    public ResponseEntity<?> cadastrar(MunicipioDtoEntrada municipioDtoEntrada, UriComponentsBuilder uriComponentsBuilder) {
        municipioDeEntrada = municipioDtoEntrada;

        var ufDoDatabase = ufRepository.findById(municipioDeEntrada.getCodigoUf());
        if(!ufDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        ufVerificada = ufDoDatabase.get();

        converterMunicipioDtoEntradaParaMunicipio();
        setarStatusAtivado();
        salvarMunicipio();
        converterMunicipioParaMunicipioDtoSaida();

        URI uri = uriComponentsBuilder.path("/municipios/{codigoMunicipio}").buildAndExpand(municipioDeSaida.getCodigoMunicipio()).toUri();
        return ResponseEntity.created(uri).body(municipioDeSaida);
    }

        private void converterMunicipioDtoEntradaParaMunicipio() {
            municipioSalvo = new Municipio();
            municipioSalvo.setNome(municipioDeEntrada.getNome());
            municipioSalvo.setUf(ufVerificada);
        }

        private void setarStatusAtivado() {
            municipioSalvo.setStatus(1);
        }

        private void salvarMunicipio() {
            municipioSalvo = municipioRepository.saveAndFlush(municipioSalvo);
        }

        private void converterMunicipioParaMunicipioDtoSaida() {
            municipioDeSaida = modelMapper.map(municipioSalvo, MunicipioDtoSaida.class);
        }

    // ---------- Listar
    public ResponseEntity<?> listar(MunicipioDtoEntrada filtros) {
        var municipioFiltro = modelMapper.map(filtros, Municipio.class);

        // ExampleMatcher - permite configurar condições para serem aplicadas nos filtros
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase() // Ignore caixa alta ou baixa - quando String
                .withStringMatcher(ExampleMatcher
                        .StringMatcher.CONTAINING); // permite encontrar palavras tipo Like com Containing

        // Example - pega campos populados para criar filtros
        Example example = Example.of(municipioFiltro, matcher);

        listaDeMunicipiosSalvos = municipioRepository.findAll(example);
        converterListaDeMunicipiosParaListaDeMunicipiosDeSaida();

        return ResponseEntity.ok().body(listaDeMunicipiosDeSaida);
    }

    private void converterListaDeMunicipiosParaListaDeMunicipiosDeSaida() {
        listaDeMunicipiosDeSaida = listaDeMunicipiosSalvos.stream().map(MunicipioDtoSaida::new).collect(Collectors.toList());
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

    // ---------- Atualizar
    public ResponseEntity<?> atualizar(Long codigoMunicipio, MunicipioDtoEntrada municipioDtoEntrada) {
        municipioDeEntrada = municipioDtoEntrada;

        var municipioDoDatabase = municipioRepository.findById(codigoMunicipio);
        if(!municipioDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        municipioSalvo = municipioDoDatabase.get();

        var ufDoDatabase = ufRepository.findById(municipioDeEntrada.getCodigoUf());
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
