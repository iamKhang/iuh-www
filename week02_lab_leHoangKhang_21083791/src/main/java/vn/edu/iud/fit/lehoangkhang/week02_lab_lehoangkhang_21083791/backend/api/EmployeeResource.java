package vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.business.EmployeeBO;
import vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.entites.Employee;
import vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.enums.EmployeeStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Path("/employees")
public class EmployeeResource {
    @Inject
    private EmployeeBO employeeBO;

    @GET
    @Path("/all")
    public Response getAllEmployees() {
        return Response.ok(employeeBO.findAll()).build();
    }

    @POST
    public Response addEmployee(
            @FormParam("fullName") String fullName,
            @FormParam("dob") String dob,  // Chuỗi ngày tháng
            @FormParam("email") String email,
            @FormParam("phone") String phone,
            @FormParam("address") String address,
            @FormParam("status") int status) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime dateOfBirth;
        try {
            LocalDate localDate = LocalDate.parse(dob, formatter);
            dateOfBirth = localDate.atStartOfDay();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid date format. Please use dd/MM/yyyy.")
                    .build();
        }

        Employee employee = new Employee();
        employee.setFullName(fullName);
        employee.setDob(dateOfBirth);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setAddress(address);
        employee.setStatus(EmployeeStatus.fromValue(status));
        employeeBO.addEmployee(employee);
        return Response.ok(employee).build();
    }
}
