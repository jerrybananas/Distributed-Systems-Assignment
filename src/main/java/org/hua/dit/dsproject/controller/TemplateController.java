package org.hua.dit.dsproject.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.hua.dit.dsproject.pet.Pet;
import org.hua.dit.dsproject.pet.PetService;
import org.hua.dit.dsproject.user.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author it21542 - Antonis Rouseas
 * Template controller for routing web pages
 */
@Controller
@RequestMapping("/")
@Tag(name = "Template Controller", description = "Web page routing and template-compatible API endpoints")
public class TemplateController {
    
    @Autowired
    private PetService petService;
    //Here we map each of the locations of the site, to their corresponding html file.

    @GetMapping(path = "modifyPet")
    public String getmodifyView() {
        return "modifyPet";
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping("vet")
    public String getVetView() {
        return "vet";
    }

    @GetMapping("citizen")
    public String getCitizenView() {
        return "citizen";
    }


    @GetMapping("admin")
    public String getAdminView() {
        return "admin";
    }

    @GetMapping("register")
    public String getRegisterView() {
        return "register";
    }

    @GetMapping("newpet")
    public String getNewPetView() {
        return "newpet";
    }

    @GetMapping("modify")
    public String getModifyView() {
        return "modify";
    }

    @GetMapping("delete")
    public String getDeleteView() {
        return "delete";
    }

    //A template where we redirect based on the role
    @GetMapping("/success")
    public String loginPageRedirect(Authentication authResult) {

        String role = authResult.getAuthorities().toString();

        if (role.contains("ROLE_ADMIN")) {
            return "redirect:/admin";
        } else if (role.contains("ROLE_CITIZEN")) {
            return "redirect:/citizen";
        } else if (role.contains("ROLE_VET")) {
            return "redirect:/vet";
        } else {
            // Default redirect if no role matches
            return "redirect:/login?error=unknown_role";
        }

    }

    // Pet endpoints for template compatibility
    @GetMapping("/pets")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'CITIZEN')")
    public ResponseEntity<?> getAllPets() {
        try {
            return ResponseEntity.ok(petService.listAllPets());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error loading pets");
        }
    }
    
    @GetMapping("/findPet/{serialNumber}")
    @ResponseBody
    public ResponseEntity<?> findPet(@PathVariable int serialNumber) {
        try {
            Optional<Pet> pet = petService.findPet(serialNumber);
            if (pet.isPresent()) {
                return ResponseEntity.ok(pet.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error finding pet");
        }
    }
    
    @GetMapping("/debug/current-user")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'CITIZEN')")
    public ResponseEntity<?> getCurrentUserInfo() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return ResponseEntity.ok("Current User ID: " + userDetails.getIdNumber() + 
                                       ", Username: " + userDetails.getUsername() + 
                                       ", Roles: " + authentication.getAuthorities().toString());
            } else {
                return ResponseEntity.ok("Authentication principal: " + 
                                       (authentication != null ? authentication.getPrincipal().getClass().getName() : "null"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error getting user info: " + e.getMessage());
        }
    }
}