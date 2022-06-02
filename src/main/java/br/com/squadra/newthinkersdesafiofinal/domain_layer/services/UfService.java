package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.UfDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.ErroPadronizado;
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
    public ResponseEntity<?> cadastrar(UfDtoEntrada ufDtoEntrada) {
        ufDeEntrada = ufDtoEntrada;

        // Design Pattern comportamental
        try{
            listaCadastrarDeValidacoesDeUf.forEach(regraDeNegocio -> regraDeNegocio.validar(ufDeEntrada));
        }catch(ValidacaoException validacaoException){
            return ResponseEntity.badRequest().body(validacaoException.getMessage());
        }

        converterUfDtoEntradaParaUf();
        salvarUf();
        buscarTodasUfsParaRetornar();
        converterListaDeUfsParaListaDeUfsDeSaida();

        return ResponseEntity.ok().body(listaDeUfsDeSaida);
    }

        private void converterUfDtoEntradaParaUf() {
            ufSalva = modelMapper.map(ufDeEntrada, Uf.class);
        }

        private void salvarUf() {
            ufSalva = ufRepository.saveAndFlush(ufSalva);
        }

        private void buscarTodasUfsParaRetornar() {
            listaDeUfsSalvas = ufRepository.findAll();
        }

        private void converterListaDeUfsParaListaDeUfsDeSaida() {
            listaDeUfsDeSaida = listaDeUfsSalvas.stream().map(UfDtoSaida::new).collect(Collectors.toList());
        }

    // ---------- Listar
    public ResponseEntity<?> listar(UfDtoEntrada filtros) {

        if(filtros.getCodigoUF() != null || filtros.getNome() != null || filtros.getSigla() != null) {
            var ufFiltro = modelMapper.map(filtros, Uf.class);

            // ExampleMatcher - permite configurar condições para serem aplicadas nos filtros
            ExampleMatcher matcher = ExampleMatcher
                    .matching()
                    .withIgnoreCase() // Ignore caixa alta ou baixa - quando String
                    .withIgnoreNullValues()
                    .withStringMatcher(ExampleMatcher
                            .StringMatcher.CONTAINING); // permite encontrar palavras tipo Like com Containing

            // Example - pega campos populados para criar filtros
            Example example = Example.of(ufFiltro, matcher);

            var ufDoDatabase = ufRepository.findOne(example);
            if(!ufDoDatabase.isPresent())
                return ResponseEntity.notFound().build();
            ufSalva = (Uf) ufDoDatabase.get();

            converterUfParaUfDtoSaida();
            return ResponseEntity.ok().body(ufDeSaida);
        }

        buscarTodasUfsParaRetornar();
        converterListaDeUfsParaListaDeUfsDeSaida();

        return ResponseEntity.ok().body(listaDeUfsDeSaida);
    }

    // ---------- Consultar
    public ResponseEntity<?> consultar(Long codigoUF) {

        var ufDoDatabase = ufRepository.findById(codigoUF);
        if(!ufDoDatabase.isPresent())
            return ResponseEntity.badRequest().body("CodigoUF - ".concat(MensagemPadrao.ID_NAO_ENCONTRADO));
        ufSalva = ufDoDatabase.get();

        converterUfParaUfDtoSaida();

        return ResponseEntity.ok().body(ufDeSaida);
    }

        private void converterUfParaUfDtoSaida() {
            ufDeSaida = modelMapper.map(ufSalva, UfDtoSaida.class);
        }

    // ---------- Atualizar
    public ResponseEntity<?> atualizar(Long codigoUF, UfDtoEntrada ufDtoEntrada) {

        // Design Pattern comportamental
        try{
            listaAtualizarDeValidacoesDeUf.forEach(regraDeNegocio -> regraDeNegocio.validar(codigoUF, ufDtoEntrada));
        }catch(ValidacaoException validacaoException){
            return ResponseEntity.badRequest().body(validacaoException.getMessage());
        }

        return ufRepository.findById(codigoUF)
                .map( ufDoDatabase -> {
                    ufDoDatabase.setSigla(ufDtoEntrada.getSigla());
                    ufDoDatabase.setNome(ufDtoEntrada.getNome());
                    ufDeSaida = modelMapper.map(ufDoDatabase, UfDtoSaida.class);
                    return ResponseEntity.ok().body(ufDeSaida);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ---------- Deletar
    public ResponseEntity<List<UfDtoSaida>> deletar(Long codigoUF) {

        return ufRepository.findById(codigoUF)
                .map(uf -> {
                    ufRepository.delete(uf);
                    buscarTodasUfsParaRetornar();
                    converterListaDeUfsParaListaDeUfsDeSaida();
                    return ResponseEntity.ok().body(listaDeUfsDeSaida);
                }).orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "CodigoUF - ".concat(MensagemPadrao.ID_NAO_ENCONTRADO)));
    }
}
