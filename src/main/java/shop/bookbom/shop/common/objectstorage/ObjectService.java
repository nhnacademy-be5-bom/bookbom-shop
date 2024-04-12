package shop.bookbom.shop.common.objectstorage;


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import lombok.NonNull;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private final RestTemplate restTemplate;
    private final TokenService tokenService;
    @Value("${object.storage.url}")
    private String storageUrl;
    private TokenInfo token;

    @Autowired
    public ObjectService(TokenService tokenService, RestTemplate restTemplate) {
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
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
     * @param objectName    저장될 파일 이름
     */
    public void uploadObject(MultipartFile file, String containerName, String objectName) {
        String url = this.getUrl(containerName, objectName);
        if (Objects.isNull(token) || token.getExpires().isBefore(LocalDateTime.now())) {
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
            HttpMessageConverterExtractor<String> responseExtractor
                    = new HttpMessageConverterExtractor<>(String.class, new RestTemplate(requestFactory).getMessageConverters());
            restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }

    /**
     * Object Storage에서 파일을 받아오는 메서드입니다.
     *
     * @param containerName 저장된 컨테이너 이름 ex) bookbom/book_thumbnail
     * @param objectName    저장한 파일 이름
     * @return 파일 데이터
     */
    public byte[] downloadObject(String containerName, String objectName) {
        String url = this.getUrl(containerName, objectName);
        if (Objects.isNull(token) || token.getExpires().isBefore(LocalDateTime.now())) {
            token = tokenService.requestToken();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", token.getId());
        headers.setAccept(List.of(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, requestHttpEntity, byte[].class);

        byte[] body = response.getBody();
        if (body == null || body.length == 0) {
            throw new FileNotFoundException();
        }
        return body;
    }

    /**
     * Object Stroage에서 받아온 바이트 배열을 이미지 데이터로 바꿔주는 메서드입니다.
     *
     * @param buffer 이미지 데이터
     * @return Base64로 인코딩한 이미지 데이터
     */
    public String bufferToUrl(byte[] buffer) {
        String imageUrl = Base64.getEncoder().encodeToString(buffer);
        return "data:image/jpeg;base64," + imageUrl;
    }
}
