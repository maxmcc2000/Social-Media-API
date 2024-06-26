package com.cooksys.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.app.entities.Hashtag;

import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long>{
	Optional<Hashtag> findByLabel(String label);

	boolean existsByLabel(String label);
}
