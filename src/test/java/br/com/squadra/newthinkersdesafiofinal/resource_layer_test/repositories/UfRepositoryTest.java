package br.com.squadra.newthinkersdesafiofinal.resource_layer_test.repositories;

import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Uf;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.UfRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UfRepositoryTest {

    @Autowired
    private UfRepository ufRepository;

    @Test
    void testePositivo_ParaBuscarUFPorNomeDoDatabase() {
        String nomeUF = "Rio Grande do Sul";
        Uf ufPorNome = ufRepository.findByNome(nomeUF).get();
        Assert.assertNotNull(ufPorNome);
        Assert.assertEquals(nomeUF, ufPorNome.getNome());
    }

    @Test
    void testePositovo_ParaBuscarUFPorSiglaDoDatabase() {
        String siglaUF = "RS";
        Uf ufPorSigla = ufRepository.findBySigla(siglaUF).get();
        Assert.assertNotNull(ufPorSigla);
        Assert.assertEquals(siglaUF, ufPorSigla.getSigla());
    }
}
