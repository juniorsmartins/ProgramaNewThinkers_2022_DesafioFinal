package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntradaListar;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.UfDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasUfAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasUfCadastrar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RecursoNaoEncontradoException;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.portas.UfService;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Uf;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.UfRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public final class UfServiceImpl implements UfService {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    // ---------- Injetados automaticamente
    @Autowired
    private UfRepository ufRepository;
    @Autowired
    private ModelMapper modelMapper;
    // ---------- Atributos p/estilo pessoal de Clean Code
    private UfDtoEntrada ufDeEntrada;
    private Uf ufSalva;
    private UfDtoSaida ufDeSaida;
    private List<Uf> listaDeUfsSalvas;
    private List<UfDtoSaida> listaDeUfsDeSaida;
    private Example exampleFiltro;
    // ---------- Regras de Negócio
    @Autowired
    private List<IRegrasUfCadastrar> listaDeRegrasDeCadastrar;
    @Autowired
    private List<IRegrasUfAtualizar> listaDeRegrasDeAtualizar;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    @Override
    public List<UfDtoSaida> cadastrar(UfDtoEntrada ufDtoEntrada) {
        ufDeEntrada = ufDtoEntrada;

        // Tratamento de regras de negócio
        listaDeRegrasDeCadastrar.forEach(regra -> regra.validar(ufDeEntrada));

        converterUfDtoEntradaParaUf();
        salvarUf();
        buscarTodasUfsParaRetornar();
        converterListaDeUfsParaListaDeUfsDeSaidaOrdenada();
        return listaDeUfsDeSaida;
    }

        private void converterUfDtoEntradaParaUf() {
            ufSalva = modelMapper.map(ufDeEntrada, Uf.class);
        }

        private void salvarUf() {
            ufRepository.saveAndFlush(ufSalva);
        }

        private void buscarTodasUfsParaRetornar() {
            listaDeUfsSalvas = ufRepository.findAll();
        }

        private void converterListaDeUfsParaListaDeUfsDeSaidaOrdenada() {
            listaDeUfsDeSaida = listaDeUfsSalvas
                    .stream()
                    .map(UfDtoSaida::new)
                    .sorted(Comparator.comparing(UfDtoSaida::getCodigoUF).reversed())
                    .toList();
        }

    // ---------- Listar
    @Override
    public ResponseEntity<?> listar(UfDtoEntradaListar ufDtoEntradaListar) {
        ufDeEntrada = modelMapper.map(ufDtoEntradaListar, UfDtoEntrada.class);

        criarExampleConfiguradoPorExampleMatcher();
        listaDeUfsSalvas = ufRepository.findAll(exampleFiltro);

        if(listaDeUfsSalvas.isEmpty())
            return ResponseEntity.ok().body(new ArrayList<>());

        converterListaDeUfsParaListaDeUfsDeSaidaOrdenada();

        if(ufDeEntrada.getCodigoUF() != null || ufDeEntrada.getNome() != null
                || ufDeEntrada.getSigla() != null)
            return ResponseEntity.ok().body(listaDeUfsDeSaida.get(0));

        return ResponseEntity.ok().body(listaDeUfsDeSaida);
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
            exampleFiltro = Example.of(modelMapper.map(ufDeEntrada, Uf.class), matcher);
        }

    // ---------- Consultar
    @Override
    public UfDtoSaida consultar(Long codigoUF) {

        return ufRepository.findById(codigoUF)
                .map(ufDoDatabase -> {
                    ufSalva = modelMapper.map(ufDoDatabase, Uf.class);
                    converterUfParaUfDtoSaida();
                    return ufDeSaida;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao.CODIGOUF_NAO_ENCONTRADO));
    }

        private void converterUfParaUfDtoSaida() {
            ufDeSaida = modelMapper.map(ufSalva, UfDtoSaida.class);
        }

    // ---------- Atualizar
    @Override
    public List<UfDtoSaida> atualizar(UfDtoEntradaAtualizar ufDtoEntrada) {

        // Tratamento de regras de negócio
        listaDeRegrasDeAtualizar.forEach(regra -> regra.validar(ufDtoEntrada));

        return ufRepository.findById(ufDtoEntrada.getCodigoUF())
                .map(ufDoDatabase -> {
                    ufDoDatabase.setSigla(ufDtoEntrada.getSigla());
                    ufDoDatabase.setNome(ufDtoEntrada.getNome());
                    ufDoDatabase.setStatus(ufDtoEntrada.getStatus());
                    ufRepository.saveAndFlush(ufDoDatabase);
                    buscarTodasUfsParaRetornar();
                    converterListaDeUfsParaListaDeUfsDeSaidaOrdenada();
                    return listaDeUfsDeSaida;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao.CODIGOUF_NAO_ENCONTRADO));
    }

    // ---------- Deletar
    @Override
    public List<UfDtoSaida> deletar(Long codigoUF) {

        return ufRepository.findById(codigoUF)
                .map(uf -> {
                    ufRepository.delete(uf);
                    buscarTodasUfsParaRetornar();
                    converterListaDeUfsParaListaDeUfsDeSaidaOrdenada();
                    return listaDeUfsDeSaida;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao.CODIGOUF_NAO_ENCONTRADO));
    }
}
