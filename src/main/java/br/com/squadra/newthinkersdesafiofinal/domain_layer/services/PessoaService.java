package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.PessoaDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities.Pessoa;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.PessoaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public final class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ModelMapper modelMapper;

    private PessoaDtoEntrada pessoaDtoEntrada;
    private Pessoa pessoaSalva;
    private PessoaDtoSaida pessoaDtoSaida;

    // ---------- MÉTODOS DE SERVIÇO ---------- //
    public ResponseEntity<?> cadastrar(PessoaDtoEntrada pessoaDtoEntrada) {
        this.pessoaDtoEntrada = pessoaDtoEntrada;

        converterPessoaDtoEntradaParaPessoa();
        salvarPessoa();
        converterPessoaParaPessoaDtoSaida();

        return ResponseEntity.ok().body(pessoaDtoSaida);
    }

        private void converterPessoaDtoEntradaParaPessoa() {
            pessoaSalva = modelMapper.map(pessoaDtoEntrada, Pessoa.class);
            pessoaSalva.setStatus(1);
        }

        private void salvarPessoa() {
            pessoaSalva = pessoaRepository.saveAndFlush(pessoaSalva);
        }

        private void converterPessoaParaPessoaDtoSaida() {
            pessoaDtoSaida = modelMapper.map(pessoaSalva, PessoaDtoSaida.class);
        }

    public ResponseEntity<?> listar(String sobrenome) {
        return null;
    }
}
