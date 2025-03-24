package org.example.bookingbe.service.CloudinaryService;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    // Inject Cloudinary từ CloudinaryConfig
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadFile(MultipartFile file, String folder, String publicId) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "folder", folder,    // Thư mục lưu ảnh
                "public_id", publicId,  // Tên file
                "overwrite", true
        ));
        return uploadResult.get("secure_url").toString();
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
            ApiResponse resource = cloudinary.api().resource(roomId, ObjectUtils.emptyMap());
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
