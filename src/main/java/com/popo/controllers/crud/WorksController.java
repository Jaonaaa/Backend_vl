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
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.popo.utils.Status;

import lombok.RequiredArgsConstructor;

import com.geta.utils.reader.CsvReader;
import com.geta.utils.upload.FileHelper;
import com.opencsv.CSVWriter;
import com.popo.models.Devis;
import com.popo.models.Works;
import com.popo.models.modelsUtilities.FileBuildingWorkDevis;
import com.popo.models.temp.DevisImport;
import com.popo.models.temp.MaisonTravaux;
import com.popo.models.temp.PaiementImport;
import com.popo.repository.DevisRepository;
import com.popo.services.ImportService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/works")
@RequiredArgsConstructor
public class WorksController {

	private final com.popo.repository.WorksRepository worksRepository;
	private final ImportService importService;
	private final DevisRepository devisRepository;

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

	@GetMapping("/csv")
	public Object getCSV() throws Exception {
		List<Devis> devis = devisRepository.findAll();
		String filename = "out_file";
		File file = createFile(filename, "csv");
		export(file, devis);
		///
		Path filePath = Paths.get("./" + filename + ".csv");
		Resource resource = new InputStreamResource(Files.newInputStream(filePath));

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
				.body(resource);
	}

	public void export(File csvFile, List<Devis> devis) {
		try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {

			// Writing header
			String[] header = { "Lieu Debut", "Creation date" };
			writer.writeNext(header);

			// Writing data rows
			for (Devis dev : devis) {
				String[] data = {
						dev.lieu_label,
						dev.getBegin_date().toString(),
				};
				writer.writeNext(data);
			}
			System.out.println("CSV file created successfully!");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File createFile(String filename, String extension) {
		// Create a File object
		File file = new File(filename + "." + extension);
		try {
			// Create the file
			if (file.createNewFile()) {
				System.out.println("File created: " + file.getName());
			} else {
				if (file.exists()) {
					try (FileWriter writer = new FileWriter(file)) {
						writer.write("");
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return file;
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
