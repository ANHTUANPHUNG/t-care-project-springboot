package cg.tcarespb.repository;

import cg.tcarespb.models.Employee;
import cg.tcarespb.models.Schedule;
import cg.tcarespb.service.cart.request.CartFilterRequest;
import cg.tcarespb.service.cart.request.CartLocationFilterRequest;
import cg.tcarespb.service.cart.request.CartSkillFilterRequest;
import cg.tcarespb.service.employee.response.EmployeeFilterResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
    @Query("SELECT e.id FROM Employee e JOIN EmployeeSkill es ON e.id = es.employee.id " +
            "JOIN EmployeeServiceGeneral esg ON e.id = esg.employee.id " +
            "JOIN EmployeeInfo ei ON e.id = ei.employee.id " +
            "WHERE  check_list_intersection((SELECT GROUP_CONCAT(eskill.skill.id) FROM EmployeeSkill eskill WHERE e.id = eskill.employee.id), :#{#reqFilter.cartSkillIdList}) > 0 " +
            "AND check_list_intersection((SELECT GROUP_CONCAT(eser.service.id) FROM EmployeeServiceGeneral eser WHERE e.id = eser.employee.id), :#{#reqFilter.cartServiceIdList}) > 0 " +
            "AND check_list_intersection((SELECT GROUP_CONCAT(ein.addInfo.id) FROM EmployeeInfo ein WHERE e.id = ein.employee.id), :#{#reqFilter.cartInfoIdList}) > 0 "+
            "AND get_distance(e.locationPlace.latitude,e.locationPlace.longitude,:#{#reqFilter.latitude},:#{#reqFilter.longitude})<= :#{#reqFilter.distance}  "+
            "AND e.jobType = :#{#reqFilter.jobType} " +
            "AND ( e.priceMin  between :#{#reqFilter.priceMin} and :#{#reqFilter.priceMax} OR e.priceMax between :#{#reqFilter.priceMin} and :#{#reqFilter.priceMax} ) " +
            "AND  e.status =:#{#reqFilter.status}  GROUP BY e.id  ")
    Page<String> filter(@Param("reqFilter") CartFilterRequest reqFilter, Pageable pageable);

    @Query("SELECT e.id FROM Employee e JOIN EmployeeSkill es ON e.id = es.employee.id " +
            "JOIN EmployeeServiceGeneral esg ON e.id = esg.employee.id " +
            "JOIN EmployeeInfo ei ON e.id = ei.employee.id " +
            "WHERE  check_list_intersection((SELECT GROUP_CONCAT(eskill.skill.id) FROM EmployeeSkill eskill WHERE e.id = eskill.employee.id), :#{#reqFilter.cartSkillIdList}) > 0 " +
            "AND check_list_intersection((SELECT GROUP_CONCAT(eser.service.id) FROM EmployeeServiceGeneral eser WHERE e.id = eser.employee.id), :#{#reqFilter.cartServiceIdList}) > 0 " +
            "AND check_list_intersection((SELECT GROUP_CONCAT(ein.addInfo.id) FROM EmployeeInfo ein WHERE e.id = ein.employee.id), :#{#reqFilter.cartInfoIdList}) > 0 "+
            "AND get_distance(e.locationPlace.latitude,e.locationPlace.longitude,:#{#reqFilter.latitude},:#{#reqFilter.longitude})<= :#{#reqFilter.distance}  "+
            "AND e.jobType = :#{#reqFilter.jobType} " +
            "AND ( e.priceMin  between :#{#reqFilter.priceMin} and :#{#reqFilter.priceMax} OR e.priceMax between :#{#reqFilter.priceMin} and :#{#reqFilter.priceMax} ) " +
            "AND  e.status =:#{#reqFilter.status}  GROUP BY e.id ")
    List<String> filterTest(@Param("reqFilter") CartFilterRequest reqFilter);
    @Query("SELECT new cg.tcarespb.service.employee.response.EmployeeFilterResponse" +
            "(e.id," +
            "e.firstName ," +
            "e.lastName," +
            "e.descriptionAboutMySelf," +
            "e.priceMin," +
            "e.priceMax," +
            "e.experience," +
            "e.locationPlace.longitude," +
            "e.locationPlace.latitude," +
            "e.locationPlace.distanceToWork," +
            "e.locationPlace.name)" +
            "FROM Employee e JOIN EmployeeSkill es ON e.id = es.employee.id " +
            "JOIN EmployeeServiceGeneral esg ON e.id = esg.employee.id " +
            "JOIN EmployeeInfo ei ON e.id = ei.employee.id " +
            "WHERE  check_list_intersection((SELECT GROUP_CONCAT(eskill.skill.id) FROM EmployeeSkill eskill WHERE e.id = eskill.employee.id), :#{#reqFilter.cartSkillIdList}) > 0 " +
            "AND check_list_intersection((SELECT GROUP_CONCAT(eser.service.id) FROM EmployeeServiceGeneral eser WHERE e.id = eser.employee.id), :#{#reqFilter.cartServiceIdList}) > 0 " +
            "AND check_list_intersection((SELECT GROUP_CONCAT(ein.addInfo.id) FROM EmployeeInfo ein WHERE e.id = ein.employee.id), :#{#reqFilter.cartInfoIdList}) > 0 "+
            "AND get_distance(e.locationPlace.latitude,e.locationPlace.longitude,:#{#reqFilter.latitude},:#{#reqFilter.longitude}) <= :#{#reqFilter.distance}  "+
            "AND e.jobType = :#{#reqFilter.jobType} " +
            "AND (e.priceMin  between :#{#reqFilter.priceMin} and :#{#reqFilter.priceMax} OR e.priceMax between :#{#reqFilter.priceMin} and :#{#reqFilter.priceMax} ) " +
            "AND  e.status =:#{#reqFilter.status}  GROUP BY e.id ")
    Page<EmployeeFilterResponse> filterAllInfoEmployee(@Param("reqFilter") CartFilterRequest reqFilter,Pageable pageable);
}
