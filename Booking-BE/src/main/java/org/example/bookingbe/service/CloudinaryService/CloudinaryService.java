package org.example.bookingbe.service.CloudinaryService;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public List<String> getImageUrl(String roomId) throws Exception {
        List<String> imageUrls = new ArrayList<>();

        // Gọi Cloudinary API để lấy danh sách ảnh theo `roomId`
        Map result = cloudinary.search()
                .expression("folder:rooms/" + roomId)  // Ảnh được lưu trong folder rooms/{roomId}
                .execute();

        List<Map> resources = (List<Map>) result.get("resources");
        for (Map resource : resources) {
            String imageUrl = (String) resource.get("secure_url");
            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }
}