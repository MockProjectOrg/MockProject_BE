package org.example.bookingbe.service.ImageService;

import org.example.bookingbe.model.Image;
import org.example.bookingbe.repository.ImageRepo.IImageRepo;
import org.example.bookingbe.service.CloudinaryService.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class ImageService {
    private final IImageRepo imageRepo;
    private final CloudinaryService cloudinaryService;

    public ImageService(IImageRepo imageRepo, CloudinaryService cloudinaryService) {
        this.imageRepo = imageRepo;
        this.cloudinaryService = cloudinaryService;
    }

    /**
     * Tải ảnh lên Cloudinary và lưu URL vào bảng Image
     *
     * @param file Tệp ảnh cần tải lên
     * @return Image đối tượng chứa thông tin ảnh đã được lưu
     */
    public Image uploadImageAndSaveToDatabase(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty, please upload a valid image.");
        }

        // Kiểm tra loại file
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Invalid file type. Only images are allowed.");
        }

        // Tải ảnh lên Cloudinary và lấy URL
        String imageUrl = cloudinaryService.uploadImage(file);

        // Kiểm tra tên file, nếu không có thì tạo tên ngẫu nhiên
        String imageName = file.getOriginalFilename();
        if (imageName == null || imageName.isEmpty()) {
            imageName = UUID.randomUUID().toString();
        }

        // Tạo đối tượng Image và lưu vào cơ sở dữ liệu
        Image image = new Image();
        image.setImageName(imageName);
        image.setImageUrl(imageUrl);

        return imageRepo.save(image);
    }
}