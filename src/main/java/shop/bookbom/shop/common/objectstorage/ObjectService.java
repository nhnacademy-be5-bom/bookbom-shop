package shop.bookbom.shop.common.objectstorage;


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import lombok.NonNull;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import shop.bookbom.shop.common.objectstorage.dto.TokenInfo;
import shop.bookbom.shop.common.objectstorage.exception.FileNotFoundException;

@Service
public class ObjectService {

    private final TokenService tokenService;
    @Value("${object.storage.url}")
    private String storageUrl;
    private TokenInfo token;

    @Autowired
    public ObjectService(TokenService tokenService) {
        this.tokenService = tokenService;
        token = tokenService.requestToken();
    }


    private String getUrl(@NonNull String containerName, @NonNull String objectName) {
        return storageUrl + "/" + containerName + "/" + objectName;
    }

    /**
     * Object Storage로 파일을 업로드해주는 메서드입니다.
     *
     * @param file          업로드할 파일 MultipartName
     * @param containerName 저장할 컨테이너 이름 ex) bookbom/book_thumbnail
     * @param objectName    ex) 저장될 파일 이름
     */
    public void uploadObject(MultipartFile file, String containerName, String objectName) {
        String url = this.getUrl(containerName, objectName);
        if (token.getExpires().isBefore(LocalDateTime.now())) {
            token = tokenService.requestToken();
        }
        try {
            InputStream inputStream = file.getInputStream();
            final RequestCallback requestCallback = request -> {
                request.getHeaders().add("X-Auth-Token", token.getId());
                IOUtils.copy(inputStream, request.getBody());
            };
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setBufferRequestBody(false);
            RestTemplate restTemplate = new RestTemplate(requestFactory);
            HttpMessageConverterExtractor<String> responseExtractor
                    = new HttpMessageConverterExtractor<>(String.class, restTemplate.getMessageConverters());
            restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }
}
