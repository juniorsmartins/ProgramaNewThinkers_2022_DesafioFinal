package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.pessoa;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasPessoaAtualizar;
import org.springframework.stereotype.Service;

@Service
public class Regra2 implements IRegrasPessoaAtualizar {

    @Override
    public void validar(PessoaDtoEntradaAtualizar pessoaDtoEntradaAtualizar) {

    }
}
