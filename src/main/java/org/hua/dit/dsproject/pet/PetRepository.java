package org.hua.dit.dsproject.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    @Query(value = "select count(n) from PET n where n.serialNumber= :serialNumber")
    int PetExists(@Param("serialNumber") int serialNumber);

    @Query(value = "SELECT * FROM pet WHERE owner_id = :ownerID", nativeQuery = true)
    List<Pet> findPetsByOwnerID(@Param("ownerID") String ownerID);

    @Query(value = "SELECT COALESCE(MAX(p.serialNumber), 0) FROM PET p")
    int findMaxSerialNumber();

    @Query(value = "SELECT p FROM PET p WHERE LPAD(p.serialNumber, 9, '0') = :petIdStr")
    Optional<Pet> findByPetIdStr(@Param("petIdStr") String petIdStr);
}
