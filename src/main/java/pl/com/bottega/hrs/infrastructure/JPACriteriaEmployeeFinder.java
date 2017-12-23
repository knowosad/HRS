package pl.com.bottega.hrs.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.application.*;
import pl.com.bottega.hrs.application.dtos.BasicEmployeeDto;
import pl.com.bottega.hrs.application.dtos.DetailedEmployeeDto;
import pl.com.bottega.hrs.model.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class JPACriteriaEmployeeFinder implements EmployeeFinder {

    private EntityManager entityManager;

    public JPACriteriaEmployeeFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public EmployeeSearchResults search(EmployeeSearchCriteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BasicEmployeeDto> cq = cb.createQuery(BasicEmployeeDto.class);
        Root employee = cq.from(Employee.class);
        cq.select(cb.construct(BasicEmployeeDto.class,
                employee.get(Employee_.empNo),
                employee.get(Employee_.firstName),
                employee.get(Employee_.lastName)));

        Predicate predicate = buildPredicate(criteria, cb, employee);

        cq.where(predicate);
        cq.distinct(true);
        Query query = entityManager.createQuery(cq);
        query.setMaxResults(criteria.getPageSize());
        query.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize());
        List<BasicEmployeeDto> results = query.getResultList();
        EmployeeSearchResults employeeSearchResults = new EmployeeSearchResults();
        employeeSearchResults.setResults(results);
        int total = searchTotalCount(criteria);
        employeeSearchResults.setTotalCount(total);
        employeeSearchResults.setPageNumber(criteria.getPageNumber());
        employeeSearchResults.setPagesCount(total / criteria.getPageSize() +
                (total % criteria.getPageSize() == 0 ? 0 : 1));
        return employeeSearchResults;
    }

    @Override
    @Transactional
    public DetailedEmployeeDto getEmployeeDetails(Integer empNo) {
        Employee employee = entityManager.find(Employee.class, empNo);
        if(employee == null)
            throw new NoSuchEntityException();
        return new DetailedEmployeeDto(employee);
    }

    private int searchTotalCount(EmployeeSearchCriteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root employee = cq.from(Employee.class);
        cq.select(cb.count(employee));
        Predicate predicate = buildPredicate(criteria, cb, employee);
        cq.where(predicate);
        Query query = entityManager.createQuery(cq);
        return ((Long) query.getSingleResult()).intValue();
    }

    private Predicate buildPredicate(EmployeeSearchCriteria criteria, CriteriaBuilder cb, Root employee) {
        Predicate predicate = cb.conjunction();
        predicate = addFirstNamePredicate(criteria, cb, employee, predicate);
        predicate = addLastNamePredicate(criteria, cb, employee, predicate);
        predicate = addBirthDateFromPredicate(criteria, cb, employee, predicate);
        predicate = addBirthDateToPredicate(criteria, cb, employee, predicate);
        predicate = addDepartmentsPredicate(criteria, cb, employee, predicate);
        predicate = addEmpNoPredicate(criteria, cb, employee, predicate);
        predicate = addTitlesPredicate(criteria, cb, employee, predicate);
        predicate = addSalaryFromPredicate(criteria, cb, employee, predicate);
        predicate = addSalaryToPredicate(criteria, cb, employee, predicate);
        predicate = addHireDateFromPredicate(criteria, cb, employee, predicate);
        predicate = addHireDateToPredicate(criteria, cb, employee, predicate);
        return predicate;
    }

    private Predicate addEmpNoPredicate(EmployeeSearchCriteria criteria, CriteriaBuilder cb, Root employee, Predicate predicate){
        if (criteria.getEmpNo() != null){
            predicate= cb.and(predicate,
                    cb.equal(employee.get(Employee_.empNo), criteria.getEmpNo()));
        }
        return predicate;
    }
    private Predicate addLastNamePredicate(EmployeeSearchCriteria criteria, CriteriaBuilder cb, Root employee, Predicate predicate) {
        if (criteria.getLastNameQuery() != null) {
            predicate = cb.and(predicate,
                    cb.like(employee.get(Employee_.lastName), criteria.getLastNameQuery() + "%"));
        }
        return predicate;
    }

    private Predicate addFirstNamePredicate(EmployeeSearchCriteria criteria, CriteriaBuilder cb, Root employee, Predicate predicate) {
        if (criteria.getFirstNameQuery() != null) {
            predicate = cb.and(predicate,
                    cb.like(employee.get(Employee_.firstName), criteria.getFirstNameQuery() + "%"));
        }
        return predicate;
    }

    private Predicate addBirthDateFromPredicate(EmployeeSearchCriteria criteria, CriteriaBuilder cb, Root employee, Predicate predicate) {
        if (criteria.getBirthDateFrom() != null) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(employee.get(Employee_.birthDate), criteria.getBirthDateFrom()));
        }
        return predicate;
    }

    private Predicate addBirthDateToPredicate(EmployeeSearchCriteria criteria, CriteriaBuilder cb, Root employee, Predicate predicate) {
        if (criteria.getBirthDateTo() != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(employee.get(Employee_.birthDate), criteria.getBirthDateTo()));
        }
        return predicate;
    }

    private Predicate addHireDateFromPredicate(EmployeeSearchCriteria criteria, CriteriaBuilder cb, Root employee, Predicate predicate) {
        if (criteria.getHireDateFrom() != null) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(employee.get(Employee_.hireDate), criteria.getHireDateFrom()));
        }
        return predicate;
    }

    private Predicate addHireDateToPredicate(EmployeeSearchCriteria criteria, CriteriaBuilder cb, Root employee, Predicate predicate) {
        if (criteria.getHireDateTo() != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(employee.get(Employee_.hireDate), criteria.getHireDateTo()));
        }
        return predicate;
    }

    private Predicate addSalaryFromPredicate(EmployeeSearchCriteria criteria, CriteriaBuilder cb, Root employee, Predicate predicate) {
        if (criteria.getSalaryFrom() != null) {
            Join<Employee, Salary> salariesJoin = employee.join(Employee_.salaries);
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(salariesJoin.get(Salary_.salary), criteria.getSalaryFrom()),
                    cb.equal(salariesJoin.get(Salary_.toDate), TimeProvider.MAX_DATE));
        }
        return predicate;
    }

    private Predicate addSalaryToPredicate(EmployeeSearchCriteria criteria, CriteriaBuilder cb, Root employee, Predicate predicate) {
        if (criteria.getSalaryTo() != null) {
            Join<Employee, Salary> salariesJoin = employee.join(Employee_.salaries);
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(salariesJoin.get(Salary_.salary), criteria.getSalaryTo()),
                    cb.equal(salariesJoin.get(Salary_.toDate), TimeProvider.MAX_DATE));
        }
        return predicate;
    }

//    select distinct e.emp_no, e.first_name, e.last_name from employees e
//    inner join titles t on e.emp_no=t.emp_no
//    where 1=1 and (t.title in (? , ?)) and t.to_date>?;

    private Predicate addTitlesPredicate(EmployeeSearchCriteria criteria, CriteriaBuilder cb, Root employee, Predicate predicate) {
        if (criteria.getTitles() != null) {
            Join<Employee, Title> titleJoin = employee.join(Employee_.titles);
            predicate = cb.and(predicate,
                    cb.isTrue(titleJoin.get(Title_.id).get("title").in(criteria.getTitles())),
                    cb.equal(titleJoin.get(Title_.toDate), TimeProvider.MAX_DATE));
        }
        return predicate;
    }

//    select distinct e.emp_no, e.first_name, e.last_name from employees e
//    inner join dept_emp de on e.emp_no=de.emp_no
//    inner join departments d on de.dept_no=d.dept_no
//    where 1=1 and de.to_date>? and (d.dept_no in (? , ?));

    private Predicate addDepartmentsPredicate(EmployeeSearchCriteria criteria, CriteriaBuilder cb, Root employee, Predicate predicate) {
        if (criteria.getDepartmentNumbers() != null && criteria.getDepartmentNumbers().size() > 0) {
            Join<Employee, DepartmentAssignment> deptAssignJoin = employee.join(Employee_.departmentAssignments);
            Join<DepartmentAssignment, Department> departmentJoin = deptAssignJoin.join(DepartmentAssignment_.id).join("department");
            predicate = cb.and(predicate, departmentJoin.get(Department_.deptNo).in(criteria.getDepartmentNumbers()));
            predicate = cb.and(predicate, cb.equal(deptAssignJoin.get(DepartmentAssignment_.toDate), TimeProvider.MAX_DATE));
        }
        return predicate;
    }
}
