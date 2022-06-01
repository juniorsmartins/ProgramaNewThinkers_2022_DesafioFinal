package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.UfDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.ValidacaoException;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.uf.ValidacoesAtualizarUf;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.uf.ValidacoesCadastrarUf;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Uf;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.UfRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
    private List<ValidacoesCadastrarUf> listaCadastrarDeValidacoesDeUf;
    @Autowired
    private List<ValidacoesAtualizarUf> listaAtualizarDeValidacoesDeUf;
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
            listaCadastrarDeValidacoesDeUf.forEach(regraDeNegocio -> regraDeNegocio.validar(ufDeEntrada));
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
    public ResponseEntity<?> listar(UfDtoEntrada filtro) {
        var ufFiltro = modelMapper.map(filtro, Uf.class);

        // ExampleMatcher - permite configurar condições para serem aplicadas nos filtros
        ExampleMatcher matcher = ExampleMatcher
                                        .matching()
                                        .withIgnoreCase() // Ignore caixa alta ou baixa - quando String
                                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // permite encontrar palavras tipo Like com Containing

        // Example - pega campos populados para criar filtros
        Example example = Example.of(ufFiltro, matcher);

        listaDeUfsSalvas = ufRepository.findAll(example);
        converterListaDeUfsParaListaDeUfsDeSaida();

        return ResponseEntity.ok().body(listaDeUfsDeSaida);
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

        try{
            listaAtualizarDeValidacoesDeUf.forEach(regraDeNegocio -> regraDeNegocio.validar(codigoUf, ufDtoEntrada));
        }catch(ValidacaoException validacaoException){
            return ResponseEntity.badRequest().body(validacaoException.getMessage());
        }

        return ufRepository.findById(codigoUf)
                .map( ufDoDatabase -> {
                    ufDoDatabase.setSigla(ufDtoEntrada.getSigla());
                    ufDoDatabase.setNome(ufDtoEntrada.getNome());
                    ufDeSaida = modelMapper.map(ufDoDatabase, UfDtoSaida.class);
                    return ResponseEntity.ok().body(ufDeSaida);
                }).orElseGet(() -> ResponseEntity.notFound().build());
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
