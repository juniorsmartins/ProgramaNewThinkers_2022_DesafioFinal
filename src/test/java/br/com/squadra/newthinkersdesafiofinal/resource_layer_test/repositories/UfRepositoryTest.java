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
    public void testePositivo_ParaBuscarUFPorNomeDoDatabase() {
        String nomeUF = "Rio Grande do Sul";
        Optional<Uf> ufPorNome = ufRepository.findByNome(nomeUF);
        Assert.assertNotNull(ufPorNome);
        Assert.assertEquals(nomeUF, ufPorNome.get().getNome());
    }

    @Test
    public void testePositivo_ParaBuscarUFPorSiglaDoDatabase() {
        String siglaUF = "RS";
        Optional<Uf> ufPorSigla = ufRepository.findBySigla(siglaUF);
        Assert.assertNotNull(ufPorSigla);
        Assert.assertEquals(siglaUF, ufPorSigla.get().getSigla());
    }

/*
  outra abordagem

  @Autowired
  private TestEntityManager em;

  @Test
  public void testeX() {
    String ufNome = "Acre";

    Uf uf = new Uf();
    uf.setNome(ufNome);
    uf.setSigla("AC");
    em.persist(uf);

    Uf ufTest = ufRepository.findByName(ufNome);
    Assert.assertNotNull(ufTest);
    Assert.assertEquals(ufNome, ufTest.getNome());

  }


*/
}
