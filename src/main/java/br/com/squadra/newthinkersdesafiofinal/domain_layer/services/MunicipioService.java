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
        setarUfVerificada();
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

        private void setarUfVerificada() {
            municipioSalvo.setUf(ufVerificada);
        }

        private void salvarMunicipio() {
            municipioSalvo = municipioRepository.saveAndFlush(municipioSalvo);
        }

        private void converterMunicipioParaMunicipioDtoSaida() {
            municipioDeSaida = modelMapper.map(municipioSalvo, MunicipioDtoSaida.class);
        }

    // ---------- Listar
    public ResponseEntity<?> listar() {

        buscarTodosOsMunicipiosDoDatabase();
        converterListaDeMunicipiosParaListaDeMunicipiosDeSaida();

        return ResponseEntity.ok().body(listaDeMunicipiosDeSaida);
    }

    private void buscarTodosOsMunicipiosDoDatabase() {
        listaDeMunicipiosSalvos = municipioRepository.findAll();
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
