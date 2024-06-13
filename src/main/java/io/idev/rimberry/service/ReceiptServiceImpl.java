package io.idev.rimberry.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import io.idev.rimberry.entities.Receipt;
import io.idev.rimberry.repos.IReceiptRepository;
import io.idev.rimberry.service.interfaces.IFactoryService;
import io.idev.storeapi.model.ReceiptDto;

@Service
public class ReceiptServiceImpl implements IFactoryService<ReceiptDto, Integer> {

	private IReceiptRepository receiptRepository;

	private ModelMapper modelMapper;

	public ReceiptServiceImpl(IReceiptRepository receiptRepository) {
		this.receiptRepository = receiptRepository;
	}

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public ReceiptDto get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReceiptDto getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReceiptDto> getAll() {
		List<Receipt> receiptList = this.receiptRepository.findAll();
		return receiptList.stream().map(receipt -> {
			return this.modelMapper.map(receipt, io.idev.storeapi.model.ReceiptDto.class);
		}).collect(Collectors.toList());
	}

	@Override
	public void add(ReceiptDto t) {
		Receipt receipt = Receipt.builder().date(t.getDate()).supplierId(t.getSupplierId()).productId(t.getProductId())
				.driver(t.getDriver()).pp(t.getPp()).tare(t.getTare()).pb(t.getPb()).tp(t.getTp()).te(t.getTe()).totalTo(t.getTotalTo())
				.tb(t.getTb()).tc(t.getTc()).tarep(t.getTarep()).tare(t.getTare()).tn(t.getTn()).build();
		this.receiptRepository.saveAndFlush(receipt);
	}

	@Override
	public void edit(ReceiptDto t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Integer l) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<ReceiptDto> getByPage(int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReceiptDto> lookup(String text) {
		// TODO Auto-generated method stub
		return null;
	}

}