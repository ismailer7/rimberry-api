package io.idev.rimberry.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.idev.rimberry.entities.Receipt;

@Repository
public interface IReceiptRepository extends JpaRepository<Receipt, Integer> {

}
