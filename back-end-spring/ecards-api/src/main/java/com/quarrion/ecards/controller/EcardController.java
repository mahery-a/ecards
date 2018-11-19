package com.quarrion.ecards.controller;

import com.quarrion.ecards.exception.ResourceNotFoundException;
import com.quarrion.ecards.model.Ecard;
import com.quarrion.ecards.repository.EcardRepository;
import com.quarrion.ecards.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EcardController {

    @Autowired
    EcardRepository ecardRepository;
    @Autowired
	private MailService notificationService;

    @GetMapping("/ecard")
    public List<Ecard> getAllEcards() {
        return ecardRepository.findAll();
    }

    @PostMapping("/ecard")
    public Ecard createEcard(@Valid @RequestBody Ecard ecard) {
        return ecardRepository.save(ecard);
    }

    @GetMapping("/ecard/{id}")
    public Ecard getEcardById(@PathVariable(value = "id") Integer ecardId) {
        return ecardRepository.findById(ecardId)
                .orElseThrow(() -> new ResourceNotFoundException("Ecard", "id", ecardId));
    }

    @PutMapping("/ecard/{id}")
    public Ecard updateEcard(@PathVariable(value = "id") Integer ecardId,
                                           @Valid @RequestBody Ecard ecardDetails) {

        Ecard ecard = ecardRepository.findById(ecardId)
                .orElseThrow(() -> new ResourceNotFoundException("Ecard", "id", ecardId));

        ecard.setTitle(ecardDetails.getTitle());
        ecard.setMediaUrl(ecardDetails.getMediaUrl());

        Ecard updatedNote = ecardRepository.save(ecard);
        return updatedNote;
    }

    @DeleteMapping("/ecard/{id}")
    public ResponseEntity<?> deleteEcard(@PathVariable(value = "id") Integer ecardId) {
        Ecard ecard = ecardRepository.findById(ecardId)
                .orElseThrow(() -> new ResourceNotFoundException("Ecard", "id", ecardId));

        ecardRepository.delete(ecard);

        return ResponseEntity.ok().build();
    }
    
    @PostMapping("send-ecard")
	public String send(@Valid @RequestBody Ecard ecard) throws MailException, MessagingException{
    	ecardRepository.save(ecard);
		try {
			notificationService.sendEcard(ecard);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		return "Congratulations! Your ecard has been sent";
	}
}
