package start;

import javax.persistence.*;

@Entity
@TableGenerator(
        name = "BOARD_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "BOARD_SEQ", allocationSize = 1 // allocationSize로 최적화....하는게 좋다.
)
@Access(AccessType.FIELD)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
        generator = "BOARD_SEQ_GENERATOR")
    private Long id;

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }
}
