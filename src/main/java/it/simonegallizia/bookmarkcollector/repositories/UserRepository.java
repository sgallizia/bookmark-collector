package it.simonegallizia.bookmarkcollector.repositories;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.simonegallizia.bookmarkcollector.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);
	public Optional<User> findByUser(String email);

	

}
