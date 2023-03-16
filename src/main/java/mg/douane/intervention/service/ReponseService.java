package mg.douane.intervention.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import mg.douane.intervention.data.domaine.Reponse;

public interface ReponseService {

    public Reponse uploadImage(MultipartFile file, Reponse reponse) throws IOException;

    public byte[] downloadImage(String fileName);

    public Resource loadPicture(String fileName);

}
