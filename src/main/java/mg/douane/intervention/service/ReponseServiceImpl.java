package mg.douane.intervention.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mg.douane.intervention.data.domaine.Reponse;
import mg.douane.intervention.repository.ReponseRepository;

@Service
public class ReponseServiceImpl implements ReponseService {

    @Autowired
    ReponseRepository reponseRepository;

    @Autowired
    ReponseService reponseService;

    private final Path root = Paths.get("./src/main/resources/static/uploadFile");

    @Override
    public Reponse uploadImage(MultipartFile file, Reponse reponse) throws IOException {
        init();
        reponse.setFileName(file.getOriginalFilename());
        reponse.setFileType(file.getContentType());
        reponse.setPieceJointeRep(compressImage(file.getBytes()));
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                String str = file.getOriginalFilename();
                String[] arrOfStr = str.split("[.]");
                Files.copy(file.getInputStream(), this.root.resolve(arrOfStr[0] + "-copie." + arrOfStr[1]));
                reponse.setFileName(arrOfStr[0] + "-copie." + arrOfStr[1]);
            }
        }
        return reponseRepository.save(reponse);
    }

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public byte[] downloadImage(String fileName) {
        Reponse reponse = reponseRepository.findByFileName(fileName);
        return decompressImage(reponse.getPieceJointeRep());
    }

    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    @Override
    public Resource loadPicture(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

}