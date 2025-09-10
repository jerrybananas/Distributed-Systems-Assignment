package org.hua.dit.dsproject.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.hua.dit.dsproject.pet.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "User Management", description = "Operations for managing users in the pet management system")
public class UserController {

    private final UserDetailsService userDetailsService;

    @Autowired
    public UserController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping(path = "/users/list")
    public List<User> getUser() {
        return userDetailsService.listAllUsers();
    }

    @PostMapping(path = "/users/new")
    public void newUser(@RequestBody User user) {
        userDetailsService.newUser(user);
    }

    @PostMapping(path = "/users/modify")
    @ResponseBody
    public String modifyUser(@RequestBody User user) {

        int response = userDetailsService.userRepository.UserExists(user.getIdNumber());
        if (response==0){
            return "This ID doesn't exist";
        }

        userDetailsService.userRepository.deleteById(user.getIdNumber());
        userDetailsService.newUser(user);

        return "Modified!";
    }

    @DeleteMapping(path = "/users/delete")
    @ResponseBody
    public String deleteUser(@RequestBody User user) {
        int response = userDetailsService.userRepository.UserExists(user.getIdNumber());
        if (response==0){
            return "This ID doesn't exist";
        }

        userDetailsService.userRepository.deleteById(user.getIdNumber());
        return "Deleted!";
    }

    @GetMapping(path = "/findPetsFromOwner/{ownerID}")
    public List<Pet> getPetsFromOwner(@PathVariable("ownerID") String ownerID) {
        return userDetailsService.userRepository.findPets(ownerID);
    }

    @GetMapping(path = "/api/users/count")
    public long getUserCount() {
        return userDetailsService.userRepository.count();
    }
}