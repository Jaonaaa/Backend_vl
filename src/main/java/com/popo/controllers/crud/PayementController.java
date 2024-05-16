package com.popo.controllers.crud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.File;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import com.popo.utils.Status;

import lombok.RequiredArgsConstructor;

import com.geta.utils.reader.CsvReader;
import com.geta.utils.upload.FileHelper;
import com.popo.models.Payement;
import com.popo.models.modelsUtilities.FilePayement;
import com.popo.models.temp.PaiementImport;
import com.popo.services.ImportService;
import com.popo.services.PayementService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/payement")
@RequiredArgsConstructor
public class PayementController {

	private final com.popo.repository.PayementRepository payementRepository;
	private final PayementService payementService;
	private final ImportService importService;

	@DeleteMapping("/{id}")
	public Status delete(@PathVariable(name = "id") Long id) {
		payementRepository.deleteById(id);
		Timestamp p = Timestamp.from(Instant.now());
		return Status.ok("Payement deleted !!", null);
	}

	@PostMapping("/file")
	public Status addFile(@RequestBody FilePayement payement) {
		File f = new File(FileHelper.upload(payement.getFile(), "./", "csv"));
		List<PaiementImport> res = PaiementImport.getPaiementImportsFromCSV(f.getAbsolutePath());
		importService.importPaiement(res);
		return Status.ok("Payement inserted succesfully", res);
	}

	@PostMapping
	public Status add(@RequestBody Payement payement) {
		Payement res = payementService.saveBuild(payement);
		return Status.ok("Payement inserted succesfully", res);
	}

	@PostMapping("/all")
	public Status addAll(@RequestBody List<Payement> payementd) {
		for (Payement payement : payementd) {
			payementService.saveBuild(payement);
		}
		return Status.ok("All Payement inserted succesfully", null);
	}

	@GetMapping
	public Status getAll() {
		List<Payement> res = payementRepository.findAll();
		return Status.ok("", res);
	}

	@GetMapping("/all/amount")
	public Status getAllAmount() {
		Double amount = 0.0;
		List<Payement> res = payementRepository.findAll();
		for (Payement payement : res) {
			amount += payement.getAmount();
		}
		return Status.ok("", amount);
	}

	@GetMapping("/{page}/count/{count}")
	public Status getElements(@PathVariable("page") int page, @PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(page, count);
		List<Payement> res = payementRepository.findAll(pageable).getContent();
		return Status.ok("", res);
	}

	@GetMapping("/page/{count}")
	public Status getPageCount(@PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(0, count);
		int totalPage = payementRepository.findAll(pageable).getTotalPages();
		return Status.ok("", totalPage);
	}

	@PutMapping
	public Status update(@RequestBody Payement payement) {
		Payement res = payementRepository.save(payement);
		return Status.ok("Payement updated succesfully", res);
	}

	@ExceptionHandler(Exception.class)
	public Status handleException(Exception e) {
		e.printStackTrace();
		return Status.error(e.getMessage(), null);
	}

}
