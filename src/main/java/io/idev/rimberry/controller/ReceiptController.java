package io.idev.rimberry.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.idev.rimberry.StoreApiConstants;
import io.idev.rimberry.service.ReceiptServiceImpl;
import io.idev.storeapi.api.controller.ReceiptApi;
import io.idev.storeapi.model.ReceiptDto;
import io.idev.storeapi.model.Response;

@RequestMapping(StoreApiConstants.API_BASE_URI)
@RestController
public class ReceiptController implements ReceiptApi {

	private ReceiptServiceImpl receiptServiceImpl;
	
	public ReceiptController(ReceiptServiceImpl receiptServiceImpl) {
		this.receiptServiceImpl = receiptServiceImpl;
	}
	
	@Override
	public ResponseEntity<Response> addReceipt(@Valid ReceiptDto receiptDto) {
		this.receiptServiceImpl.add(receiptDto);
		Response response = new Response().code(200).message("New Receipt Added!").details(null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<List<ReceiptDto>> getReceipts() {
		List<ReceiptDto> receiptList = this.receiptServiceImpl.getAll();
		return new ResponseEntity<List<ReceiptDto>>(receiptList, HttpStatus.OK);
	}
	
}
