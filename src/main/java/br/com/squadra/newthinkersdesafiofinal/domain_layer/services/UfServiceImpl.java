package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntradaAtualizar;
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
import java.util.List;
import java.util.stream.Collectors;

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
    // ---------- Regras de Negócio
    @Autowired
    private List<IRegrasUfCadastrar> listaDeRegrasDeCadastro;
    @Autowired
    private List<IRegrasUfAtualizar> listaDeRegrasDeAtualização;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    @Override
    public List<UfDtoSaida> cadastrar(UfDtoEntrada ufDtoEntrada) {
        ufDeEntrada = ufDtoEntrada;

        // Tratamento de regras de negócio
        listaDeRegrasDeCadastro.forEach(regra -> regra.validar(ufDeEntrada));

        converterUfDtoEntradaParaUf();
        salvarUf();
        buscarTodasUfsParaRetornar();
        converterListaDeUfsParaListaDeUfsDeSaida();
        return listaDeUfsDeSaida;
    }

        private void converterUfDtoEntradaParaUf() {
            ufSalva = modelMapper.map(ufDeEntrada, Uf.class);
        }

        private void salvarUf() {
            ufSalva = ufRepository.saveAndFlush(ufSalva);
        }

        private void buscarTodasUfsParaRetornar() {
            listaDeUfsSalvas = ufRepository.findAll()
                    .stream()
                    .sorted((uf1, uf2) -> uf2.getCodigoUF().compareTo(uf1.getCodigoUF()))
                    .toList();
        }

        private void converterListaDeUfsParaListaDeUfsDeSaida() {
            listaDeUfsDeSaida = listaDeUfsSalvas.stream()
                    .map(UfDtoSaida::new).collect(Collectors.toList());
        }

    // ---------- Listar
    @Override
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
                throw new RecursoNaoEncontradoException(MensagemPadrao.CODIGOUF_NAO_ENCONTRADO);
            ufSalva = (Uf) ufDoDatabase.get();

            converterUfParaUfDtoSaida();
            return ResponseEntity.ok().body(ufDeSaida);
        }

        if(filtros.getStatus() != null) {
            listaDeUfsSalvas = ufRepository.findByStatus(filtros.getStatus());
            converterListaDeUfsParaListaDeUfsDeSaida();
            return ResponseEntity.ok().body(listaDeUfsDeSaida);
        }

        buscarTodasUfsParaRetornar();
        converterListaDeUfsParaListaDeUfsDeSaida();
        return ResponseEntity.ok().body(listaDeUfsDeSaida);
    }

        private void converterUfParaUfDtoSaida() {
            ufDeSaida = modelMapper.map(ufSalva, UfDtoSaida.class);
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

    // ---------- Atualizar
    @Override
    public List<UfDtoSaida> atualizar(UfDtoEntradaAtualizar ufDtoEntrada) {

        // Tratamento de regras de negócio
        listaDeRegrasDeAtualização.forEach(regra -> regra.validar(ufDtoEntrada));

        return ufRepository.findById(ufDtoEntrada.getCodigoUF())
                .map(ufDoDatabase -> {
                    ufDoDatabase.setSigla(ufDtoEntrada.getSigla());
                    ufDoDatabase.setNome(ufDtoEntrada.getNome());
                    ufDoDatabase.setStatus(ufDtoEntrada.getStatus());
                    ufRepository.saveAndFlush(ufDoDatabase);
                    buscarTodasUfsParaRetornar();
                    converterListaDeUfsParaListaDeUfsDeSaida();
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
                    converterListaDeUfsParaListaDeUfsDeSaida();
                    return listaDeUfsDeSaida;
                }).orElseThrow(() -> new RecursoNaoEncontradoException(MensagemPadrao.CODIGOUF_NAO_ENCONTRADO));
    }
}
