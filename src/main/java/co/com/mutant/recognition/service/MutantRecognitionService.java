package co.com.mutant.recognition.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import co.com.mutant.recognition.core.DNAValidator;
import co.com.mutant.recognition.dto.ReportDTO;
import co.com.mutant.recognition.enums.ValidSequences;

public interface MutantRecognitionService {
	
	public static final Set<Character> VALID_SEQUENCE = new HashSet<>(Arrays.asList('A', 'T', 'C', 'G'));

    public static final int MUTANT_SEQUENCE_LENGTH = 4;

    public static final int MUTANT_CHROMOSOME_LENGTH = 6;

    public static final int MUTANT_VALID_SEQUENCE_LENGTH = 3;

    public Boolean isMutant( final String[] dna);

    public ReportDTO getReport();

    public default Character validateEndProcessMutant(final DNAValidator dnaValidator, final ValidSequences directions) {
    	dnaValidator.getNext(directions);
        return dnaValidator.getNext(directions);
    }


}
