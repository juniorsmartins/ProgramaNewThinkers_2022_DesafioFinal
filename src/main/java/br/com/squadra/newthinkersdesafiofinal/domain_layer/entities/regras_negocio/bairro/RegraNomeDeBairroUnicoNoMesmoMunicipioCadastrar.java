package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.bairro;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio.IRegrasBairroCadastrar;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.MensagemPadrao;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RecursoNaoEncontradoException;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes.RegrasDeNegocioVioladasException;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Bairro;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.BairroRepository;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.MunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegraNomeDeBairroUnicoNoMesmoMunicipioCadastrar implements IRegrasBairroCadastrar {

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private BairroRepository bairroRepository;

    @Override
    public void validar(BairroDtoEntrada bairroDtoEntrada) {

        var municipioPorCodigo = municipioRepository
                .findById(bairroDtoEntrada.getCodigoMunicipio());
        if(!municipioPorCodigo.isPresent())
            throw new RecursoNaoEncontradoException(MensagemPadrao.CODIGOMUNICIPIO_NAO_ENCONTRADO);

        System.out.println(municipioPorCodigo.get().getNome());

        var listaDeBairrosPorMunicipio = bairroRepository
                .findByMunicipio(municipioPorCodigo.get());

        var a = listaDeBairrosPorMunicipio
                .stream()
                .filter(bairro -> bairroDtoEntrada.getNome().equalsIgnoreCase(bairro.getNome()))
                .distinct();
        if(a != null)
            throw new RegrasDeNegocioVioladasException(MensagemPadrao.NOME_NAO_UNICO_NO_MUNICIPIO);
    }
}
