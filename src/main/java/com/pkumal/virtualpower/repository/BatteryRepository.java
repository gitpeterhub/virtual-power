package com.pkumal.virtualpower.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pkumal.virtualpower.modal.Battery;

public interface BatteryRepository extends CrudRepository<Battery, Long> {

	@Query("Select b from Battery b where b.postCode between ?1 and ?2")
	List<Battery> findByPostCodes(String postCode1, String postCode2);
}
