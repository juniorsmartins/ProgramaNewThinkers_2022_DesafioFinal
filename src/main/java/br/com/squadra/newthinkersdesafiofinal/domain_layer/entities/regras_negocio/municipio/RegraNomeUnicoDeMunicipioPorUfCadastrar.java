package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.municipio;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasMunicipioCadastrar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RecursoNaoEncontradoException;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RegrasDeNegocioVioladasException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Municipio;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.MunicipioRepository;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.UfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class RegraNomeUnicoDeMunicipioPorUfCadastrar implements IRegrasMunicipioCadastrar {

    @Autowired
    private UfRepository ufRepository;

    @Autowired
    private MunicipioRepository municipioRepository;

    @Override
    public void validar(MunicipioDtoEntrada municipioDtoEntrada) {

        // busca uf por codigo
        var ufPorCodigo = ufRepository.findById(municipioDtoEntrada.getCodigoUF());

        // verifica existência de uf
        if(!ufPorCodigo.isPresent())
            throw new RecursoNaoEncontradoException(MensagemPadrao.CODIGOUF_NAO_ENCONTRADO);

        // busca municipios por uf
        var listaDeMunicipiosPorUf = municipioRepository
                .findByUf_codigoUf(ufPorCodigo.get().getCodigoUF());

        // checagem sobre o nome, lança exceção se já houver nome de município na mesma UF
        for(Municipio municipio : listaDeMunicipiosPorUf)
            if(municipio.getNome().equalsIgnoreCase(municipioDtoEntrada.getNome()))
                throw new RegrasDeNegocioVioladasException(MensagemPadrao.NOME_NAO_UNICO_NA_UF);
    }
}
