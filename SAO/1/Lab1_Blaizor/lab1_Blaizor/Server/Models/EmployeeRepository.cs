using lab1_Blaizor.Shared;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace lab1_Blaizor.Server.Models
{
    public class EmployeeRepository : IEmployeeRepository
    {
        private readonly AppDbContext appDbContext;
        private readonly IDepartmentRepository departmentRepository;

        public EmployeeRepository(AppDbContext appDbContext, IDepartmentRepository departmentRepository)
        {
            this.appDbContext = appDbContext;
            this.departmentRepository = departmentRepository;
        }

        public async Task<Employee> AddEmployee(Employee employee)
        {
            employee.Department = await departmentRepository.GetDepartment(employee.DepartmentId) 
                ?? throw new Exception("Invalid Department Employee Id.");

            var result = await appDbContext.Employees.AddAsync(employee);
            await appDbContext.SaveChangesAsync();
            return result.Entity;
        }

        public async Task<IEnumerable<Employee>> GetAllEmployees() => 
            await appDbContext.Employees
            .Include(x => x.Department)
            .ToListAsync();

        public async Task<Employee> GetEmployee(int employeeId) => 
            await appDbContext.Employees
                .Include(e => e.Department)
                .FirstOrDefaultAsync(e => e.EmployeeId == employeeId);

        public async Task<IEnumerable<Employee>> GetEmployees(int departmentId) =>
            await appDbContext.Employees
                .Where(x => x.DepartmentId == departmentId)
                .Include(x => x.Department)
                .ToListAsync();

        public async Task<Employee> UpdateEmployee(Employee employee)
        {
            var result = await appDbContext.Employees
                .FirstOrDefaultAsync(e => e.EmployeeId == employee.EmployeeId);

            if (result == null) return null;
            
            result.FirstName = employee.FirstName;
            result.LastName = employee.LastName;
            result.Email = employee.Email;
            result.DateOfBrith = employee.DateOfBrith;
            result.Gender = employee.Gender;
            if (employee.DepartmentId != 0)
            {
                result.DepartmentId = employee.DepartmentId;
            }
            else if (employee.Department != null)
            {
                result.DepartmentId = employee.Department.DepartmentId;
            }
            result.PhotoPath = employee.PhotoPath;

            await appDbContext.SaveChangesAsync();

            return result;
        }
    }
}