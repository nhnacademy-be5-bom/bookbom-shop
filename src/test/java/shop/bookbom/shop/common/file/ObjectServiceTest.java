package shop.bookbom.shop.common.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import shop.bookbom.shop.common.file.dto.TokenInfo;
import shop.bookbom.shop.common.file.exception.FileNotFoundException;

@ExtendWith(MockitoExtension.class)
class ObjectServiceTest {

    @Mock
    RestTemplate restTemplate;

    @Mock
    TokenService tokenService;

    @InjectMocks
    ObjectService objectService;

    @Test
    @DisplayName("Object Storage 파일 업로드")
    void uploadObject() throws Exception {
        // given
        String url = "http://test.com";
        ReflectionTestUtils.setField(objectService, "storageUrl", url);
        TokenInfo token = new TokenInfo("testToken", LocalDateTime.now().plusDays(3), LocalDateTime.now());
        when(tokenService.requestToken()).thenReturn(token);
        String containerName = "testContainer";
        String objectName = "testObject";
        byte[] fileContent = "testFile".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileContent);

        // when
        objectService.uploadFile(mockMultipartFile, containerName, objectName);

        // then
        verify(restTemplate, times(1)).execute(anyString(), (HttpMethod) any(), (RequestCallback) any(), any());
    }

    @Test
    @DisplayName("Object Storage에서 파일 다운로드")
    void downloadObject() {
        // given
        String url = "http://test.com";
        ReflectionTestUtils.setField(objectService, "storageUrl", url);
        String containerName = "testContainer";
        String objectName = "testObject";
        byte[] fileContent = "testFile".getBytes();
        ResponseEntity<byte[]> mockResponseEntity = ResponseEntity.ok(fileContent);
        when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity<?>) any(), eq(byte[].class)))
                .thenReturn(mockResponseEntity);
        when(tokenService.requestToken()).thenReturn(
                new TokenInfo("test", LocalDateTime.now().plusDays(7), LocalDateTime.now()));
        // when
        byte[] result = objectService.downloadFile(containerName, objectName);

        // then
        assertThat(result).isEqualTo(fileContent);
    }

    @Test
    @DisplayName("Object Storage에서 파일 다운로드 실패 시 FileNotFoundException")
    void downloadObjectException() {
        // given
        String url = "http://test.com";
        ReflectionTestUtils.setField(objectService, "storageUrl", url);
        String containerName = "testContainer";
        String objectName = "testObject";
        when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity<?>) any(), eq(byte[].class)))
                .thenReturn(ResponseEntity.ok(null));
        when(tokenService.requestToken()).thenReturn(
                new TokenInfo("test", LocalDateTime.now().plusDays(7), LocalDateTime.now()));
        // when
        assertThatThrownBy(() -> objectService.downloadFile(containerName, objectName))
                .isInstanceOf(FileNotFoundException.class);

    }
}
