package co.com.mutant.recognition.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.mutant.recognition.dto.DNASampleDTO;
import co.com.mutant.recognition.dto.ReportDTO;
import co.com.mutant.recognition.service.MutantRecognitionService;

@RestController
@RequestMapping(path = "mutantRecognition")
@CrossOrigin("*")
public class MutantRecognitionController {
	
	private MutantRecognitionService mutantRecognitionService;
	
	public MutantRecognitionController(MutantRecognitionService mutantRecognitionService) {
        this.mutantRecognitionService = mutantRecognitionService;

    }

    @GetMapping(path = "/report")
    public ReportDTO getStats() {
        return this.mutantRecognitionService.getReport();
    }

    @PostMapping(path = "/validate")
    public ResponseEntity<Boolean> isMutant(@RequestBody final DNASampleDTO dnaSample) {
        return ResponseEntity
                .ok(this.mutantRecognitionService.isMutant(dnaSample.getDnaSample()));
    }

}
