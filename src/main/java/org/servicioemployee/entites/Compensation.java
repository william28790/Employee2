package org.servicioemployee.entites;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import java.util.*;

@Data
@Table(name = "Compensation")
public class Compensation extends PanacheEntity {
    @Transient
    private Long id;
    @Transient
    private Long homeCNUM;
    @Transient
    private String compensationGradeProfile;
    @Transient
    private String pmr;
    @Transient
    private double lengthOfService;
    @Transient
    private double annualReferenceSalary;
    @Transient
    private double monthlyReferenceSalary;
    @Transient
    private double nomberOfSalaryMonths;
    @Transient
    private String currency;
    @Transient
    private Date fechaDeCarga;
}

//agregando comentario para probar el git
