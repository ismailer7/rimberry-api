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
import io.idev.storeapi.api.controller.FactoryApi;
import io.idev.storeapi.model.FactoryDto;
import io.idev.storeapi.model.Response;

@RequestMapping(StoreApiConstants.API_BASE_URI)
@RestController
public class FactoryController implements FactoryApi {

	@Override
	public Optional<NativeWebRequest> getRequest() {
		// TODO Auto-generated method stub
		return FactoryApi.super.getRequest();
	}

	@Override
	public ResponseEntity<Response> addFactory(@Valid FactoryDto factoryDto) {
		// TODO Auto-generated method stub
		return FactoryApi.super.addFactory(factoryDto);
	}

	@Override
	public ResponseEntity<Response> deleteFactory(Integer factoryId) {
		// TODO Auto-generated method stub
		return FactoryApi.super.deleteFactory(factoryId);
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
