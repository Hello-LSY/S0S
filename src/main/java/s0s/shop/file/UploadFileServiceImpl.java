//package s0s.shop.file;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import s0s.shop.product.Product;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class UploadFileServiceImpl implements UploadFileService {
//
//    final private UploadFileRepository uploadFileRepository;
//
//    @Value("${image.upload.directory}")
//    String imageUploadDirectory;
//
//    @Override
//    public UploadFile saveUploadFile(String imageUrl, Product product) {
//        UploadFile uploadFile = UploadFile.builder()
//                .imageUrl(imageUrl)
//                .product(product)
//                .build();
//        return uploadFileRepository.save(uploadFile);
//    }
//
//    @Override
//    public UploadFileDTO saveImageFile(MultipartFile file, Product product) {
//        // UUID 생성
//        String uuid = UUID.randomUUID().toString();
//
//        // 파일 확장자 추출
//        String originalFilename = file.getOriginalFilename();
//
//        // UUID를 파일 이름에 추가하여 저장
//        String savedFilename = uuid + "_" + originalFilename;
//
//        try {
//            // 이미지 파일 저장
//            Path uploadPath = Paths.get(imageUploadDirectory, savedFilename);
//            Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("이미지 파일을 저장하는 중에 오류가 발생했습니다.", e);
//        }
//
//        // 저장된 이미지 파일의 경로를 imageUrl로 설정하여 업로드 파일 저장
//        String imageUrl = imageUploadDirectory + "/" + savedFilename;
//
//        // UploadFile 엔티티를 생성하고 저장
//        UploadFile uploadFile = saveUploadFile(imageUrl, product);
//
//        return new UploadFileDTO(uploadFile.getImageUrl());
//    }
//}
