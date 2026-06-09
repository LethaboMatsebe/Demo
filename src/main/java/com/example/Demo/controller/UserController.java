package com.example.Demo.controller;

import com.example.Demo.DTO.RegisterRequest;
import com.example.Demo.Entity.User;
import com.example.Demo.services.UserService;
//import org.apache.catalina.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/email/{email:.+}") // Added :.+ to handle dots in emails
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        return userService.findByEmail(email)
                .map(user -> ResponseEntity.ok(user)) // Used lambda to fix ambiguity
                .orElse(ResponseEntity.notFound().build());
    }




    @PostMapping("/register")
    public String registerUser(@ModelAttribute("RegisterRequest") RegisterRequest registerRequest,
                               RedirectAttributes redirectAttributes) {



        try {
            // 3. Call your service to save the user to the H2/Database
            userService.register(registerRequest);

            // 4. Add a success message that will appear on the login page
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please log in.");
//            return "redirect:/register";

            // 5. Redirect to the login page (Browser starts a new GET request)
             return "redirect:http://localhost:8081/api/login";
//            return "redirect:http://localhost:8081/api/login?success=Registration successful! Please log in.";



        } catch (Exception e) {
            // If something goes wrong (e.g. email already exists), go back to register
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/register";
        }



    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    // Update user by ID
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.findById(id)
                .map(user -> {
                    user.setEmail(updatedUser.getEmail());
                    user.setRole(updatedUser.getRole());
                    User saved = userService.save(user);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.existsById(id)) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}


