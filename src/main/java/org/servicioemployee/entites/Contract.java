package org.servicioemployee.entites;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name ="Contract")
public class Contract extends PanacheEntity {
    @Transient
    private Long id;
    //@OneToOne
   // @JoinColumn(name="empl_id")
   // private Employee empl;
    @Transient
    private int employeeId;
    @Transient
    private String assignamentType;
    @Transient
    private String workShift;
    @Transient
    private int scheduledWeeklyHours;
    @Transient
    private String employeeTypo;
    @Transient
    private String timeType;
    @Transient
    private String supervisoryOrganization;
    @Transient
    private String lineOfBusiness;
    @Transient
    private String jobProfile;
    @Transient
    private String businessTitle;
    @Transient
    private String compensationGrade;
    @Transient
    private String compensationGradeProfile;
    @Transient
    private String subBanda;
    @Transient
    private boolean status;
    @Transient
    private Date hireDate;
    @Transient
    private String hireCategory;
    @Transient
    private String hireReason;
    @Transient
    private String costCenterId;
    @Transient
    private String costCenterName;
    @Transient
    private String worhPlaceIncicator;
    @Transient
    private double fte ;
    @Transient
    private boolean employees;
    @Transient
    private boolean manager;
    @Transient
    private Date fechaDeCarga;
    @Transient
    private boolean isMatrixManager;


}
