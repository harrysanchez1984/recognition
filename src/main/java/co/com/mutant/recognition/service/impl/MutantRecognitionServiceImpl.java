package co.com.mutant.recognition.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import co.com.mutant.recognition.core.DNAValidator;
import co.com.mutant.recognition.core.SearchStrategy;
import co.com.mutant.recognition.dto.ReportDTO;
import co.com.mutant.recognition.entities.MutantEntity;
import co.com.mutant.recognition.enums.DNAGenes;
import co.com.mutant.recognition.enums.Directions;
import co.com.mutant.recognition.enums.ValidSequences;
import co.com.mutant.recognition.repository.MutantRepository;
import co.com.mutant.recognition.service.MutantRecognitionService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j 
public class MutantRecognitionServiceImpl implements MutantRecognitionService {

	private MutantRepository mutantRepository;
	
	public MutantRecognitionServiceImpl(MutantRepository mutantRepository) {
		this.mutantRepository = mutantRepository;
	}

	private String[] dna;

	static List<SearchStrategy> getDnaRun() {
		return Arrays.asList(new SearchStrategy(Directions.TOP, ValidSequences.DOWN),
				new SearchStrategy(Directions.LEFT, ValidSequences.RIGHT),
				new SearchStrategy(Directions.TOP, ValidSequences.DIAGONAL_DOWN),
				new SearchStrategy(Directions.LEFT, ValidSequences.DIAGONAL_DOWN),
				new SearchStrategy(Directions.LEFT, ValidSequences.DIAGONAL_UP),
				new SearchStrategy(Directions.BOTTOM, ValidSequences.DIAGONAL_UP));
	}

	@Override
	public Boolean isMutant(String[] dna) {
		this.dna = dna;
		validateDnaSize();
		validateDnaStructure();

		return saveResult(dna, searchInAllDirections(dna));
	}

	@Override
	public ReportDTO getReport() {
		final Long mutant = mutantRepository.countByIsMutantValue(Boolean.TRUE).get();
		final Long human = mutantRepository.countByIsMutantValue(Boolean.FALSE).get();

		return ReportDTO.builder().humanDna(human).mutantDna(mutant).ratio(validateRatio(human, mutant)).build();
	}

	private boolean searchInAllDirections(String[] dna) {
		Integer count = 0;
		for (SearchStrategy searchStrategy : getDnaRun()) {
			log.info(" searchStrategy : {} ", searchStrategy);
			count += searchUsingStrategy(dna, searchStrategy.getDirection(), searchStrategy.getSequences());
			if (count >= MUTANT_VALID_SEQUENCE_LENGTH) {
				log.info(" count: {} ", count);
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	private Integer searchUsingStrategy(String[] dna, Directions direction, ValidSequences validSequence) {
		DNAValidator dnaIterator = new DNAValidator(dna, direction, validSequence);
		int sequenceCount = 0;
		do {
			int counter = 1;
			Character prevChar = dnaIterator.getValue();
			Character curChar;
			while (Objects.nonNull(curChar = dnaIterator.getNext(validSequence))) {
				if (curChar.equals(prevChar)) {
					counter++;
				} else {
					prevChar = curChar;
					counter = 1;
				}
				if (counter == MUTANT_SEQUENCE_LENGTH) {
					log.info(" sequence count returned : {} ", sequenceCount);
					return 1;
				}
			}
		} while (dnaIterator.nextStartDimension(direction));
		return sequenceCount;
	}

	private void validateDnaSize() {
		Arrays.stream(this.dna).parallel().filter(dnaMutant -> Objects.nonNull(dna))
				.filter(dnaMutant -> dnaMutant.length() > 0).filter(dnaMutant -> {
					return dnaMutant.length() <= MutantRecognitionService.MUTANT_CHROMOSOME_LENGTH;
				}).map(dnaMutant -> Boolean.TRUE).findAny()
				.orElseThrow(() -> new RuntimeException("Array size error"));
	}

	private void validateDnaStructure() {
		log.info(" DNA validation: " + this.dna);
		Arrays.stream(this.dna).forEach(charItem -> {
			charItem.chars().forEach(value -> {
				validateDnaContent(Character.toString((char) value));
			});
		});
	}

	private Boolean saveResult(final String[] dna, final Boolean isMutant) {
		return storeResult(dna, isMutant);
	}

	@Async
	private Boolean storeResult(final String[] dna, final Boolean isMutant) {
		this.mutantRepository.save(MutantEntity.builder().id(String.join("-", dna)).isMutantValue(isMutant).build());
		return isMutant;
	}

	public double validateRatio(final double human, final double mutant) {
		log.info("Ratio -> no-mutant : {} mutant : {}", human, mutant);
		long ratio = 0;
		if (mutant == 0) {
			return ratio;
		}
		return (human / mutant);
	}

	private Boolean validateDnaContent(String character) {
		return Arrays.stream(DNAGenes.values()).filter(charEnum -> charEnum.name().equalsIgnoreCase(character))
				.map(value -> Boolean.TRUE).findAny()
				.orElseThrow(() -> new RuntimeException("Not valid DNA structure"));

	}

}
