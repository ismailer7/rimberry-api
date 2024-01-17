package io.idev.rimberry.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.idev.rimberry.StoreApiConstants;
import io.idev.rimberry.dto.Page;
import io.idev.rimberry.service.ProductServiceImpl;
import io.idev.storeapi.api.controller.ProductApi;
import io.idev.storeapi.model.ProductDto;
import io.idev.storeapi.model.Response;

@RequestMapping(StoreApiConstants.API_BASE_URI)
@RestController
public class ProductController implements ProductApi {

	private static Logger logger = LogManager.getLogger(ProductController.class.toString());

	ProductServiceImpl productServiceImpl;

	public ProductController(ProductServiceImpl productServiceImpl) {
		this.productServiceImpl = productServiceImpl;
	}

	@Override
	public ResponseEntity<Response> addProduct(@Valid @RequestBody ProductDto productDto) {
		productServiceImpl.add(productDto);
		Response response = new Response().code(200).message("Product Added!").details(null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/product/products")
	public ResponseEntity<Page<ProductDto>> getProducts(@RequestParam int page) {
		org.springframework.data.domain.Page<ProductDto> productPage = this.productServiceImpl.getByPage(--page);
		@SuppressWarnings("unchecked")
		Page<ProductDto> pageObject = new Page<ProductDto>().currentPage(productPage.getNumber() + 1)
				.totalPages(productPage.getTotalPages()).content(productPage.getContent())
				.totalElements(productPage.getNumberOfElements()).hasNext(productPage.hasNext())
				.hasPrevious(productPage.hasPrevious());
		return new ResponseEntity<Page<ProductDto>>(pageObject, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<List<ProductDto>> allProducts() {
		List<ProductDto> productList = this.productServiceImpl.getAll();
		return new ResponseEntity<List<ProductDto>>(productList, HttpStatus.OK);
	}
	
	
	
	@Override
	public ResponseEntity<Response> editProduct(@Valid @RequestBody ProductDto productPayload) {
		this.productServiceImpl.edit(productPayload);
		Response response = new Response().code(200).message("Product Has Been Updated");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> deleteProduct(@PathVariable("productId") Integer productId) {
		this.productServiceImpl.delete(productId);
		Response response = new Response().code(200).message(String.format("Product with ID [%d] is Deleted", productId))
				.details(null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<List<ProductDto>> searchProduct(@RequestParam String text) {
		List<ProductDto> response = this.productServiceImpl.lookup(text);
		return new ResponseEntity<List<ProductDto>>(response, HttpStatus.OK);
	}

}
