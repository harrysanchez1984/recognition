package co.com.mutant.recognition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import co.com.mutant.recognition.dto.DNASampleDTO;

@SpringBootTest
class RecognitionApplicationTests {

	@Test
	void contextLoads() {
		String[] process = {"arg1", "arg2"};

		DNASampleDTO sample =  new DNASampleDTO();
		sample.setDnaSample(process);

		assertTrue(Objects.nonNull(sample));
		assertEquals("arg1", sample.getDnaSample()[0]);
	}

}
