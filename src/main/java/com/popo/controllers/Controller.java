package com.popo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Connection;
import java.util.List;
import com.popo.utils.Status;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.geta.utils.upload.FileHelper;
import com.popo.connection.Connect;
import com.popo.models.Car;
import com.popo.models.Tree;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Controller {

	private final com.popo.repository.CarRepository carRepository;
	private final com.popo.repository.views.RedOnlyRepository redOnlyRepository;

	@DeleteMapping("/{id}")
	public Status delete(@PathVariable(name = "id") Long id) {
		carRepository.deleteById(id);
		return Status.ok("Car deleted !!", null);
	}

	@PostMapping
	// @Transactional
	public Status add(@RequestBody Car car) {
		for (int i = 0; i < 3; i++) {
			Car res = carRepository.save(car);
			if (i == 2)
				throw new RuntimeException("Car already exists");
		}
		return Status.ok("Car inserted succesfully", null);
	}

	@PostMapping("/file")
	public Status addFile(@RequestBody Tree tree) {
		FileHelper.upload(tree.getBase64(), "./src/main/resources/static/", "png");
		return Status.ok("Inserted ", null);
	}

	@GetMapping("/car")
	public Status get() throws Exception {
		List<Car> res = carRepository.findAll();
		return Status.ok("", res);
	}

	@GetMapping
	public Status getAll() throws Exception {
		Connection connection = Connect.getConnectionPostgresql();
		List<Tree> res = new Tree().selectAll(connection);
		connection.close();
		return Status.ok("", res);
	}

	@GetMapping("/red")
	public Status getAllRed() {
		List<?> res = redOnlyRepository.alaivo();
		System.out.println(res.size());
		return Status.ok("", res);
	}

	@GetMapping("/{page}/count/{count}")
	public Status getElements(@PathVariable("page") int page, @PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(page, count);
		List<Car> res = carRepository.findAll(pageable).getContent();
		return Status.ok("", res);
	}

	@GetMapping("/page/{count}")
	public Status getPageCount(@PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(0, count);
		int totalPage = carRepository.findAll(pageable).getTotalPages();
		return Status.ok("", totalPage);
	}

	@PutMapping
	public Status update(@RequestBody Car car) {
		Car res = carRepository.save(car);
		return Status.ok("Car updated succesfully", res);
	}

	@ExceptionHandler(Exception.class)
	public Status handleException(Exception e) {
		e.printStackTrace();
		return Status.error(e.getMessage(), null);
	}

}
