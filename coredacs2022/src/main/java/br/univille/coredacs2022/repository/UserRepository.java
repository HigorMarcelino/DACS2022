package br.univille.coredacs2022.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.coredacs2022.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUser(String user);
    Optional<User> findByUserAndSenha(String user, String senha);
    
}
