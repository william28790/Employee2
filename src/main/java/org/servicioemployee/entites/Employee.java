package org.servicioemployee.entites;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

//import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

/* import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
*/
@Entity
@Data
public class Employee extends PanacheEntityBase {

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
   // private int IdUsuario;
    @Id
    private Long id; // se refiere al homeCNUM
    private int employeeId;
    private String preferredName;
    private String legalName;
    private String firstName;
    private String middleName;
    private String initiald;
    private String lastName;
    private String fullName;
    private String email;
    private String country;
    private Date dateOfBirth;
    private String countryOfBirth;
    private Long managerId; // se refiere al homeCNUM
    private Long managerMatrixId; // se refiere al homeCNUM
    @Transient
    @OneToOne(mappedBy = "id" , cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Compensation compensation;
    @Transient
    @OneToOne(mappedBy = "id" , cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Contract contract;
//@Transient
//@OneToOne(mappedBy = "id" , cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
//@JsonManagedReference
//private List<Employee> listEmployee;
    private Date fechaCarga; // parte de la pk
    private boolean isManager;

    //@OneToOne(mappedBy = "id",cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    //@JsonManagedReference
    //@OneToOne(mappedBy = "empl")
   // private Contract contract;

// pruebaa 2


}
