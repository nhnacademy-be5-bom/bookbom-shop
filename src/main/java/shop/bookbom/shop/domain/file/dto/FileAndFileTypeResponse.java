package shop.bookbom.shop.domain.file.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.bookfile.entity.BookFile;

/**
 * packageName    : shop.bookbom.shop.domain.file.dto
 * fileName       : FileAndFileTypeResponse
 * author         : UuLaptop
 * date           : 2024-04-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-25        UuLaptop       최초 생성
 */
@Getter
@NoArgsConstructor
public class FileAndFileTypeResponse {
    private Long id;
    private String url;
    private String fileType;
    private String extension;

    @Builder
    private FileAndFileTypeResponse(String url,
                                    String fileType,
                                    String extension) {
        this.url = url;
        this.fileType = fileType;
        this.extension = extension;
    }

    public static FileAndFileTypeResponse from(BookFile bookFile) {
        return FileAndFileTypeResponse.builder()
                .url(bookFile.getFile().getUrl())
                .fileType(bookFile.getBookFileType().getName())
                .extension(bookFile.getFile().getExtension())
                .build();
    }
}
