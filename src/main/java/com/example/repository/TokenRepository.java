package com.example.repository;

import com.example.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);

    @Query("select t from Token t where t.user.id =:userId and t.expired = false or t.revoked = false ")
    List<Token> findAllValidTokenByUserId(int userId);

}
