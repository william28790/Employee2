package org.servicioemployee.repositorio;


import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.servicioemployee.entites.*;


import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ApplicationScoped
public class Repositorio implements PanacheRepositoryBase<Employee,Long> {

    @Inject
    Vertx vertx;


    private WebClient webClientContract;
    private WebClient webClientLearning;
    private WebClient webClientCompensation;




    @PostConstruct
    void initialize(){
        this.webClientContract = WebClient.create(vertx ,
                new WebClientOptions().setDefaultHost("localhost")
                        .setDefaultPort(8087).setSsl(false).setTrustAll(true));
        this.webClientLearning = WebClient.create(vertx ,
                new WebClientOptions().setDefaultHost("localhost")
                        .setDefaultPort(8086).setSsl(false).setTrustAll(true));
        this.webClientCompensation = WebClient.create(vertx ,
                new WebClientOptions().setDefaultHost("localhost")
                        .setDefaultPort(8088).setSsl(false).setTrustAll(true));
    }


// devuelve todos los contratos
    private Uni<List<Contract>> getListContract(){
        return webClientContract.get(8087, "localhost", "/contract").send()
                .onFailure().invoke(res -> log.error("Error recuperando productos ", res))
                .onItem().transform(res -> {
                    List<Contract> lista = new ArrayList<>();
                    JsonArray objects = res.bodyAsJsonArray();
                    objects.forEach(p -> {
                        log.info("See Objects: " + objects);
                        ObjectMapper objectMapper = new ObjectMapper();
                        // Pass JSON string and the POJO class
                        Contract con = null;
                        try {
                            con = objectMapper.readValue(p.toString(), Contract.class);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        lista.add(con);
                    });
                    return lista;
                });
    }

// devuelve todas las compensaciones

//Devuelve los contratos de los manager a traves de el parametro true de la url
    public Uni<List<Contract>> getListContractManager(){
        return webClientContract.get(8087,"localhost","/contract/true/contract").send()
                .onFailure().invoke(res -> log.error("Error recuperando productos ", res))
                .onItem().transform(res -> {
                    List<Contract> lista = new ArrayList<>();
                    JsonArray objects = res.bodyAsJsonArray();
                    objects.forEach(p -> {
                        log.info("See Objects: " + objects);
                        ObjectMapper objectMapper = new ObjectMapper();
                        // Pass JSON string and the POJO class
                        Contract con = null;
                        try {
                            con = objectMapper.readValue(p.toString(), Contract.class);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        lista.add(con);
                    });
                    return lista;
                });
    }


    private Uni<List<Compensation>> getListCompensation(){
        return webClientCompensation.get(8088, "localhost", "/compensation").send()
                .onFailure().invoke(res -> log.error("Error recuperando productos ", res))
                .onItem().transform(res -> {
                    List<Compensation> lista = new ArrayList<>();
                    JsonArray objects = res.bodyAsJsonArray();
                    objects.forEach(p -> {
                        log.info("See Objects: " + objects);
                        ObjectMapper objectMapper = new ObjectMapper();
                        Compensation com = null;
                        try {
                            com = objectMapper.readValue(p.toString(), Compensation.class);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        lista.add(com);
                    });
                    return lista;
                });
    }


    public Uni<Compensation> getCompensationXid(Long idEmployee){
       String url =  "/compensation/"+idEmployee.toString()+"/compenasation";
        return webClientCompensation.get(8088, "localhost", url)
                .send()
                .onFailure().invoke(res -> log.error("Error recuperando productos ", res))
                .onItem().transform(res -> {
                   // List<Compensation> lista = new ArrayList<>();
                    JsonArray objects = res.bodyAsJsonArray(); // el dato lo tengo aca
                   // objects.forEach(p -> { //se borra
                        log.info("See Objects: " + objects);
                        ObjectMapper objectMapper = new ObjectMapper();
                        Compensation com = null;
                        try {
                           // com = objectMapper.readValue(p.toString(), Compensation.class);  esta se borra
                            com = objectMapper.readValue(objects.toString(), Compensation.class);  // se modifica
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                     //   lista.add(com);
                   // });
                    return com;
                });
    }


    //v1 son todos los empleados
    //V2 son los contractos de los manager
    //DEVUELVE LISTA DE EMPLEADOS MANAGER
    public Uni<List<Employee>> getEmployeeIsManager() {
            return Uni.combine().all().unis(getListEmployees(),getListContractManager())
                    .combinedWith((v1,v2) ->{
                        List<Employee> lista=new ArrayList<>();
                        v1.forEach(e ->{
                            v2.forEach(c ->{

                                if(e.getId()== c.getEmployeeId()){
                                    //si son manager se le setea el contract y se lo agrega a la lista para el retorno
                                    //e.setContract(c);
                                    lista.add(e);
                                }
                            });
                        } );
                        return lista;
                    });
    }


    //LISTA DE TODAS LAS COMPENSACIONES


// busca por id del impleado
    private Uni<Employee> getEmployeeReactive(Long Id){
        return Employee.findById(Id);
    }

    // devuelve la lista de todos los empleados
    private Uni<List<Employee>> getListEmployees(){
        return Employee.listAll();
    }

    //V1 Es el EMPLOYEE que se trae cunado le pasamos el ID
    // V2 Listado de los Contract que estan en la base se consume desde el servicio Contract.
    // Devuelve un empleado con contrato la busqueda se hace por el id del empleado que esta en el contrato.
    public Uni<Employee> getContractPorId(@PathParam("Id") Long Id) {
        return Uni.combine().all().unis(getEmployeeReactive(Id), getListContract())
                .combinedWith((v1,v2) -> {
                   // Contract contract=new Contract();
                    //log.info("Lista contratos" + v2.toString());
                    for (Contract con : v2)
                   // v2.forEach(con -> {
                    {
                       // log.info("Antes de entrar al IF " + con.getId());
                       if(con.getEmployeeId()== v1.getId()){
                           v1.setContract(con);

                        }
                    }
                    return v1;
                });
    }

    //Devuelve lista de los empleados del manager (Solo empleado sin contrato ni compensaciones)
@QueryParam("idManager")
    public Uni<List<Employee>> getEmployeesDeManager(Long idManager) {
        return Employee.find("managerId= ?1", idManager).list();
    }

    //Lista de empleados buscados por el id del manager
    //Los trae con sus contratos
    public Uni<List<Employee>> getEmployeesConContract(Long idManager) {
        return Uni.combine().all().unis(getEmployeesDeManager(idManager), getListContract())
                .combinedWith((v1,v2) ->{
                    List<Employee>  list= new ArrayList<>();
                    for (Employee em : v1){
                        Compensation com = (Compensation) getCompensationXid(em.getId());

                        if(com != null) {
                            em.setCompensation(com);
                        }
                        for(Contract c :v2 ){
                            if(em.getId()== c.getEmployeeId())
                                em.setContract(c);
                        }
                       // for (Compensation com : v3){
                         //   if(em.getId() == com.getHomeCNUM())
                           //     em.setCompensation(com);
                        //}
                        list.add(em);
                    }
                    return list;
                });
    }

    //Lista de empleados buscados por el id del manager
    //Empleado completo con contrato y compensaciones
    public Uni<List<Employee>> getEmployeesCompletos(Long idManager) {
        return Uni.combine().all().unis(getEmployeesConContract(idManager), getListCompensation())
                .combinedWith((v1,v2) ->{
                    List<Employee>  list= new ArrayList<>();

                    for (Employee em : v1){

                        Compensation com = (Compensation) getCompensationXid(em.getId());
                        if (com != null){

                            em.setCompensation(com);
                        }

                      //  for(Compensation c :v2 ){
                    //        if(em.getId()==c.getHomeCNUM())
                     //           em.setCompensation(c);
                    //    }
                        // for (Compensation com : v3){
                        //   if(em.getId() == com.getHomeCNUM())
                        //     em.setCompensation(com);
                        //}
                        list.add(em);
                    }
                    return list;
                });
    }

    //se le pasa por parametro el id del manager y devuelve la cantidad de empleados a cargo
@QueryParam("idManager")
    public Uni<Long> managerYCantEmpleados(Long idManager) {
        return  find("managerId=?1",idManager).count();
    }



    

}
