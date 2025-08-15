package com.UserRegistration.UserApp.Controller;

import com.UserRegistration.UserApp.Exception.UserNotFoundException;
import com.UserRegistration.UserApp.Model.User;
import com.UserRegistration.UserApp.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user){
        return userRepo.save(user);
    }

    @GetMapping("getUsers")
    public List<User> showUsers(){
        return userRepo.findAll();
    }

    @GetMapping("/getUser/{id}")
    public User getUserById(@PathVariable Long id){
        return userRepo.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));
    }

    @PutMapping("/updateUser/{id}")
    public User editUser(@RequestBody User user, @PathVariable Long id){
        return userRepo.findById(id)
                .map(user1 ->{
                    user1.setName(user.getName());
                    user1.setUsername(user.getUsername());
                    user1.setEmail(user.getEmail());
                    return userRepo.save(user1);
                }).orElseThrow(()->new UserNotFoundException(id));
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUserById(@PathVariable Long id){
          if(!userRepo.existsById(id)){
              throw new UserNotFoundException(id);
          }
          userRepo.deleteById(id);
          return "User with id " +id+ " has been deleted successfully!";
    }

}
