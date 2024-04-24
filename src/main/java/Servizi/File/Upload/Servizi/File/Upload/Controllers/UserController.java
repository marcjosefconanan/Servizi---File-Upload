package Servizi.File.Upload.Servizi.File.Upload.Controllers;

import Servizi.File.Upload.Servizi.File.Upload.Dto.DownloadProfilePictureDto;
import Servizi.File.Upload.Servizi.File.Upload.Entities.User;
import Servizi.File.Upload.Servizi.File.Upload.Repositories.UserRepository;
import Servizi.File.Upload.Servizi.File.Upload.Services.UserService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/adduser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allStudents = userService.getAll();
        return ResponseEntity.ok().body(allStudents);
    }

    @GetMapping("/finduserbyid/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.findUserById(id);
        return ResponseEntity.ok().body(userOptional);
    }


    @PutMapping("/updateuser/{id}")
    public ResponseEntity<User> updateUserById(@RequestBody User user, @PathVariable Long id) {
        Optional<User> userOptional = userService.updateUser(id, user);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok().body(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteid")
    public ResponseEntity<Optional<User>> deleteUserById(@RequestParam Long id) {
        Optional<User> userToDelete = userService.deleteUserById(id);
        if (userToDelete.isPresent()) {
            return ResponseEntity.ok().body(userToDelete);
        }
        return ResponseEntity.notFound().build();

    }

    @PatchMapping("/upload/profilepicture/{id}")
    public ResponseEntity<User> uploadProfilePicture(@PathVariable Long id , @RequestParam MultipartFile picture) throws IOException {
        Optional<User> userOptional = userService.uploadProfilePicture(id,picture);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.get());
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/download/profilepicture/{id}")
    public ResponseEntity<DownloadProfilePictureDto> downloadProfilePicture(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Optional<DownloadProfilePictureDto> profilePictureDTOOptional = userService.downloadProfilePicture(id,response);
        if(profilePictureDTOOptional.isPresent()){
            return ResponseEntity.ok(profilePictureDTOOptional.get());
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
}
