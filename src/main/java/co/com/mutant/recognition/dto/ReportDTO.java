package co.com.mutant.recognition.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReportDTO {

	@JsonProperty("mutant_dna_count")
	private Long mutantDna;

	@JsonProperty("human_dna_count")
	private Long humanDna;

	private double ratio;

}
