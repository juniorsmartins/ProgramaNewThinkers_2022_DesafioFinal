package br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories;

import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
