package org.hua.dit.dsproject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author it21542 - Antonis Rouseas
 * User details service for Spring Security authentication
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    //The service contains a repo and it can load a user by its username, returning a UserDetails object
    @Autowired
    UserRepository userRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> applicationUser = userRepository.findByUserName(username);

        applicationUser.orElseThrow(() -> new UsernameNotFoundException("There is no such user"));
        return applicationUser.map(UserDetails::new).get();
    }

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }


    public synchronized void newUser(User user) {
        // Generate automatic ID for the user
        String newId = generateNextUserId();
        user.setIdNumber(newId);
        
        // Validate user data
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            throw new IllegalArgumentException("Role is required");
        }
        
        userRepository.save(user);
    }
    
    private synchronized String generateNextUserId() {
        // Find the next available ID in 9-digit format
        List<User> allUsers = userRepository.findAll();
        
        // Extract numeric IDs and find the maximum
        long maxId = 0;
        for (User user : allUsers) {
            try {
                String cleanId = user.getIdNumber().replaceAll("[^0-9]", "");
                if (!cleanId.isEmpty()) {
                    long id = Long.parseLong(cleanId);
                    if (id > maxId) {
                        maxId = id;
                    }
                }
            } catch (NumberFormatException e) {
                // Skip non-numeric IDs
            }
        }
        
        long nextId = maxId + 1;
        
        // Ensure ID is within valid range (000000001 to 999999999)
        if (nextId > 999999999L) {
            // Search for gaps in the sequence starting from 1
            for (long i = 1; i <= 999999999L; i++) {
                String candidateId = String.format("%09d", i);
                boolean exists = allUsers.stream()
                        .anyMatch(u -> candidateId.equals(u.getIdNumber()) || 
                                      candidateId.equals(u.getIdNumber().replaceAll("[^0-9]", "")));
                if (!exists) {
                    return candidateId;
                }
            }
            throw new IllegalStateException("No available user ID slots remaining");
        }
        
        return String.format("%09d", nextId);
    }

}