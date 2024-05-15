package com.popo.UserAuth.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.popo.UserAuth.Enum.Role;
import com.popo.UserAuth.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    public Optional<User> findByNumero(String numero);

    public List<User> findAllByRoles(Role role);

}
