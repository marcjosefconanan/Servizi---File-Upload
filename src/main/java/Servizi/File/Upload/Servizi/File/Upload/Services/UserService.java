package Servizi.File.Upload.Servizi.File.Upload.Services;

import Servizi.File.Upload.Servizi.File.Upload.Dto.DownloadProfilePictureDto;
import Servizi.File.Upload.Servizi.File.Upload.Entities.User;
import Servizi.File.Upload.Servizi.File.Upload.Repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
    public Optional<User>updateUser(Long id,User user){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            userOptional.get().setNome(user.getNome());
            User userUpdated = userRepository.save(userOptional.get());
            return Optional.of(userUpdated);
        }
        return Optional.empty();
    }

    public Optional<User> deleteUserById(Long id){
        Optional<User> userToDeleteOptional = userRepository.findById(id);
        if(userToDeleteOptional.isPresent()){
            userRepository.delete(userToDeleteOptional.get());
            return userToDeleteOptional;
        }else{
            return Optional.empty();
        }

    }

    public Optional<User> uploadProfilePicture(Long id, MultipartFile picture) throws IOException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            String pictureName = fileStorageService.upload(picture);
            userOptional.get().setProfilePicture(pictureName);
            User userUpdated = userRepository.save(userOptional.get());
            return Optional.of(userUpdated);
        }else {
            return Optional.empty();
        }
    }

    public Optional<DownloadProfilePictureDto> downloadProfilePicture(Long id, HttpServletResponse response) throws IOException {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent()){

            String fileName = userOptional.get().getProfilePicture();
            byte[] profilePicture = fileStorageService.download(fileName,response);
            DownloadProfilePictureDto userProfilePictureDTO = new DownloadProfilePictureDto();
            userProfilePictureDTO.setUser(userOptional.get());
            userProfilePictureDTO.setProfileImage(profilePicture);
            return Optional.of(userProfilePictureDTO);

        }else {
            return Optional.empty();
        }
    }



}
