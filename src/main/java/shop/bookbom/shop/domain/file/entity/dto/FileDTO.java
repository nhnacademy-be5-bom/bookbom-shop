package shop.bookbom.shop.domain.file.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : shop.bookbom.shop.domain.file.entity.dto
 * fileName       : FileDTO
 * author         : UuLaptop
 * date           : 2024-04-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-15        UuLaptop       최초 생성
 */
@NoArgsConstructor
@Getter
public class FileDTO {
    private String url;
    private String extension;

    @Builder
    public FileDTO(String url, String extension) {
        this.url = url;
        this.extension = extension;
    }
}
