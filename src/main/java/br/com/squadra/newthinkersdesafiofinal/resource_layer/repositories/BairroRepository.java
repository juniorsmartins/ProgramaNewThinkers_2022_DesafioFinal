package br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories;

import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Bairro;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BairroRepository extends JpaRepository<Bairro, Long> {

    List<Bairro> findByMunicipio(Municipio municipio);
}
