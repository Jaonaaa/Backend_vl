package com.popo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import com.popo.utils.Status;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;

import com.geta.utils.reader.CsvReader;
import com.geta.utils.reader.ExcelReader;
import com.geta.utils.upload.FileHelper;
import com.popo.connection.Connect;
import com.popo.models.TeoAloha.Car;
import com.popo.models.TeoAloha.Tree;

// import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Controller {

	// private final com.popo.repository.CarRepository carRepository;
	// private final com.popo.repository.views.RedOnlyRepository redOnlyRepository;
	// private final TaskRepository taskRepository;

	@GetMapping("/download/{fileName}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws Exception {
		Path filePath = Paths.get(fileName);
		Resource resource = new InputStreamResource(Files.newInputStream(filePath));

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(resource);

	}

	// @GetMapping("/count")
	// public Status count() {
	// return Status.error("Count car", carRepository.count());

	// }

	// @DeleteMapping("/{id}")
	// public Status delete(@PathVariable(name = "id") Long id) {
	// carRepository.deleteById(id);
	// return Status.ok("Car deleted !!", null);
	// }

	// @PostMapping
	// // @Transactional
	// public Status add(@RequestBody Car car) {
	// for (int i = 0; i < 3; i++) {
	// Car res = carRepository.save(car);
	// if (i == 2)
	// throw new RuntimeException("Car already exists");
	// }
	// return Status.ok("Car inserted succesfully", null);
	// }

	@PostMapping("/file")
	public Status addFile(@RequestBody Tree tree) {
		FileHelper.upload(tree.getBase64(), "./src/main/resources/static/", "png");
		return Status.ok("Inserted ", null);
	}

	// @PostMapping("/task")
	// public Status addTask(@RequestBody Task task) {
	// System.out.println(task.toString());

	// Optional<Task> parent = taskRepository.findById(task.getParentTaskId());
	// if (parent.isPresent())
	// task.setParentTask(parent.get());
	// Task t = taskRepository.save(task);
	// return Status.ok("Inserted ", t);
	// }

	// @GetMapping("/task")
	// public Status addTask() {
	// List<Task> t = taskRepository.findAll();
	// return Status.ok("Fetched ", t);
	// }

	// @GetMapping("/car")
	// public Status get() throws Exception {
	// List<Car> res = carRepository.findAll();
	// return Status.ok("", res);
	// }

	@GetMapping
	public Status getAll() throws Exception {
		Connection connection = Connect.getConnectionPostgresql();
		List<Tree> res = new Tree().selectAll(connection);

		// CsvReader.export("test", res, ",", new String[] { "id", "base64", "name" });
		ExcelReader.export("test", res, new String[] { "id", "base64", "name" });

		connection.close();
		return Status.ok("", res);
	}

	// @GetMapping("/red")
	// public Status getAllRed() {
	// List<?> res = redOnlyRepository.alaivo();
	// System.out.println(res.size());
	// return Status.ok("", res);
	// }

	// @GetMapping("/{page}/count/{count}")
	// public Status getElements(@PathVariable("page") int page,
	// @PathVariable("count") int count) {
	// Pageable pageable = PageRequest.of(page, count);
	// List<Car> res = carRepository.findAll(pageable).getContent();
	// return Status.ok("", res);
	// }

	// @GetMapping("/page/{count}")
	// public Status getPageCount(@PathVariable("count") int count) {
	// Pageable pageable = PageRequest.of(0, count);
	// int totalPage = carRepository.findAll(pageable).getTotalPages();
	// return Status.ok("", totalPage);
	// }

	// @PutMapping
	// public Status update(@RequestBody Car car) {
	// Car res = carRepository.save(car);
	// return Status.ok("Car updated succesfully", res);
	// }

	@ExceptionHandler(Exception.class)
	public Status handleException(Exception e) {
		e.printStackTrace();
		return Status.error(e.getMessage(), null);
	}

}
