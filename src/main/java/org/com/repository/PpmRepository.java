package org.com.repository;

import org.com.entity.Nh3PPM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PpmRepository extends JpaRepository<Nh3PPM, Long> {

}
