package io.idev.rimberry.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import io.idev.rimberry.entities.Factory;
import io.idev.rimberry.entities.FactoryOwner;
import io.idev.rimberry.repos.IFactoryOwnerRepository;
import io.idev.rimberry.repos.IFactoryRepository;
import io.idev.rimberry.service.interfaces.IFactoryService;
import io.idev.storeapi.model.FactoryDto;

public class FactoryServiceImpl implements IFactoryService<FactoryDto, Integer> {

	private IFactoryRepository factoryRepository;

	private IFactoryOwnerRepository factoryOwnerRepository;

	private ModelMapper modelMapper;

	private static final int MAX_PER_PAGE = 5;

	public FactoryServiceImpl(IFactoryRepository factoryRepository, IFactoryOwnerRepository factoryOwnerRepository) {
		this.factoryRepository = factoryRepository;
		this.factoryOwnerRepository = factoryOwnerRepository;
	}

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public FactoryDto get(Integer id) {
		Optional<Factory> op = this.factoryRepository.findById(id);
		Factory factory = op.get();
		FactoryDto factoryDto = this.modelMapper.map(factory, FactoryDto.class);
		return factoryDto;
	}

	@Override
	public FactoryDto getByName(String name) {
		Factory factory = this.factoryRepository.getByName(name);
		FactoryDto factoryDto = null;
		if (factory != null) {
			factoryDto = this.modelMapper.map(factory, FactoryDto.class);
		}
		return factoryDto;
	}

	@Override
	public List<FactoryDto> getAll() {
		List<Factory> factoryList = this.factoryRepository.findAll();
		return factoryList.stream().filter(factory -> !factory.isDeleted()).map(factory -> {
			return this.modelMapper.map(factory, io.idev.storeapi.model.FactoryDto.class);
		}).collect(Collectors.toList());
	}

	@Override
	public void add(FactoryDto t) {
		Factory factory = Factory.builder().name(t.getName()).location(t.getLocation()).created(new Date())
				.updated(new Date()).build();
		this.factoryRepository.saveAndFlush(factory);
	}

	@Override
	public void edit(FactoryDto t) {
		Factory factory = this.factoryRepository.findById(t.getId()).get();
		if (t.getName() != null && !t.getName().isEmpty()) {
			factory.setName(t.getName());
		}
		if (t.getLocation() != null && !t.getLocation().isEmpty()) {
			factory.setLocation(t.getLocation());
		}
		factory.setUpdated(new Date());
		this.factoryRepository.saveAndFlush(factory);

	}

	@Override
	public void delete(Integer l) {
		Factory factory = this.factoryRepository.getById(l);
		factory.setDeleted(true);
		this.factoryRepository.saveAndFlush(factory);
	}

	@Override
	public Page<FactoryDto> getByPage(int page) {
		Page<Factory> pageFactory = this.factoryRepository.findAllByisDeletedFalse(PageRequest.of(page, MAX_PER_PAGE));
		return pageFactory.map(factory -> {
			return this.modelMapper.map(factory, io.idev.storeapi.model.FactoryDto.class);
		});
	}

	@Override
	public List<FactoryDto> lookup(String text) {
		List<Factory> lookupResult = null;
		if (text.matches("-?\\d+(\\.\\d+)?")) {
			lookupResult = this.factoryRepository.lookupById(Integer.valueOf(text));
		} else {
			if (text.contains("@")) {
				// lookup by owner email.
				List<FactoryOwner> owners = this.factoryOwnerRepository.lookupByEmail(text);
				lookupResult = owners.stream().map(owner -> {
					Integer ownerId = owner.getId();
					Factory factory = this.factoryRepository.findByOwnerId(ownerId);
					return factory;
				}).collect(Collectors.toList());
			} else {
				lookupResult = this.factoryRepository.lookupByNAme(text);
				if (lookupResult == null || lookupResult.isEmpty()) {
					// lookup by owner name
					List<FactoryOwner> owners = this.factoryOwnerRepository.lookupByName(text);
					lookupResult = owners.stream().map(owner -> {
						Integer ownerId = owner.getId();
						Factory factory = this.factoryRepository.findByOwnerId(ownerId);
						return factory;
					}).collect(Collectors.toList());
				}
				if (lookupResult == null || lookupResult.isEmpty()) {
					lookupResult = this.factoryRepository.lookupByLocation(text);
				}
			}
		}
		return lookupResult.stream().map(factory -> {
			return this.modelMapper.map(factory, FactoryDto.class);
		}).collect(Collectors.toList());
	}

}
