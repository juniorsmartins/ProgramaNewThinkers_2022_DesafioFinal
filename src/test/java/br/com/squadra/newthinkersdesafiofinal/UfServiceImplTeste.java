package br.com.squadra.newthinkersdesafiofinal;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.UfDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.services.UfServiceImpl;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Uf;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.repositories.UfRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

@SpringBootTest
public class UfServiceImplTeste {

    public static final Long ID = 1L;
    public static final String SIGLA = "AM";
    public static final String NOME = "Amapá";
    public static final Integer STATUS = 1;

    @InjectMocks
    private UfServiceImpl ufService;
    @Mock
    private UfRepository ufRepository;
    @Mock
    private ModelMapper modelMapper;

    private Uf uf;
    private Optional<Uf> ufOptional;
    private UfDtoSaida ufDtoSaida;

    @BeforeEach
    void criarMocksAntes() {
        MockitoAnnotations.openMocks(this);
        iniciarUFs();
    }

    @Test
    public void UfServiceImplTeste() {
        Mockito.when(ufRepository.findById(Mockito.anyLong())).thenReturn(ufOptional);
        Mockito.when(modelMapper.map(ufOptional, Uf.class)).thenReturn(uf);
        Mockito.when(modelMapper.map(uf, UfDtoSaida.class)).thenReturn(ufDtoSaida);
        UfDtoSaida ufDtoSaidaResponse = ufService.consultar(1L);

        Assertions.assertNotNull(ufDtoSaidaResponse);
        Assertions.assertEquals(ID, ufDtoSaidaResponse.getCodigoUF());
        Assertions.assertEquals(UfDtoSaida.class, ufDtoSaidaResponse.getClass());
    }



    void iniciarUFs() {
        uf = new Uf();
        uf.setCodigoUF(ID);
        uf.setNome(NOME);
        uf.setSigla(SIGLA);
        uf.setStatus(STATUS);

        ufOptional = Optional.of(uf);

        ufDtoSaida = new UfDtoSaida();
        ufDtoSaida.setCodigoUF(ID);
        ufDtoSaida.setNome(NOME);
        ufDtoSaida.setSigla(SIGLA);
        ufDtoSaida.setStatus(STATUS);
    }
}
