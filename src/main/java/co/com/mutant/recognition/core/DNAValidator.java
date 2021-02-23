package co.com.mutant.recognition.core;

import java.util.stream.Stream;

import co.com.mutant.recognition.enums.Directions;
import co.com.mutant.recognition.enums.ValidSequences;

public class DNAValidator {

	private final String[] dna;
	private final int dnaSize;
	private int row, col;
	private int rowStart, colStart;
	Directions start;
	ValidSequences direction;

	public DNAValidator(String[] dna, Directions start, ValidSequences direction) {

		this.dna = dna;
		this.dnaSize = dna.length;
		this.row = 0;
		this.col = 0;
		this.start = start;
		this.direction = direction;
		this.rowStart = 0;
		this.colStart = 0;

		initConfig(start, direction);

	}

	private void initConfig(Directions directions, ValidSequences circuitEnum) {

		Stream.of(directions).parallel()
				.filter(mutantEnum -> mutantEnum.equals(Directions.BOTTOM) && direction == ValidSequences.DIAGONAL_UP)
				.flatMap(this::apply)
				.filter(mutantEnum -> mutantEnum.equals(Directions.TOP) && direction == ValidSequences.DIAGONAL_DOWN)
				.flatMap(this::apply);
	}

	public boolean nextStartDimension(Directions directions) {
		return Stream.of(directions).parallel().flatMap(this::validateNextInitMutantProcess).findAny().get();
	}

	public Character getValue() {
		if (row < 0 || row > dnaSize || col < 0 || col > dnaSize) {
			return null;
		}
		return dna[row].charAt(col);
	}

	public Character getNext(ValidSequences direction) {

		return Stream.of(direction).parallel().flatMap(this::validateProcessEnum).findAny().orElse(null);
	}

	private Stream<Directions> apply(Directions item) {
		this.col = 1;
		this.colStart = 1;
		return Stream.of(item);
	}

	private Stream<Character> validateProcessEnum(final ValidSequences item) {
		Boolean isValue = Boolean.FALSE;
		if (item.equals(ValidSequences.RIGHT) && (col < dnaSize - 1)) {
			col = col + 1;
			isValue = Boolean.TRUE;
		} else if (item.equals(ValidSequences.DOWN) && (row < dnaSize - 1)) {
			row = row + 1;
			isValue = Boolean.TRUE;
		} else if (item.equals(ValidSequences.DIAGONAL_DOWN) && (row < dnaSize - 1 && col < dnaSize - 1)) {
			row = row + 1;
			col = col + 1;
			isValue = Boolean.TRUE;
		} else if (item.equals(ValidSequences.DIAGONAL_UP) && (row > 0 && col < dnaSize - 1)) {
			row = row - 1;
			col = col + 1;
			isValue = Boolean.TRUE;
		}

		if (isValue) {
			return Stream.of(getValue());
		}

		return null;
	}

	private Stream<Boolean> validateNextInitMutantProcess(final Directions directions) {
		if (directions.equals(Directions.TOP) || directions.equals(Directions.BOTTOM)) {
			row = (directions.equals(Directions.TOP)) ? 0 : dnaSize - 1;
			colStart = colStart + 1;
			col = colStart;
			return Stream.of(colStart < dnaSize);
		}
		col = 0;
		rowStart = rowStart + 1;
		row = rowStart;
		return Stream.of(rowStart < dnaSize);
	}

}
