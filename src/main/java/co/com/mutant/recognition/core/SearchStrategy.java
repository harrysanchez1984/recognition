package co.com.mutant.recognition.core;

import co.com.mutant.recognition.enums.Directions;
import co.com.mutant.recognition.enums.ValidSequences;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SearchStrategy {
	
	private Directions direction;
    private ValidSequences sequences;

    public SearchStrategy(Directions direction, ValidSequences sequences) {
        this.direction = direction;
        this.sequences = sequences;
    }

    public Directions getDirections() {
        return this.direction;
    }

    public ValidSequences getSequences() {
        return this.sequences;
    }

}
