package site.aiion.api.analysis.mbti.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MbtiDTO {
    
    private Long mbti_id;
    private String m_name;
    private String m_char;
    private String e_i;
    private String s_n;
    private String t_f;
    private String j_p;
    private String m_result;
}

