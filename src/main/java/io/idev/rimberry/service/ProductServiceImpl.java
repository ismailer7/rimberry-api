package io.idev.rimberry.service;

import java.time.ZoneOffset;
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
import io.idev.storapi.enums.ProductType;
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
		Product product = Product.builder().name(t.getName()).type(t.getType()).isDeleted(false).created(new Date())
				.updated(new Date()).build();
		this.productRepository.saveAndFlush(product);
	}

	@Override
	public void edit(ProductDto t) {
		Product product = this.productRepository.findById(t.getId()).get();
		if (t.getName() != null && !t.getName().isEmpty()) {
			product.setName(t.getName());
		}
		if (t.getType() != null && !t.getType().isEmpty()) {
			product.setType(t.getType());
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
			ProductDto productDto = this.modelMapper.map(product, io.idev.storeapi.model.ProductDto.class);
			productDto.setCreated(
					product.getCreated() != null ? product.getCreated().toInstant().atOffset(ZoneOffset.UTC) : null);
			switch (productDto.getType()) {
			case "0":
				productDto.setType("FRAMBOISE");
				break;
			case "1":
				productDto.setType("MURE");
				break;
			case "2":
				productDto.setType("MYRTILLE");
				break;
			case "3":
				productDto.setType("FRAISE");
				break;
			case "4":
				productDto.setType("POIVRON GREEN");
				break;
			case "5":
				productDto.setType("POIVRON RED");
				break;
			case "6":
				productDto.setType("POIVRON YELLOW");
				break;
			}
			return productDto;
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
			if(productList == null || productList.isEmpty()) {
				//productList = this.productRepository.lookupByType(text);
				if(ProductType.FRAAMBOISE.name().contains(text)) {
					productList = this.productRepository.lookupByType(ProductType.FRAAMBOISE.ordinal());
				}
				if(ProductType.FRAISE.name().contains(text)) {
					productList = this.productRepository.lookupByType(ProductType.FRAISE.ordinal());
								}
				if(ProductType.MURE.name().contains(text)) {
					productList = this.productRepository.lookupByType(ProductType.MURE.ordinal());
				}
				if(ProductType.MYRTILLE.name().contains(text)) {
					productList = this.productRepository.lookupByType(ProductType.MYRTILLE.ordinal());
				}
				if(ProductType.POIVRON_GREEN.name().contains(text)) {
					productList = this.productRepository.lookupByType(ProductType.POIVRON_GREEN.ordinal());
				}
				if(ProductType.POIVRON_RED.name().contains(text)) {
					productList = this.productRepository.lookupByType(ProductType.POIVRON_RED.ordinal());
				}
				if(ProductType.POIVRON_YELLOW.name().contains(text)) {
					productList = this.productRepository.lookupByType(ProductType.POIVRON_YELLOW.ordinal());
				}
			}
		}
		return productList.stream().map(product -> {
			return this.modelMapper.map(product, ProductDto.class);
		}).collect(Collectors.toList());
	}

}
