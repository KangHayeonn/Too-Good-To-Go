package com.toogoodtogo.application;

import com.toogoodtogo.domain.exceptions.CUploadImageInvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
@Slf4j
public class UploadFileConverter {
    public String parseFileInfo(MultipartFile multipartFile, String path, Long id) {
        if(multipartFile.isEmpty()) return "default.png";

        String contentType = multipartFile.getContentType();
        String originalFileExtension;
        if (!StringUtils.hasText(contentType)) throw new CUploadImageInvalidException();
        if(contentType.contains("image/jpeg") || contentType.contains("image/jpg")){
            originalFileExtension = ".jpg";
        }
        else if(contentType.contains("image/png")){
            originalFileExtension = ".png";
        }
        else if(contentType.contains("image/gif")) {
            originalFileExtension = ".gif";
        } else throw new CUploadImageInvalidException();

        // 파일 이름
        String fileName = UUID.randomUUID() + originalFileExtension;
        // 파일 위치
        String filePath = path + "/" + id + "/" + fileName; // ex) shopsImage/1/random_name.jpg
        log.info("profileImage : origName : \"{}\", fileName : \"{}\", filePath : \"{}\"",
                multipartFile.getOriginalFilename(), fileName, filePath);
        return filePath;
    }
}
