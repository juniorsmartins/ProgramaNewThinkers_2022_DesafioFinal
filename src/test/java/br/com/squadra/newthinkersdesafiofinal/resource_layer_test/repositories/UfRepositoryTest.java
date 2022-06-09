package br.com.squadra.newthinkersdesafiofinal.resource_layer_test.repositories;

import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Uf;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.UfRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UfRepositoryTest {

    @Autowired
    public UfRepository ufRepository;

    @Test
    public void testeNegativo_ParaBuscarUFPorNomeDoDatabase() {
        String nomeUF = "Vila Real";
        Optional<Uf> ufPorNome = ufRepository.findByNome(nomeUF);
        Assert.assertNotSame(Uf.class, ufPorNome);
    }

    @Test
    public void testePositivo_ParaBuscarUFPorNomeDoDatabase() {
        String nomeUF = "Rio Grande do Sul";
        Uf ufPorNome = ufRepository.findByNome(nomeUF).get();
        Assert.assertNotNull(ufPorNome);
        Assert.assertEquals(nomeUF, ufPorNome.getNome());
    }

    @Test
    public void testePositivo_ParaBuscarUFPorSiglaDoDatabase() {
        String siglaUF = "RS";
        Uf ufPorSigla = ufRepository.findBySigla(siglaUF).get();
        Assert.assertNotNull(ufPorSigla);
        Assert.assertEquals(siglaUF, ufPorSigla.getSigla());
    }
}
