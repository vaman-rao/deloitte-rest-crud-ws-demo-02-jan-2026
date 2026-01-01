package com.demo.rest;

import com.demo.model.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    // In-memory data store (for demo only)
    private static Map<Integer, Employee> db = new HashMap<>();

    static {
        db.put(101, new Employee(101, "Sonu"));
        db.put(102, new Employee(102, "Monu"));
        db.put(103, new Employee(103, "Tonu"));
    }

    // GET all
    @GET
    public Collection<Employee> getAllEmployees() {
        return db.values();
    }

    // GET by id
    @GET
    @Path("/{id}")
    public Employee getEmployee(@PathParam("id") int id) {
        return db.get(id);
    }

    // POST - create
    @POST
    public Response addEmployee(Employee emp) {
        db.put(emp.getId(), emp);
        return Response.status(Response.Status.CREATED).entity(emp).build();
    }

    // PUT - full update
    @PUT
    @Path("/{id}")
    public Response updateEmployee(@PathParam("id") int id, Employee emp) {
        emp.setId(id);
        db.put(id, emp);
        return Response.ok(emp).build();
    }

    // PATCH - partial update
    @PATCH
    @Path("/{id}")
    public Response patchEmployee(@PathParam("id") int id, Map<String, Object> updates) {
        Employee emp = db.get(id);
        if (emp == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (updates.containsKey("name")) {
            emp.setName((String) updates.get("name"));
        }
        return Response.ok(emp).build();
    }

    // DELETE
    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") int id) {
        Employee removed = db.remove(id);
        if (removed == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
