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

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.popo.models.WorksPredefinedBy;
import com.popo.repository.WorksPredefinedByRepository;
import com.popo.services.WorkPredefinedByService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/worksPredefinedBy")
@RequiredArgsConstructor
public class WorksPredefinedByController {

	private final com.popo.repository.WorksPredefinedByRepository worksPredefinedByRepository;
	private final WorkPredefinedByService workPredefinedByService;

	@DeleteMapping("/{id}")
	public Status delete(@PathVariable(name = "id") Long id) {
		worksPredefinedByRepository.deleteById(id);
		return Status.ok("WorksPredefinedBy deleted !!", null);
	}

	// @PostMapping
	// public Status add(@RequestBody WorksPredefinedBy worksPredefinedBy) {
	// WorksPredefinedBy res = worksPredefinedByRepository.save(worksPredefinedBy);
	// return Status.ok("WorksPredefinedBy inserted succesfully", res);
	// }

	// Ajout des travaux predefinies
	@Transactional
	@PostMapping("/all")
	public Status addAll(@RequestBody List<WorksPredefinedBy> worksPredefinedBys) {
		List<WorksPredefinedBy> res = workPredefinedByService.saveAll(worksPredefinedBys, null);
		return Status.ok("WorksPredefinedBy inserted succesfully", res);
	}

	@GetMapping
	public Status getAll() {
		List<WorksPredefinedBy> res = worksPredefinedByRepository.findAll();
		return Status.ok("", res);
	}

	@GetMapping("/{page}/count/{count}")
	public Status getElements(@PathVariable("page") int page, @PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(page, count);
		List<WorksPredefinedBy> res = worksPredefinedByRepository.findAll(pageable).getContent();
		return Status.ok("", res);
	}

	@GetMapping("/page/{count}")
	public Status getPageCount(@PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(0, count);
		int totalPage = worksPredefinedByRepository.findAll(pageable).getTotalPages();
		return Status.ok("", totalPage);
	}

	@PutMapping
	public Status update(@RequestBody WorksPredefinedBy worksPredefinedBy) {
		WorksPredefinedBy res = workPredefinedByService.update(worksPredefinedBy);
		return Status.ok("WorksPredefinedBy updated succesfully", res);
	}

	@ExceptionHandler(Exception.class)
	public Status handleException(Exception e) {
		e.printStackTrace();
		return Status.error(e.getMessage(), null);
	}

}
