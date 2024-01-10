package io.idev.rimberry.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import io.idev.rimberry.StoreApiConstants;
import io.idev.rimberry.dto.Page;
import io.idev.rimberry.service.SupplierServiceImpl;
import io.idev.storeapi.api.controller.SupplierApi;
import io.idev.storeapi.model.FactoryDto;
import io.idev.storeapi.model.ProductDto;
import io.idev.storeapi.model.Response;
import io.idev.storeapi.model.SupplierDto;

@RequestMapping(StoreApiConstants.API_BASE_URI)
@RestController
public class SupplierController implements SupplierApi {

	
	private SupplierServiceImpl supplierServiceImpl;
	
	
	public SupplierController(SupplierServiceImpl supplierServiceImpl) {
		this.supplierServiceImpl = supplierServiceImpl;
	}
	
	@Override
	public Optional<NativeWebRequest> getRequest() {
		// TODO Auto-generated method stub
		return SupplierApi.super.getRequest();
	}

	@Override
	public ResponseEntity<Response> addSupplier(@Valid SupplierDto supplierDto) {
		this.supplierServiceImpl.add(supplierDto);
		Response response = new Response().code(200).message("New Supplier Added!").details(null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	
	@GetMapping("/supplier/suppliers")
	public ResponseEntity<Page<SupplierDto>> getProducts(@RequestParam int page) {
		org.springframework.data.domain.Page<SupplierDto> productPage = this.supplierServiceImpl.getByPage(--page);
		@SuppressWarnings("unchecked")
		Page<SupplierDto> pageObject = new Page<SupplierDto>().currentPage(productPage.getNumber() + 1)
				.totalPages(productPage.getTotalPages()).content(productPage.getContent())
				.totalElements(productPage.getNumberOfElements()).hasNext(productPage.hasNext())
				.hasPrevious(productPage.hasPrevious());
		return new ResponseEntity<Page<SupplierDto>>(pageObject, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Response> deleteSupplier(Integer supplierId) {
		this.supplierServiceImpl.delete(supplierId);
		Response response = new Response().code(200).message(String.format("Supplier with ID [%d] is Deleted", supplierId))
				.details(null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> editSupplier(@Valid SupplierDto supplierDto) {
		this.supplierServiceImpl.edit(supplierDto);
		Response response = new Response().code(200).message("Supplier Has Been Updated");
		return new ResponseEntity<Response>(response, HttpStatus.OK); 
	}

	@Override
	public ResponseEntity<List<SupplierDto>> searchSupplier(@NotNull @Valid String text) {
		List<SupplierDto> lookupResult = this.supplierServiceImpl.lookup(text);
		return new ResponseEntity<List<SupplierDto>>(lookupResult, HttpStatus.OK);
	}

}
