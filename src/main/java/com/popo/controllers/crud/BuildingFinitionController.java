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

import com.popo.models.BuildingFinition;
import com.popo.services.BuildingFinitionService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/buildingFinition")
@RequiredArgsConstructor
public class BuildingFinitionController {

	private final com.popo.repository.BuildingFinitionRepository buildingFinitionRepository;
	private final BuildingFinitionService buildingFinitionService;

	@DeleteMapping("/{id}")
	public Status delete(@PathVariable(name = "id") Long id) {
		buildingFinitionRepository.deleteById(id);
		return Status.ok("BuildingFinition deleted !!", null);
	}

	@PostMapping
	public Status add(@RequestBody BuildingFinition buildingFinition) {
		BuildingFinition res = buildingFinitionService.saveBuild(buildingFinition);
		return Status.ok("BuildingFinition inserted succesfully", res);
	}

	@GetMapping
	public Status getAll() {
		List<BuildingFinition> res = buildingFinitionRepository.findAll();
		return Status.ok("", res);
	}

	@GetMapping("/{page}/count/{count}")
	public Status getElements(@PathVariable("page") int page, @PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(page, count);
		List<BuildingFinition> res = buildingFinitionRepository.findAll(pageable).getContent();
		return Status.ok("", res);
	}

	@GetMapping("/page/{count}")
	public Status getPageCount(@PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(0, count);
		int totalPage = buildingFinitionRepository.findAll(pageable).getTotalPages();
		return Status.ok("", totalPage);
	}

	@PutMapping
	public Status update(@RequestBody BuildingFinition buildingFinition) {
		if (buildingFinition.getPercent() < 0)
			throw new RuntimeException("Invalid percent");
		BuildingFinition res = buildingFinitionRepository.save(buildingFinition);
		return Status.ok("BuildingFinition updated succesfully", res);
	}

	@ExceptionHandler(Exception.class)
	public Status handleException(Exception e) {
		e.printStackTrace();
		return Status.error(e.getMessage(), null);
	}

}
