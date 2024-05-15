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

import java.io.File;
import java.util.List;
import com.popo.utils.Status;

import lombok.RequiredArgsConstructor;

import com.geta.utils.upload.FileHelper;
import com.popo.models.Works;
import com.popo.models.modelsUtilities.FileBuildingWorkDevis;
import com.popo.models.temp.DevisImport;
import com.popo.models.temp.MaisonTravaux;
import com.popo.models.temp.PaiementImport;
import com.popo.services.ImportService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/works")
@RequiredArgsConstructor
public class WorksController {

	private final com.popo.repository.WorksRepository worksRepository;
	private final ImportService importService;

	@PostMapping("/file")
	public Status addFile(@RequestBody FileBuildingWorkDevis file) {
		List<MaisonTravaux> maisonTravauxs = null;
		List<DevisImport> imports = null;

		try {
			File building_work = new File(FileHelper.upload(file.getBuilding_work(), "./", "csv"));
			Thread.sleep(50);
			File devis = new File(FileHelper.upload(file.getDevis(), "./", "csv"));
			maisonTravauxs = MaisonTravaux.getMaisonTravauxsFromCSV(building_work.getAbsolutePath());
			imports = DevisImport.getDevisImportsFromCSV(devis.getAbsolutePath());
			importService.importDevisAndMaisonsTravaux(maisonTravauxs, imports);

		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException(e);
		}

		return Status.ok("Data inserted succesfully", null);

	}

	@DeleteMapping("/{id}")
	public Status delete(@PathVariable(name = "id") Long id) {
		worksRepository.deleteById(id);
		return Status.ok("Works deleted !!", null);
	}

	@PostMapping
	public Status add(@RequestBody Works works) {
		Works res = worksRepository.save(works);
		return Status.ok("Works inserted succesfully", res);
	}

	@GetMapping
	public Status getAll() {
		List<Works> res = worksRepository.findAll();
		return Status.ok("", res);
	}

	@GetMapping("/{page}/count/{count}")
	public Status getElements(@PathVariable("page") int page, @PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(page, count);
		List<Works> res = worksRepository.findAll(pageable).getContent();
		return Status.ok("", res);
	}

	@GetMapping("/page/{count}")
	public Status getPageCount(@PathVariable("count") int count) {
		Pageable pageable = PageRequest.of(0, count);
		int totalPage = worksRepository.findAll(pageable).getTotalPages();
		return Status.ok("", totalPage);
	}

	@PutMapping
	public Status update(@RequestBody Works works) {
		Works res = worksRepository.save(works);
		return Status.ok("Works updated succesfully", res);
	}

	@ExceptionHandler(Exception.class)
	public Status handleException(Exception e) {
		e.printStackTrace();
		return Status.error(e.getMessage(), null);
	}

}
