package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.bairro;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasBairroAtualizar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RecursoNaoEncontradoException;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RegrasDeNegocioVioladasException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Bairro;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.BairroRepository;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.MunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegraNomeUnicoDeBairroPorMunicipioAtualizar implements IRegrasBairroAtualizar {

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private BairroRepository bairroRepository;

    @Override
    public void validar(BairroDtoEntradaAtualizar bairroDtoEntrada) {

        // busca município por códigoID
        var municipioPorCodigo = municipioRepository
                .findById(bairroDtoEntrada.getCodigoMunicipio());

        // verifica se trouxe município
        if(!municipioPorCodigo.isPresent())
            throw new RecursoNaoEncontradoException(MensagemPadrao.CODIGOMUNICIPIO_NAO_ENCONTRADO);

        // busca os bairros por município
        var listaDeBairrosPorMunicipio = bairroRepository
                .findByMunicipio_codigoMunicipio(municipioPorCodigo.get().getCodigoMunicipio());

        // checagem sobre o nome, lança exceção se já houver nome de bairro igual no mesmo município
        for(Bairro bairro : listaDeBairrosPorMunicipio)
            if (bairro.getNome().equalsIgnoreCase(bairroDtoEntrada.getNome()))
                if(bairro.getCodigoBairro() != bairroDtoEntrada.getCodigoBairro())
                    throw new RegrasDeNegocioVioladasException(MensagemPadrao.NOME_NAO_UNICO_NO_MUNICIPIO);
    }
}
