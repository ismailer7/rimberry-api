package io.idev.rimberry.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.idev.rimberry.entities.Product;
import io.idev.rimberry.repos.IProductRepository;
import io.idev.rimberry.service.interfaces.IProductService;
import io.idev.storeapi.model.ProductDto;

@Service
public class ProductServiceImpl implements IProductService<ProductDto, Integer> {

	IProductRepository productRepository;

	private ModelMapper modelMapper;

	private static final int MAX_PER_PAGE = 5;

	public ProductServiceImpl(IProductRepository productRespository) {
		this.productRepository = productRespository;
	}

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public ProductDto get(Integer id) {
		Optional<Product> op = this.productRepository.findById(id);
		Product product = op.get();
		ProductDto productDto = this.modelMapper.map(product, ProductDto.class);
		return productDto;
	}

	@Override
	public ProductDto getByName(String name) {
		Product product = this.productRepository.getByName(name);
		ProductDto productDto = null;
		if (product != null) {
			productDto = this.modelMapper.map(product, ProductDto.class);
		}
		return productDto;
	}

	@Override
	public List<ProductDto> getAll() {
		List<Product> productList = this.productRepository.findAll();
		return productList.stream().filter(product -> !product.isDeleted()).map(product -> {
			return this.modelMapper.map(product, io.idev.storeapi.model.ProductDto.class);
		}).collect(Collectors.toList());
	}

	@Override
	public void add(ProductDto t) {
//		Product product = Product.builder().name(t.getName()).type(t.getpType()).isDeleted(false).created(new Date())
//				.updated(new Date()).build();
		// this.productRepository.saveAndFlush(product);
	}

	@Override
	public void edit(ProductDto t) {
		Product product = this.productRepository.findById(t.getId()).get();
		if (t.getName() != null && !t.getName().isEmpty()) {
			product.setName(t.getName());
		}
		if (t.getpType() != null && !t.getpType().isEmpty()) {
			product.setType(t.getpType());
		}
		product.setUpdated(new Date());
		this.productRepository.saveAndFlush(product);
	}

	@Override
	public void delete(Integer l) {
		Product product = this.productRepository.getById(l);
		product.setDeleted(true);
		this.productRepository.saveAndFlush(product);
	}

	@Override
	public Page<ProductDto> getByPage(int page) {
		Page<Product> pageProduct = this.productRepository.findAllByisDeletedFalse(PageRequest.of(page, MAX_PER_PAGE));
		return pageProduct.map(product -> {
			return this.modelMapper.map(product, io.idev.storeapi.model.ProductDto.class);
		});
	}

	@Override
	public List<ProductDto> lookup(String text) {
		// TODO implement lookup feature and adding criteria.
		List<Product> productList = null;
		if (text.matches("-?\\d+(\\.\\d+)?")) {
			productList = this.productRepository.lookupById(Integer.valueOf(text));
		} else {
			productList = this.productRepository.lookupByName(text);
		}
		return productList.stream().map(product -> {
			return this.modelMapper.map(product, ProductDto.class);
		}).collect(Collectors.toList());
	}

}
