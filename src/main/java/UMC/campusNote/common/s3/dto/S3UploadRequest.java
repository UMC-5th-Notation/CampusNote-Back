package UMC.campusNote.common.s3.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class S3UploadRequest {

    private Long userId;
    private String dirName;
}
