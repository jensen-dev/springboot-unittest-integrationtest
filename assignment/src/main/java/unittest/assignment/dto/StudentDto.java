package unittest.assignment.dto;

import lombok.*;

import javax.persistence.Column;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class StudentDto {
    public Long id;
    public String fn;
    public String ln;
    public String email;
}
