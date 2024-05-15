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
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.popo.utils.Status;

import lombok.RequiredArgsConstructor;

import com.popo.models.Devis;
import com.popo.models.modelsUtilities.GraphsSetup;
import com.popo.services.DevisService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/devis")
public class DevisController {

	private final com.popo.repository.DevisRepository devisRepository;
	private final DevisService devisService;

	@DeleteMapping("/{id}")
	public Status delete(@PathVariable(name = "id") Long id) {
		devisRepository.deleteById(id);
		return Status.ok("Devis deleted !!", null);
	}

	@PostMapping
	public Status add(@RequestBody Devis devis) {
		Devis res = devisService.saveBuild(devis);
		return Status.ok("Devis inserted succesfully", res);
	}

	@GetMapping
	public Status getAll() {
		List<Devis> res = devisService.getAllDevis();
		return Status.ok("", res);
	}

	@GetMapping("/total")
	public Status getSumTotal() {
		Double amount = 0.0;
		List<Devis> res = devisRepository.findAll();
		for (Devis devis : res) {
			amount += devis.getTotal_price();
		}
		return Status.ok("", amount);
	}

	// @GetMapping("/by/year")
	// public Status getAllDevisByYear() {
	// Object[][] res = devisRepository.filterByYear();
	// return Status.ok("", GraphsSetup.graphTime(res));
	// }

	@GetMapping("/by/month/{year}")
	public Status getAllDevisByMonth(@PathVariable(name = "year") Long year) {
		Object[][] res = devisRepository.filterByMonth(year);
		return Status.ok("", GraphsSetup.graphTime(res));
	}

	@GetMapping("/allYear")
	public Status getAllMonth() {
		Object[][] res = devisRepository.getAllYearPresent();
		return Status.ok("", res);
	}

	@GetMapping("/client/{id}")
	public Status getAllDeviseOfClient(@PathVariable(name = "id") int id_user) {
		List<Devis> res = devisService.getAllOfMyDevis(id_user);
		return Status.ok("", res);
	}

	@GetMapping("/{page}/count/{count}")
	public Status getElements(@PathVariable("page") int page, @PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(page, count);
		List<Devis> res = devisRepository.findAll(pageable).getContent();
		return Status.ok("", res);
	}

	@GetMapping("/page/{count}")
	public Status getPageCount(@PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(0, count);
		int totalPage = devisRepository.findAll(pageable).getTotalPages();
		return Status.ok("", totalPage);
	}

	@PutMapping
	public Status update(@RequestBody Devis devis) {
		Devis res = devisRepository.save(devis);
		return Status.ok("Devis updated succesfully", res);
	}

	@ExceptionHandler(Exception.class)
	public Status handleException(Exception e) {
		e.printStackTrace();
		return Status.error(e.getMessage(), null);
	}

}
