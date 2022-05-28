package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.MunicipioDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities.Municipio;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.MunicipioRepository;
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
    private ModelMapper modelMapper;
    // ---------- Atributos p/estilo pessoal de Clean Code
    private MunicipioDtoEntrada municipioDeEntrada;
    private Municipio municipioSalvo;
    private MunicipioDtoSaida municipioDeSaida;
    List<Municipio> listaDeMunicipiosSalvos;
    List<MunicipioDtoSaida> listaDeMunicipiosDeSaida;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    public ResponseEntity<?> cadastrar(MunicipioDtoEntrada municipioDtoEntrada, UriComponentsBuilder uriComponentsBuilder) {
        municipioDeEntrada = municipioDtoEntrada;

        converterMunicipioDtoEntradaParaMunicipio();
        setarStatusAtivado();
        salvarMunicipio();
        converterMunicipioParaMunicipioDtoSaida();

        URI uri = uriComponentsBuilder.path("/municipios/{codigoMunicipio}").buildAndExpand(municipioDeSaida.getCodigoMunicipio()).toUri();
        return ResponseEntity.created(uri).body(municipioDeSaida);
    }

        private void converterMunicipioDtoEntradaParaMunicipio() {
            municipioSalvo = modelMapper.map(municipioDeEntrada, Municipio.class);
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
            return ResponseEntity.badRequest().body("Chave Identificadora não encontrada!");
        municipioSalvo = municipioDoDatabase.get();

        converterMunicipioParaMunicipioDtoSaida();

        return ResponseEntity.ok().body(municipioDeSaida);
    }

    // ---------- Atualizar
    public ResponseEntity<?> atualizar(Long codigoMunicipio, MunicipioDtoEntrada municipioDtoEntrada) {
        municipioDeEntrada = municipioDtoEntrada;

        var municipioDoDatabase = municipioRepository.findById(codigoMunicipio);
        if(!municipioDoDatabase.isPresent())
            return ResponseEntity.badRequest().body("Chave Identificadora não encontrada!");
        municipioSalvo = municipioDoDatabase.get();

        atualizarMunicipio();
        converterMunicipioParaMunicipioDtoSaida();

        return ResponseEntity.ok().body(municipioDeSaida);
    }

        private void atualizarMunicipio() {
            municipioSalvo.setNome(municipioDeEntrada.getNome());
        }

    // ---------- Deletar
    public ResponseEntity<?> deletar(Long codigoMunicipio) {

        var municipioDoDatabase = municipioRepository.findById(codigoMunicipio);
        if(!municipioDoDatabase.isPresent())
            return ResponseEntity.badRequest().body("Chave Identificadora não encontrada!");

        municipioRepository.deleteById(codigoMunicipio);
        return ResponseEntity.ok().body("Deletado Município com Chave Identificadora: " + codigoMunicipio + ".");
    }
}
