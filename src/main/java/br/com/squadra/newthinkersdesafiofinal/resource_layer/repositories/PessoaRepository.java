package br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories;

import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
