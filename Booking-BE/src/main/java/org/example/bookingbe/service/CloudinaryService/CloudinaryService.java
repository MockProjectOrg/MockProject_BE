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

    // Khởi tạo Cloudinary với thông tin cấu hình từ file properties
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

    /**
     * Tải ảnh lên Cloudinary và trả về URL ảnh đã tải lên.
     *
     * @param file MultipartFile đối tượng ảnh được tải lên
     * @return URL ảnh đã tải lên
     */
    public String uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty.");
        }

        try {
            // Tải ảnh lên Cloudinary và lấy kết quả trả về
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("secure_url");  // Trả về URL ảnh bảo mật
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed: " + e.getMessage(), e);
        }
    }

    /**
     * Lấy URL ảnh từ Cloudinary bằng roomId.
     *
     * @param roomId Id của phòng
     * @return URL của ảnh phòng
     */
    public String getImageUrl(String roomId) {
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("roomId cannot be null or empty.");
        }

        try {
            // Lấy thông tin ảnh từ Cloudinary với roomId làm tham số
            Map<String, Object> resource = cloudinary.api().resource(roomId, ObjectUtils.emptyMap());
            if (resource != null && resource.containsKey("secure_url")) {
                return (String) resource.get("secure_url");  // Trả về URL ảnh bảo mật
            } else {
                throw new RuntimeException("Image not found for room: " + roomId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch image for room: " + roomId, e);
        }
    }

    /**
     * Lấy tất cả URL của ảnh trong một thư mục phòng.
     *
     * @param roomId Id của phòng
     * @return Danh sách URL của các ảnh trong phòng
     */
    public List<String> getImageUrlsForRoom(String roomId) {
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("roomId cannot be null or empty.");
        }

        List<String> imageUrls = new ArrayList<>();
        try {
            // Lấy danh sách ảnh trong thư mục phòng
            Map<String, Object> result = cloudinary.api().resources(ObjectUtils.asMap(
                    "prefix", "rooms/" + roomId, "type", "upload"));

            List<Map<String, Object>> resources = (List<Map<String, Object>>) result.get("resources");
            for (Map<String, Object> resource : resources) {
                String url = (String) resource.get("secure_url");
                if (url != null) {
                    imageUrls.add(url);  // Thêm URL vào danh sách
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch images for room: " + roomId, e);
        }

        if (imageUrls.isEmpty()) {
            throw new RuntimeException("No images found for room: " + roomId);
        }

        return imageUrls;
    }

    /**
     * Xóa ảnh khỏi Cloudinary dựa trên publicId.
     *
     * @param publicId Id công khai của ảnh cần xóa
     */
    public void deleteImage(String publicId) {
        if (publicId == null || publicId.trim().isEmpty()) {
            throw new IllegalArgumentException("publicId cannot be null or empty.");
        }

        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete image with publicId: " + publicId, e);
        }
    }
}
