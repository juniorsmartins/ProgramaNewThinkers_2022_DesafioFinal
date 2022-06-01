package br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories;

import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BairroRepository extends JpaRepository<Bairro, Long> {

    Optional<Bairro> findByNome(String nome);
}
