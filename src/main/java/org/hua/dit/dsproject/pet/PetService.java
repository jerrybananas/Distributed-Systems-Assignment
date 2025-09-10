package org.hua.dit.dsproject.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author it21542 - Antonis Rouseas
 * Service layer for Pet management with validation and error handling
 */
@Service
@Validated
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> listAllPets() {
        return petRepository.findAll();
    }

    @Transactional
    public synchronized Pet newPet(Pet pet) {
        // Generate new ID automatically
        int newId = generateNextPetId();
        pet.setSerialNumber(newId);
        
        // Set the pet ID string (9-digit zero-padded format)
        pet.setPetIdStr(String.format("%09d", newId));
        
        // Validate pet data
        if (pet.getName() == null || pet.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Pet name is required");
        }
        if (pet.getRace() == null || pet.getRace().trim().isEmpty()) {
            throw new IllegalArgumentException("Pet race is required");
        }
        // Owner ID should be set by the controller from the logged-in user
        if (pet.getOwnerID() == null || pet.getOwnerID().trim().isEmpty()) {
            throw new IllegalArgumentException("Owner ID must be set by the system");
        }
        
        return petRepository.save(pet);
    }
    
    private synchronized int generateNextPetId() {
        // Find the next available ID starting from the maximum existing ID + 1
        int maxId = petRepository.findMaxSerialNumber();
        int nextId = maxId + 1;
        
        // Ensure ID is within valid range (000000001 to 999999999)
        if (nextId > 999999999) {
            // Search for gaps in the sequence starting from 1
            for (int i = 1; i <= 999999999; i++) {
                if (!petRepository.existsById(i)) {
                    return i;
                }
            }
            throw new IllegalStateException("No available pet ID slots remaining");
        }
        
        return nextId;
    }

    public boolean deletePet(int serialNumber) {
        if (!petRepository.existsById(serialNumber)) {
            throw new IllegalArgumentException("Pet with serial number " + serialNumber + " does not exist");
        }
        petRepository.deleteById(serialNumber);
        return true;
    }

    public Optional<Pet> findPet(int id) {
        return petRepository.findById(id);
    }


    @Transactional
    public Pet updatePetMedicalHistory(int serialNumber, String medicalHistory) {
        Optional<Pet> petOptional = petRepository.findById(serialNumber);
        if (!petOptional.isPresent()) {
            throw new IllegalArgumentException("Pet with serial number " + serialNumber + " does not exist");
        }
        
        Pet pet = petOptional.get();
        pet.setMedical_history(medicalHistory);
        return petRepository.save(pet);
    }

    @Transactional
    public Pet approvePetRecord(int serialNumber) {
        System.out.println("=== SERVICE: Approving pet " + serialNumber + " ===");
        Optional<Pet> petOptional = petRepository.findById(serialNumber);
        if (!petOptional.isPresent()) {
            System.out.println("=== ERROR: Pet " + serialNumber + " not found ===");
            throw new IllegalArgumentException("Pet with serial number " + serialNumber + " does not exist");
        }
        
        Pet pet = petOptional.get();
        System.out.println("=== BEFORE: Pet " + serialNumber + " approval status = " + pet.getVet_approved() + " ===");
        pet.setVet_approved(true);
        Pet savedPet = petRepository.save(pet);
        System.out.println("=== AFTER: Pet " + serialNumber + " approval status = " + savedPet.getVet_approved() + " ===");
        return savedPet;
    }
    
    @Transactional
    public Pet updatePet(int serialNumber, Pet updatedPet) {
        Optional<Pet> existingPetOptional = petRepository.findById(serialNumber);
        if (!existingPetOptional.isPresent()) {
            throw new IllegalArgumentException("Pet with serial number " + serialNumber + " does not exist");
        }
        
        Pet existingPet = existingPetOptional.get();
        
        // Preserve critical fields that shouldn't be overwritten
        updatedPet.setSerialNumber(serialNumber);
        updatedPet.setPetIdStr(String.format("%09d", serialNumber));
        
        // Preserve approval status if not explicitly provided
        if (updatedPet.getVet_approved() == null) {
            updatedPet.setVet_approved(existingPet.getVet_approved());
        }
        
        // Preserve owner ID to prevent unauthorized ownership changes
        updatedPet.setOwnerID(existingPet.getOwnerID());
        
        return petRepository.save(updatedPet);
    }
    
    public boolean petExists(int serialNumber) {
        return petRepository.existsById(serialNumber);
    }
    
    public List<Pet> findPetsByOwnerID(String ownerID) {
        if (ownerID == null || ownerID.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner ID cannot be null or empty");
        }
        return petRepository.findPetsByOwnerID(ownerID.trim());
    }
    
    public Optional<Pet> findPetByIdString(String petIdStr) {
        if (petIdStr == null || petIdStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Pet ID string cannot be null or empty");
        }
        
        // Normalize the input to 9-digit zero-padded format
        String normalizedId = normalizePetIdString(petIdStr.trim());
        
        try {
            int serialNumber = Integer.parseInt(normalizedId);
            return petRepository.findById(serialNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid pet ID format: " + petIdStr);
        }
    }
    
    public String normalizePetIdString(String petIdStr) {
        // Remove any non-numeric characters
        String cleanId = petIdStr.replaceAll("[^0-9]", "");
        
        if (cleanId.isEmpty()) {
            throw new IllegalArgumentException("Pet ID must contain numeric characters");
        }
        
        if (cleanId.length() > 9) {
            throw new IllegalArgumentException("Pet ID cannot exceed 9 digits");
        }
        
        // Pad with zeros to make it 9 digits
        return String.format("%09d", Integer.parseInt(cleanId));
    }
    
    public String getPetIdString(int serialNumber) {
        return String.format("%09d", serialNumber);
    }
    
    public long getTotalPetCount() {
        return petRepository.count();
    }
}
