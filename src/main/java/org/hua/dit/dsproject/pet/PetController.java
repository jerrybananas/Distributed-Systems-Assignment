package org.hua.dit.dsproject.pet;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.hua.dit.dsproject.user.UserDetails;

// Using Spring Boot's built-in validation
import java.util.List;
import java.util.Optional;

/**
 * @author it21542 - Antonis Rouseas
 * REST Controller for Pet management operations
 */
@RestController
@RequestMapping("/api/pets")
@Validated
@Tag(name = "Pet Management", description = "Operations for managing pets in the system")
public class PetController {
    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
        System.out.println("=== PetController initialized ===");
        System.out.println("Controller mapped to: /api/pets");
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'CITIZEN')")
    public ResponseEntity<List<Pet>> getAllPets() {
        try {
            List<Pet> pets = petService.listAllPets();
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CITIZEN')")
    public ResponseEntity<?> createPet(@RequestBody Pet pet) {
        try {
            // Get the current logged-in user's ID and set it as owner
            String currentUserId = getCurrentUserId();
            pet.setOwnerID(currentUserId);
            
            Pet savedPet = petService.newPet(pet);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPet);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the pet");
        }
    }

    @PutMapping("/{serialNumber}/medical")
    @PreAuthorize("hasAnyRole('ADMIN', 'VET')")
    public ResponseEntity<?> updateMedicalHistory(
            @PathVariable int serialNumber,
            @RequestBody String medicalHistory) {
        try {
            Pet updatedPet = petService.updatePetMedicalHistory(serialNumber, medicalHistory);
            return ResponseEntity.ok(updatedPet);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating medical history");
        }
    }

    @PutMapping("/{serialNumber}/approve")
    @PreAuthorize("hasRole('VET')")
    public ResponseEntity<?> approvePetRecord(@PathVariable int serialNumber) {
        System.out.println("=== APPROVE ENDPOINT CALLED for pet " + serialNumber + " ===");
        try {
            Pet approvedPet = petService.approvePetRecord(serialNumber);
            return ResponseEntity.ok(approvedPet);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while approving pet record");
        }
    }
    
    @PutMapping("/{serialNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updatePet(
            @PathVariable int serialNumber,
            @RequestBody Pet pet) {
        try {
            Pet updatedPet = petService.updatePet(serialNumber, pet);
            return ResponseEntity.ok(updatedPet);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the pet");
        }
    }

    @GetMapping("/{serialNumber}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'CITIZEN')")
    public ResponseEntity<?> getPetBySerialNumber(@PathVariable int serialNumber) {
        try {
            Optional<Pet> pet = petService.findPet(serialNumber);
            if (pet.isPresent()) {
                return ResponseEntity.ok(pet.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving the pet");
        }
    }

    @DeleteMapping("/{serialNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePet(@PathVariable int serialNumber) {
        try {
            petService.deletePet(serialNumber);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the pet");
        }
    }

    @GetMapping("/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getPetCount() {
        try {
            long count = petService.getTotalPetCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/owner/{ownerID}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CITIZEN')")
    public ResponseEntity<List<Pet>> getPetsByOwner(@PathVariable String ownerID) {
        try {
            List<Pet> pets = petService.findPetsByOwnerID(ownerID);
            return ResponseEntity.ok(pets);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search/{petIdStr}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'CITIZEN')")
    public ResponseEntity<?> getPetByIdString(@PathVariable String petIdStr) {
        try {
            Optional<Pet> pet = petService.findPetByIdString(petIdStr);
            if (pet.isPresent()) {
                return ResponseEntity.ok(pet.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while searching for the pet");
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        System.out.println("=== /api/pets/test endpoint called ===");
        return ResponseEntity.ok("PetController is working!");
    }

    @GetMapping({"/my-pets", "/mypets"})
    // @PreAuthorize("hasAnyRole('ADMIN', 'CITIZEN')")  // Temporarily disabled for testing
    public ResponseEntity<?> getMyPets() {
        System.out.println("=== /api/pets/my-pets (or mypets) endpoint called ===");
        try {
            String currentUserId = getCurrentUserId();
            System.out.println("DEBUG: Current User ID = " + currentUserId);
            List<Pet> pets = petService.findPetsByOwnerID(currentUserId);
            System.out.println("DEBUG: Found " + pets.size() + " pets for user " + currentUserId);
            return ResponseEntity.ok(pets);
        } catch (IllegalArgumentException e) {
            System.out.println("DEBUG: IllegalArgumentException: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("DEBUG: Exception: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error loading pets: " + e.getMessage());
        }
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEBUG: Authentication = " + authentication);
        if (authentication != null) {
            System.out.println("DEBUG: Principal type = " + authentication.getPrincipal().getClass().getName());
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                System.out.println("DEBUG: UserDetails ID = " + userDetails.getIdNumber());
                return userDetails.getIdNumber();
            }
        }
        throw new IllegalStateException("Unable to determine current user ID");
    }

    @GetMapping("/**")
    public ResponseEntity<String> catchAll() {
        System.out.println("=== Catch-all endpoint called for /api/pets/** ===");
        return ResponseEntity.ok("Catch-all hit - no specific endpoint matched");
    }
}