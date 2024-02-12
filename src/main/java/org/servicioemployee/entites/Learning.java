package org.servicioemployee.entites;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.Date;

@Data
@Entity

public class Learning extends PanacheEntity {

    @Transient
    private int id;
    @Transient
    private  int homeCNUM;
    @Transient
    private int employeedId;
    @Transient
    private String learning_assignments_not_complete_Total;
    @Transient
    private String learning_assignments_not_complete_Required;
    @Transient
    private String learning_assignments_not_complete_PastDue;
    @Transient
    private String think_status_current_year;
    @Transient
    private String think_status_previous_yaer;
    @Transient
    private String learninig_hours_completed_current_year;
    @Transient
    private String learninig_hours_completed_previous_year;
    @Transient
    private String highest_degree;
    @Transient
    private String highest_field_of_study;
    @Transient
    private Date fecha_de_carga;
}
