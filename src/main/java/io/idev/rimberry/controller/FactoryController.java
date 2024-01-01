package io.idev.rimberry.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.idev.rimberry.StoreApiConstants;
import io.idev.rimberry.dto.Page;
import io.idev.rimberry.service.FactoryServiceImpl;
import io.idev.storeapi.api.controller.FactoryApi;
import io.idev.storeapi.model.FactoryDto;
import io.idev.storeapi.model.Response;
import io.idev.storeapi.model.SupplierDto;

@RequestMapping(StoreApiConstants.API_BASE_URI)
@RestController
public class FactoryController implements FactoryApi {


	private FactoryServiceImpl factoryServiceImpl;

	public FactoryController(FactoryServiceImpl factoryServiceImpl) {
		this.factoryServiceImpl = factoryServiceImpl;
	}

	@Override
	public ResponseEntity<Response> addFactory(@Valid FactoryDto factoryDto) {
		this.factoryServiceImpl.add(factoryDto);
		Response response = new Response().code(200).message("Factory Added!").details(null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@GetMapping("/factory/factories")
	public ResponseEntity<Page<FactoryDto>> getFactories(@RequestParam int page) {
		org.springframework.data.domain.Page<FactoryDto> productPage = this.factoryServiceImpl.getByPage(--page);
		@SuppressWarnings("unchecked")
		Page<FactoryDto> pageObject = new Page<FactoryDto>().currentPage(productPage.getNumber() + 1)
				.totalPages(productPage.getTotalPages()).content(productPage.getContent())
				.totalElements(productPage.getNumberOfElements()).hasNext(productPage.hasNext())
				.hasPrevious(productPage.hasPrevious());
		return new ResponseEntity<Page<FactoryDto>>(pageObject, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> deleteFactory(Integer factoryId) {
		this.factoryServiceImpl.delete(factoryId);
		Response response = new Response().code(200).message(String.format("Factory with ID %d Deleted", factoryId))
				.details(null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> editFactory(@Valid FactoryDto factoryDto) {
		// TODO Auto-generated method stub
		return FactoryApi.super.editFactory(factoryDto);
	}

	@Override
	public ResponseEntity<List<FactoryDto>> searchFactory(@NotNull @Valid String text) {
		// TODO Auto-generated method stub
		return FactoryApi.super.searchFactory(text);
	}

}
