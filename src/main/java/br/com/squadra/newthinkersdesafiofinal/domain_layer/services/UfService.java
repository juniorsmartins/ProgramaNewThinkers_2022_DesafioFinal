package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.UfDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.ValidacaoException;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.uf.ValidacoesCadastrarUf;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Uf;
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
public final class UfService {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    // ---------- Injetados automaticamente
    @Autowired
    private UfRepository ufRepository;
    @Autowired
    private ModelMapper modelMapper;
    // ---------- Padrão de Projeto
    @Autowired
    private List<ValidacoesCadastrarUf> listaDeValidacoesDeUf;
    // ---------- Atributos p/estilo pessoal de Clean Code
    private UfDtoEntrada ufDeEntrada;
    private Uf ufSalva;
    private UfDtoSaida ufDeSaida;
    private List<Uf> listaDeUfsSalvas;
    private List<UfDtoSaida> listaDeUfsDeSaida;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    public ResponseEntity<?> cadastrar(UfDtoEntrada ufDtoEntrada, UriComponentsBuilder uriComponentsBuilder) {
        ufDeEntrada = ufDtoEntrada;

        // Design Pattern comportamental
        try{
            listaDeValidacoesDeUf.forEach(regraDeNegocio -> regraDeNegocio.validar(ufDeEntrada));
        }catch(ValidacaoException validacaoException){
            return ResponseEntity.badRequest().body(validacaoException.getMessage());
        }

        converterUfDtoEntradaParaUf();
        setarStatusAtivado();
        salvarUf();
        converterUfParaUfDtoSaida();

        URI uri = uriComponentsBuilder.path("/ufs/{codigoPessoa}").buildAndExpand(ufDeSaida.getCodigoUf()).toUri();
        return ResponseEntity.created(uri).body(ufDeSaida);
    }

        private void converterUfDtoEntradaParaUf() {
            ufSalva = modelMapper.map(ufDeEntrada, Uf.class);
        }

        private void setarStatusAtivado() {
            ufSalva.setStatus(1);
        }

        private void salvarUf() {
            ufSalva = ufRepository.saveAndFlush(ufSalva);
        }

        private void converterUfParaUfDtoSaida() {
            ufDeSaida = modelMapper.map(ufSalva, UfDtoSaida.class);
        }

    // ---------- Listar
    public ResponseEntity<?> listar() {

        buscarTodasAsUfsDoDatabase();
        converterListaDeUfsParaListaDeUfsDeSaida();

        return ResponseEntity.ok().body(listaDeUfsDeSaida);
    }

        private void buscarTodasAsUfsDoDatabase() {
            listaDeUfsSalvas = ufRepository.findAll();
        }

        private void converterListaDeUfsParaListaDeUfsDeSaida() {
            listaDeUfsDeSaida = listaDeUfsSalvas.stream().map(UfDtoSaida::new).collect(Collectors.toList());
        }

    // ---------- Consultar
    public ResponseEntity<?> consultar(Long codigoUf) {

        var ufDoDatabase = ufRepository.findById(codigoUf);
        if(!ufDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        ufSalva = ufDoDatabase.get();

        converterUfParaUfDtoSaida();

        return ResponseEntity.ok().body(ufDeSaida);
    }

    // ---------- Atualizar
    public ResponseEntity<?> atualizar(Long codigoUf, UfDtoEntrada ufDtoEntrada) {
        ufDeEntrada = ufDtoEntrada;

        var ufDoDatabase = ufRepository.findById(codigoUf);
        if(!ufDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        ufSalva = ufDoDatabase.get();

        atualizarUf();
        converterUfParaUfDtoSaida();

        return ResponseEntity.ok().body(ufDeSaida);
    }

        private void atualizarUf() {
            ufSalva.setNome(ufDeEntrada.getNome());
            ufSalva.setSigla(ufDeEntrada.getSigla());
        }

    // ---------- Deletar
    public ResponseEntity<?> deletar(Long codigoUf) {

        var ufDoDatabase = ufRepository.findById(codigoUf);
        if(!ufDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);

        ufRepository.deleteById(codigoUf);
        return ResponseEntity.ok().body(MensagemPadrao.ID_DELETADO);
    }
}
