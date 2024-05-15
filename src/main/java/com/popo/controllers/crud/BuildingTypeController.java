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

import com.popo.models.BuildingType;
import com.popo.services.BuildingTypeService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/buildingType")
@RequiredArgsConstructor
public class BuildingTypeController {

	private final com.popo.repository.BuildingTypeRepository buildingTypeRepository;
	private final BuildingTypeService buildingTypeService;

	@DeleteMapping("/{id}")
	public Status delete(@PathVariable(name = "id") Long id) {
		buildingTypeRepository.deleteById(id);
		return Status.ok("BuildingType deleted !!", null);
	}

	@Transactional
	@PostMapping
	public Status add(@RequestBody BuildingType buildingType) {
		BuildingType res = buildingTypeService.saveBuild(buildingType);
		return Status.ok("BuildingType inserted succesfully", res);
	}

	@GetMapping
	public Status getAll() {
		List<BuildingType> res = buildingTypeService.getAll();
		return Status.ok("", res);
	}

	@GetMapping("/{page}/count/{count}")
	public Status getElements(@PathVariable("page") int page, @PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(page, count);
		List<BuildingType> res = buildingTypeRepository.findAll(pageable).getContent();
		return Status.ok("", res);
	}

	@GetMapping("/page/{count}")
	public Status getPageCount(@PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(0, count);
		int totalPage = buildingTypeRepository.findAll(pageable).getTotalPages();
		return Status.ok("", totalPage);
	}

	@PutMapping
	public Status update(@RequestBody BuildingType buildingType) {
		BuildingType res = buildingTypeRepository.save(buildingType);
		return Status.ok("BuildingType updated succesfully", res);
	}

	@ExceptionHandler(Exception.class)
	public Status handleException(Exception e) {
		e.printStackTrace();
		return Status.error(e.getMessage(), null);
	}

}
