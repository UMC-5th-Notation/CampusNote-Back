package UMC.campusNote.common.s3.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class S3UploadRequest {

    private Long userId;
    private String dirName;
}
