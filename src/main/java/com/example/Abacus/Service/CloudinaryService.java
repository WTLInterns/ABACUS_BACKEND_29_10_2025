package com.example.Abacus.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private Cloudinary cloudinary;
    
    // Your Cloudinary credentials
    private static final String CLOUD_NAME = "dwv4ommho"; // Replace with your actual cloud name
    private static final String API_KEY = "582875762753276";
    private static final String API_SECRET = "apH9WHPk7fsMubtt8C89JKUVP7U";

    @PostConstruct
    public void initializeCloudinary() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET));
    }

    /**
     * Uploads a file to Cloudinary
     * 
     * @param file The file to upload
     * @return A map containing the upload result information
     * @throws IOException If there's an error during upload
     */
    public Map<String, Object> upload(MultipartFile file) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
    }

    /**
     * Deletes a file from Cloudinary
     * 
     * @param publicId The public ID of the file to delete
     * @return A map containing the deletion result information
     * @throws IOException If there's an error during deletion
     */
    public Map<String, Object> delete(String publicId) throws IOException {
        return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}