package org.example.bookingbe.service.CloudinaryService;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService(
            @Value("${cloudinary.cloud-name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret
    ) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    // Phương thức tải ảnh lên Cloudinary
    public String uploadImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();  // Trả về URL ảnh đã tải lên
        } catch (IOException e) {
            throw new RuntimeException("Upload failed: " + e.getMessage());
        }
    }

    // Phương thức lấy URL của ảnh từ roomId (Giả định sẽ trả về URL ảnh)
    public String getImageUrl(String roomId) {
        try {
            // Giả sử roomId là tên của ảnh hoặc folder trên Cloudinary
            Map uploadResult = cloudinary.api().resource(roomId, ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");  // Lấy URL ảnh từ kết quả trả về
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch image for room: " + roomId);
        }
    }

    // Phương thức lấy danh sách URL ảnh cho phòng (roomId)
    public List<String> getImageUrlsForRoom(String roomId) {
        List<String> imageUrls = new ArrayList<>();
        try {
            // Giả sử chúng ta có thể lấy danh sách các ảnh từ folder phòng trong Cloudinary
            Map result = cloudinary.api().resources(ObjectUtils.asMap("prefix", "rooms/" + roomId, "type", "upload"));
            List<Map> resources = (List<Map>) result.get("resources");

            for (Map resource : resources) {
                imageUrls.add((String) resource.get("url"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch images for room: " + roomId, e);
        }
        return imageUrls;
    }
}
