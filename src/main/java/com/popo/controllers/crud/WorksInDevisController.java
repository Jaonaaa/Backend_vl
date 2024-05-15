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
import com.popo.models.WorksInDevis;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/worksInDevis")
public class WorksInDevisController {

	@Autowired
	private com.popo.repository.WorksInDevisRepository worksInDevisRepository;

	@DeleteMapping("/{id}")
	public Status delete(@PathVariable(name = "id") Long id) {
		worksInDevisRepository.deleteById(id);
		return Status.ok("WorksInDevis deleted !!", null);
	}

	@PostMapping
	public Status add(@RequestBody WorksInDevis worksInDevis) {
		WorksInDevis res = worksInDevisRepository.save(worksInDevis);
		return Status.ok("WorksInDevis inserted succesfully", res);
	}

	@GetMapping
	public Status getAll() {
		List<WorksInDevis> res = worksInDevisRepository.findAll();
		return Status.ok("", res);
	}

	@GetMapping("/{page}/count/{count}")
	public Status getElements(@PathVariable("page") int page, @PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(page, count);
		List<WorksInDevis> res = worksInDevisRepository.findAll(pageable).getContent();
		return Status.ok("", res);
	}

	@GetMapping("/page/{count}")
	public Status getPageCount(@PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(0, count);
		int totalPage = worksInDevisRepository.findAll(pageable).getTotalPages();
		return Status.ok("", totalPage);
	}

	@PutMapping
	public Status update(@RequestBody WorksInDevis worksInDevis) {
		WorksInDevis res = worksInDevisRepository.save(worksInDevis);
		return Status.ok("WorksInDevis updated succesfully", res);
	}

	@ExceptionHandler(Exception.class)
	public Status handleException(Exception e) {
		e.printStackTrace();
		return Status.error(e.getMessage(), null);
	}

}
