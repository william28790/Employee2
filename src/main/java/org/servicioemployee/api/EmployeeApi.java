package org.servicioemployee.api;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import org.servicioemployee.entites.Compensation;
import org.servicioemployee.entites.Employee;

//se cambiaron todos los javax por jakarta
//import javax.annotation.PostConstruct;
//import javax.inject.Inject;
import jakarta.inject.Inject;
//import javax.ws.rs.*;
import jakarta.ws.rs.*;
//import javax.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
import jakarta.ws.rs.core.Response;
import org.servicioemployee.repositorio.Repositorio;

import java.util.List;
//import static javax.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.CREATED;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeApi {

//este es un comentario de prueba
    @Inject
    Repositorio er;

    @GET
    public Uni<List<PanacheEntityBase>> list() {
    return Employee.listAll();
    // return ur.listUser();
    }

    // Buscar por id del empleado y te lo devuelve con so contrato
    @GET
    @Path("/{ID}/employee")
    public Uni<Employee> getByIdContract(@PathParam("ID") Long ID){
        return er.getContractPorId(ID);
    }

//devuelve los empleados que son manager con su contrato
   // @GET
   // @Path("/manager/employees")
   // public  Uni<List<Employee>> getEmployeeIsManager(){
      //  return er.getEmployeeIsManager();
   // }

    @GET
    @Path("/allManager/sinParametros")
    public  Uni<List<Employee>> getEmployeeIsManager(){
        return er.getAllManager();
    }


    @GET
    @Path("/{idManager}/employees")
    public Uni<List<Employee>> getEmployeesDeManager(@PathParam("idManager") Long idManager){
        return er.getEmployeesDeManager(idManager);
    }

    @GET
    @Path("/{idManager}/employeeConContractos")
    public Uni<List<Employee>> getEmployeesCompletos(@PathParam("idManager")Long idManager){
        return er.getEmployeesConContract(idManager);
    }

    @GET
    @Path("/{idManager}/empleadosXManagerCompletos")
    public  Uni<List<Employee>> getEmployeesCompletos2(@PathParam("idManager") Long idManager){
        return er.getEmployeesCompletos(idManager);
    }

    @GET
    @Path("/{idManager}/managerYCantEmpleados")
    public Uni<Long> managerYCantEmpleados(@PathParam("idManager")Long idManager){
        return er.managerYCantEmpleados(idManager);
    }

    @GET
    @Path("/{idEmpleado}/pruebaCompensation")
    public Uni<Compensation> pruebaCompensation(@PathParam("idEmployee")Long idEmployee){
        return er.getCompensationXid(idEmployee);
    }


    @POST
    public Uni<Response> add(Employee e) {
        //ur.createdUser(p);

        return Panache.withTransaction( e:: persist)
                .replaceWith(Response.ok(e).status(CREATED)::build);
        //return Response.ok().build();
    }


    @GET
    @Path("empleadosPorManager")
    public Uni<List<PanacheEntityBase>> empleadosPorManager(){
        return er.empleadosPorManager();
    }


      /*
    @GET
    @Path("empleadosPorManagerConsumoContract")
    public Uni<List<PanacheEntityBase>> empleadosPorManagerConsumoContract(){
        return er.empleadosPorManagerConsumoContract();
    }
*/



//Fin
}



