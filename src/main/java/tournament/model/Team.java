package tournament.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TEAM")
public class Team {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int size;

    @OneToMany(mappedBy = "team")
    @JsonManagedReference
    private List<Member> members;
}
