package Servizi.File.Upload.Servizi.File.Upload.Controllers;

import Servizi.File.Upload.Servizi.File.Upload.Services.FileStorageService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileStorageService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(service.upload(file));
    }

    @GetMapping("/download")
    public @ResponseBody ResponseEntity<byte[]> download(
            @RequestParam String fileName,
            HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(service.download(fileName,response));
    }
}