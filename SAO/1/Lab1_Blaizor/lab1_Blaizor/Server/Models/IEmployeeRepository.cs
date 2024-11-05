using lab1_Blaizor.Shared;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace lab1_Blaizor.Server.Models
{
    public interface IEmployeeRepository
    {
        Task<IEnumerable<Employee>> GetEmployees(int departmentId);
        Task<IEnumerable<Employee>> GetAllEmployees();
        Task<Employee> GetEmployee(int employeeId);
        Task<Employee> UpdateEmployee(Employee employee);
    }
}
