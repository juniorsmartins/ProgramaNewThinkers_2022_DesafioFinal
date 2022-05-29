package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.EnderecoDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.EnderecoDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Endereco;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.EnderecoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public final class EnderecoService {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    // ---------- Atributos Injetados automaticamente
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ModelMapper modelMapper;
    // ---------- Atributos p/estilo pessoal de Clean Code
    private EnderecoDtoEntrada enderecoDeEntrada;
    private Endereco enderecoSalvo;
    private EnderecoDtoSaida enderecoDeSaida;
    private List<Endereco> listaDeEnderecosSalvos;
    private List<EnderecoDtoSaida> listaDeEnderecosDeSaida;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    // ---------- Cadastrar
    public ResponseEntity<?> cadastrar(EnderecoDtoEntrada enderecoDtoEntrada, UriComponentsBuilder uriComponentsBuilder) {
        enderecoDeEntrada = enderecoDtoEntrada;

        converterEnderecoDtoEntradaParaEndereco();
        setarStatusAtivado();
        salvarEndereco();
        converterEnderecoParaEnderecoDtoSaida();

        URI uri = uriComponentsBuilder.path("/enderecos/{codigoEndereco}").buildAndExpand(enderecoDeSaida.getCodigoEndereco()).toUri();
        return ResponseEntity.created(uri).body(enderecoDeSaida);
    }

        private void converterEnderecoDtoEntradaParaEndereco() {
            enderecoSalvo = modelMapper.map(enderecoDeEntrada, Endereco.class);
        }

        private void setarStatusAtivado() {
            enderecoSalvo.setStatus(1);
        }

        private void salvarEndereco() {
         enderecoSalvo = enderecoRepository.saveAndFlush(enderecoSalvo);
        }

        private void converterEnderecoParaEnderecoDtoSaida() {
            enderecoDeSaida = modelMapper.map(enderecoSalvo, EnderecoDtoSaida.class);
        }

    // ---------- Listar
    public ResponseEntity<?> listar() {

        buscarTodosOsEnderecosDoDatabase();
        converterListaDeEnderecosParaListaDeEnderecosDeSaida();

        return ResponseEntity.ok().body(listaDeEnderecosDeSaida);
    }

        private void buscarTodosOsEnderecosDoDatabase() {
            listaDeEnderecosSalvos = enderecoRepository.findAll();
        }

        private void converterListaDeEnderecosParaListaDeEnderecosDeSaida() {
            listaDeEnderecosDeSaida = listaDeEnderecosSalvos.stream().map(EnderecoDtoSaida::new).collect(Collectors.toList());
        }

    // ---------- Consultar
    public ResponseEntity<?> consultar(Long codigoEndereco) {

        var enderecoDoDatabase = enderecoRepository.findById(codigoEndereco);
        if(!enderecoDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        enderecoSalvo = enderecoDoDatabase.get();

        converterEnderecoParaEnderecoDtoSaida();

        return ResponseEntity.ok().body(enderecoDeSaida);
    }

    // ---------- Atualizar
    public ResponseEntity<?> atualizar(Long codigoEndereco, EnderecoDtoEntrada enderecoDtoEntrada) {
        enderecoDeEntrada = enderecoDtoEntrada;

        var enderecoDoDatabase = enderecoRepository.findById(codigoEndereco);
        if(!enderecoDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);
        enderecoSalvo = enderecoDoDatabase.get();

        atualizarEndereco();
        converterEnderecoParaEnderecoDtoSaida();

        return ResponseEntity.ok().body(enderecoDeSaida);
    }

        private void atualizarEndereco() {
            enderecoSalvo.setCep(enderecoDeEntrada.getCep());
            enderecoSalvo.setNomeRua(enderecoDeEntrada.getNomeRua());
            enderecoSalvo.setNumero(enderecoDeEntrada.getNumero());
            enderecoSalvo.setComplemento(enderecoDeEntrada.getComplemento());
        }

    // ---------- Deletar
    public ResponseEntity<?> deletar(Long codigoEndereco) {

        var enderecoDoDatabase = enderecoRepository.findById(codigoEndereco);
        if(!enderecoDoDatabase.isPresent())
            return ResponseEntity.badRequest().body(MensagemPadrao.ID_NAO_ENCONTRADO);

        enderecoRepository.deleteById(codigoEndereco);
        return ResponseEntity.ok().body(MensagemPadrao.ID_DELETADO);
    }
}
