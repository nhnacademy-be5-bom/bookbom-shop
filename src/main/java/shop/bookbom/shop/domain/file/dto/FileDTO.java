package shop.bookbom.shop.domain.file.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.bookfile.entity.BookFile;

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
    private Long id;
    private String url;
    private String extension;

    @Builder
    private FileDTO(String url, String extension) {
        this.url = url;
        this.extension = extension;
    }

    public static FileDTO from(BookFile bookFile) {
        return FileDTO.builder()
                .url(bookFile.getFile().getUrl())
                .extension(bookFile.getFile().getExtension())
                .build();
    }
}
