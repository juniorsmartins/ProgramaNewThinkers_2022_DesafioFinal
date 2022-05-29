package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.BairroDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Bairro;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.BairroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public final class BairroService {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    // ---------- Atributos Injetados automaticamente
    @Autowired
    private BairroRepository bairroRepository;
    @Autowired
    private ModelMapper modelMapper;
    // ---------- Atributos p/estilo pessoal de Clean Code
    private BairroDtoEntrada bairroDeEntrada;
    private Bairro bairroSalvo;
    private BairroDtoSaida bairroDeSaida;
    private List<Bairro> listaDeBairrosSalvos;
    private List<BairroDtoSaida> listaDeBairrosDeSaida;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    public ResponseEntity<?> cadastrar(BairroDtoEntrada bairroDtoEntrada, UriComponentsBuilder uriComponentsBuilder) {
        bairroDeEntrada = bairroDtoEntrada;

        converterBairroDtoEntradaParaBairro();
        setarStatusAtivado();
        salvarBairro();
        converterBairroParaBairroDtoSaida();

        URI uri = uriComponentsBuilder.path("/bairros/{codigoBairro}").buildAndExpand(bairroDeSaida.getCodigoBairro()).toUri();
        return ResponseEntity.created(uri).body(bairroDeSaida);
    }

        private void converterBairroDtoEntradaParaBairro() {
            bairroSalvo = modelMapper.map(bairroDeEntrada, Bairro.class);
        }

        private void setarStatusAtivado() {
            bairroSalvo.setStatus(1);
        }

        private void salvarBairro() {
            bairroSalvo = bairroRepository.saveAndFlush(bairroSalvo);
        }

        private void converterBairroParaBairroDtoSaida() {
            bairroDeSaida = modelMapper.map(bairroSalvo, BairroDtoSaida.class);
        }

    // ---------- Listar
    public ResponseEntity<?> listar() {

        buscarTodosOsBairrosDoDatabase();
        converterListaDeBairrosParaListaDeBairrosDeSaida();

        return ResponseEntity.ok().body(listaDeBairrosDeSaida);
    }

        private void buscarTodosOsBairrosDoDatabase() {
            listaDeBairrosSalvos = bairroRepository.findAll();
        }

        private void converterListaDeBairrosParaListaDeBairrosDeSaida() {
            listaDeBairrosDeSaida = listaDeBairrosSalvos.stream().map(BairroDtoSaida::new).collect(Collectors.toList());
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

    // ---------- Atualizar
    public ResponseEntity<?> atualizar(Long codigoBairro, BairroDtoEntrada bairroDtoEntrada) {
        bairroDeEntrada = bairroDtoEntrada;

        var bairroDoDatabase = bairroRepository.findById(codigoBairro);
        if(!bairroDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        bairroSalvo = bairroDoDatabase.get();

        atualizarBairro();
        converterBairroParaBairroDtoSaida();

        return ResponseEntity.ok().body(bairroDeSaida);
    }

        private void atualizarBairro() {
            bairroSalvo.setNome(bairroDeEntrada.getNome());
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
