package org.hua.dit.dsproject.user;

import org.hua.dit.dsproject.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Define our repository
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserName(String username);

    @Query(value = "select n from PET n where n.ownerID= :id")
    List<Pet> findPets(@Param("id") String ownerID);

    @Query(value = "select count(n) from User n where n.idNumber= :id")
    int UserExists(@Param("id") String ownerID);
}