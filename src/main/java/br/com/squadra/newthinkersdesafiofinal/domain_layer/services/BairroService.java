package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.BairroDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities.Bairro;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.BairroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // ---------- Listar

    // ---------- Consultar

    // ---------- Atualizar

    // ---------- Deletar
    public ResponseEntity<?> deletar(Long codigoBairro) {

        var bairroDoDatabase = bairroRepository.findById(codigoBairro);
        if(!bairroDoDatabase.isPresent())
            return ResponseEntity.badRequest().body("Chave Identificadora não encontrada!");

        bairroRepository.deleteById(codigoBairro);
        return ResponseEntity.ok().body("Deletado Bairro com Chave Identificadora: " + codigoBairro + ".");
    }
}
