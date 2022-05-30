package br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories;

import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Uf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UfRepository extends JpaRepository<Uf, Long> {

    Optional<Uf> findBySigla(String sigla);
    Optional<Uf> findByNome(String nome);
}
