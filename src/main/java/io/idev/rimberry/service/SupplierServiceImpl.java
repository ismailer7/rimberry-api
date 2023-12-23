package io.idev.rimberry.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import io.idev.rimberry.entities.Supplier;
import io.idev.rimberry.repos.ISupplierRepository;
import io.idev.rimberry.service.interfaces.ISupplierService;
import io.idev.storeapi.model.ProductDto;
import io.idev.storeapi.model.SupplierDto;

public class SupplierServiceImpl implements ISupplierService<SupplierDto, Integer> {

	private ISupplierRepository supplierRepository;

	private ModelMapper modelMapper;

	private static final int MAX_PER_PAGE = 5;

	public SupplierServiceImpl(ISupplierRepository supplierRepository) {
		this.supplierRepository = supplierRepository;
	}

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public SupplierDto get(Integer id) {
		Optional<Supplier> op = this.supplierRepository.findById(id);
		Supplier supplier = op.get();
		SupplierDto supplierDto = this.modelMapper.map(supplier, SupplierDto.class);
		return supplierDto;
	}

	@Override
	public SupplierDto getByName(String name) {
		Supplier supplier = this.supplierRepository.getByName(name);
		SupplierDto supplierDto = null;
		if (supplier != null) {
			supplierDto = this.modelMapper.map(supplier, SupplierDto.class);
		}
		return supplierDto;
	}

	@Override
	public List<SupplierDto> getAll() {
		List<Supplier> supplierList = this.supplierRepository.findAll();
		return supplierList.stream().filter(supplier -> !supplier.isDeleted()).map(supplier -> {
			return this.modelMapper.map(supplier, io.idev.storeapi.model.SupplierDto.class);
		}).collect(Collectors.toList());
	}

	@Override
	public void add(SupplierDto t) {
		Supplier supplier = Supplier.builder().name(t.getName()).address(t.getAddress()).cin(t.getCin()).rib(t.getRib())
				.source(t.getSource()).isDeleted(false).updated(new Date()).created(new Date()).build();
		this.supplierRepository.saveAndFlush(supplier);
	}

	@Override
	public void edit(SupplierDto t) {
		Supplier supplier = this.supplierRepository.findById(t.getId()).get();
		if (t.getName() != null && !t.getName().isEmpty()) {
			supplier.setName(t.getName());
		}
		if (t.getAddress() != null && !t.getAddress().isEmpty()) {
			supplier.setAddress(t.getAddress());
		}
		if (t.getCin() != null && !t.getCin().isEmpty()) {
			supplier.setCin(t.getCin());
		}
		if (t.getRib() != null && !t.getRib().isEmpty()) {
			supplier.setRib(t.getRib());
		}
		if (t.getSource() != null && !t.getSource().isEmpty()) {
			supplier.setSource(t.getSource());
		}
		supplier.setUpdated(new Date());
		this.supplierRepository.saveAndFlush(supplier);

	}

	@Override
	public void delete(Integer l) {
		Supplier supplier = this.supplierRepository.getById(l);
		supplier.setDeleted(true);
		this.supplierRepository.saveAndFlush(supplier);
	}

	@Override
	public Page<ProductDto> getByPage(int page) {
		Page<Supplier> pageSupplier = this.supplierRepository
				.findAllByisDeletedFalse(PageRequest.of(page, MAX_PER_PAGE));
		return pageSupplier.map(supplier -> {
			return this.modelMapper.map(supplier, io.idev.storeapi.model.ProductDto.class);
		});
	}

}
