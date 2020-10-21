package nosmokes;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Pay_table")
public class Pay {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long point;

    @PrePersist
    public void onPrePersist(){
        Paid paid = new Paid();
        BeanUtils.copyProperties(this, paid);
        paid.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        nosmokes.external.Deduct deduct = new nosmokes.external.Deduct();
        // mappings goes here
        PayApplication.applicationContext.getBean(nosmokes.external.DeductService.class)
            .pay(deduct);


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }




}
