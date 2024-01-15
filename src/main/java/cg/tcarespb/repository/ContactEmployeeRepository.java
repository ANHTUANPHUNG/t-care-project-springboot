package cg.tcarespb.repository;

import cg.tcarespb.models.ContactEmployee;
import cg.tcarespb.models.enums.EContactStatus;
import cg.tcarespb.service.contactEmployee.response.ContactEmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactEmployeeRepository extends JpaRepository<ContactEmployee,String> {
    @Query("SELECT NEW cg.tcarespb.service.contactEmployee.response.ContactEmployeeResponse(ce.id, ce.cart.id, ce.cart.user.id, ce.employee.id) " +
            "FROM ContactEmployee ce " +
            "WHERE ce.employee.id = :idEmployee " +
            "AND ce.contactStatus = :status")
    Page<ContactEmployeeResponse> getContactEmployee(@Param("idEmployee") String idEmployee, @Param("status") EContactStatus status, Pageable pageable);


}
