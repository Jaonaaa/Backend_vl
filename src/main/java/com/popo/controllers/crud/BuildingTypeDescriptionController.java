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
import com.popo.models.BuildingTypeDescription;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/buildingTypeDescription")
public class BuildingTypeDescriptionController {

	@Autowired
	private com.popo.repository.BuildingTypeDescriptionRepository buildingTypeDescriptionRepository;

	@DeleteMapping("/{id}")
	public Status delete(@PathVariable(name = "id") Long id) {
		buildingTypeDescriptionRepository.deleteById(id);
		return Status.ok("BuildingTypeDescription deleted !!", null);
	}

	@PostMapping
	public Status add(@RequestBody BuildingTypeDescription buildingTypeDescription) {
		BuildingTypeDescription res = buildingTypeDescriptionRepository.save(buildingTypeDescription);
		return Status.ok("BuildingTypeDescription inserted succesfully", res);
	}

	@GetMapping
	public Status getAll() {
		List<BuildingTypeDescription> res = buildingTypeDescriptionRepository.findAll();
		return Status.ok("", res);
	}

	@GetMapping("/{page}/count/{count}")
	public Status getElements(@PathVariable("page") int page, @PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(page, count);
		List<BuildingTypeDescription> res = buildingTypeDescriptionRepository.findAll(pageable).getContent();
		return Status.ok("", res);
	}

	@GetMapping("/page/{count}")
	public Status getPageCount(@PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(0, count);
		int totalPage = buildingTypeDescriptionRepository.findAll(pageable).getTotalPages();
		return Status.ok("", totalPage);
	}

	@PutMapping
	public Status update(@RequestBody BuildingTypeDescription buildingTypeDescription) {
		BuildingTypeDescription res = buildingTypeDescriptionRepository.save(buildingTypeDescription);
		return Status.ok("BuildingTypeDescription updated succesfully", res);
	}

	@ExceptionHandler(Exception.class)
	public Status handleException(Exception e) {
		e.printStackTrace();
		return Status.error(e.getMessage(), null);
	}

}
