package br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories;

import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
}
