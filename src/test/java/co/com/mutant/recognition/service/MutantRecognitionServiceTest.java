package co.com.mutant.recognition.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.com.mutant.recognition.dto.ReportDTO;
import co.com.mutant.recognition.entities.MutantEntity;
import co.com.mutant.recognition.repository.MutantRepository;
import co.com.mutant.recognition.service.impl.MutantRecognitionServiceImpl;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MutantRecognitionServiceTest {
	
	@Mock
    private MutantRepository mutantRepository;

    @InjectMocks
    MutantRecognitionServiceImpl mutantRecognitionServiceImpl;

    @SuppressWarnings("deprecation")
	@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mutantRepository.save(Mockito.any(MutantEntity.class))).thenReturn(MutantEntity.builder()
                .id(String.join("1"))
                .isMutantValue(true)
                .build());
    }

    @Test
    void whenAdnIsMutantThenReturnTrue() {

        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        boolean flag = mutantRecognitionServiceImpl.isMutant(dna);
        assertTrue(flag);

    }

    @Test
    void whenAdnIsNotMutantThenReturnFalse() {

        String[] dna = {"ATGAGA","CAGAGC","TTCTGT","AGAAGG","CTTCTA","TCACTG"};

        boolean flag = mutantRecognitionServiceImpl.isMutant(dna);

        assertFalse(flag);

    }


    @Test
    void validateRatioReturnZeroTest() {
        double response = mutantRecognitionServiceImpl.validateRatio(Long.parseLong("2"), Long.valueOf(2));
        assertTrue(response > 0L);
        assertTrue(response == 1L);

    }

    @Test
    void validateRatioReturnRatioMutantTest() {
        double response = mutantRecognitionServiceImpl.validateRatio(Long.parseLong("2"), Long.valueOf(20));
        System.out.println(" response : " + response);
        assertTrue(response > 0L);
        assertTrue(response == 0.1D);
    }


    @Test
    void validateRatioReturnRatioHumanTest() {

        double response = mutantRecognitionServiceImpl.validateRatio(Long.parseLong("40"), Long.valueOf(20));
        System.out.println(" response : " + response);
        assertTrue(response > 0L);
        assertTrue(response == 2.0D);

    }

    @Test
    void validateRatioReturnZeroRatioHumanTest() {

        double response = mutantRecognitionServiceImpl.validateRatio(Long.parseLong("0"), Long.valueOf(20));
        assertTrue(response == 0.0D);

    }

    @Test
    void validateGetStats() {

        when(mutantRepository.countByIsMutantValue(Mockito.any(Boolean.class)))
                .thenReturn(Optional.of(20L));

        ReportDTO report = mutantRecognitionServiceImpl.getReport();
        System.out.println(" Stats : "+ report);
    }

}
