package io.idev.rimberry.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import io.idev.rimberry.StoreApiConstants;
import io.idev.storeapi.api.controller.SupplierApi;
import io.idev.storeapi.model.Response;
import io.idev.storeapi.model.SupplierDto;

@RequestMapping(StoreApiConstants.API_BASE_URI)
@RestController
public class SupplierController implements SupplierApi {

	@Override
	public Optional<NativeWebRequest> getRequest() {
		// TODO Auto-generated method stub
		return SupplierApi.super.getRequest();
	}

	@Override
	public ResponseEntity<Response> addSupplier(@Valid SupplierDto supplierDto) {
		// TODO Auto-generated method stub
		return SupplierApi.super.addSupplier(supplierDto);
	}

	@Override
	public ResponseEntity<Response> deleteSupplier(Integer supplierId) {
		// TODO Auto-generated method stub
		return SupplierApi.super.deleteSupplier(supplierId);
	}

	@Override
	public ResponseEntity<Response> editSupplier(@Valid SupplierDto supplierDto) {
		// TODO Auto-generated method stub
		return SupplierApi.super.editSupplier(supplierDto);
	}

	@Override
	public ResponseEntity<List<SupplierDto>> searchSupplier(@NotNull @Valid String text) {
		// TODO Auto-generated method stub
		return SupplierApi.super.searchSupplier(text);
	}

}
