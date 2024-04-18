package shop.bookbom.shop.common.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void uploadFile(MultipartFile file, String containerName, String objectName);

    byte[] downloadFile(String containerName, String objectName);
}
