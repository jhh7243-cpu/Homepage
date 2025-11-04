package site.aiion.api.common.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@Schema(description = "응답 메시지")
public class Messenger 
{
    @Schema(description = "응답 코드", example = "200")
    private int Code;
    
    @Schema(description = "응답 메시지", example = "어서옵쇼")
    private String message;
    
    @Schema(description = "응답 데이터")
    private Object data;
    
}
