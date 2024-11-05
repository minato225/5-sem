using System;
using System.Linq;
using System.Collections.Generic;
using System.Configuration;
using Microsoft.EntityFrameworkCore;

namespace WcfService
{
    public class WcfService : IWCFService
    {
        private readonly EmployeeDBEntities _appDbContext;

        public WcfService()
        {
            var connectionString = ConfigurationManager.ConnectionStrings["MyConnection"].ToString();
            _appDbContext = new EmployeeDBEntities(connectionString);
        }

        public Employees AddEmployee(Employees employee)
        {
            employee.Departments= this.GetDepartment(employee.DepartmentId)
                ?? throw new Exception("Invalid Department Employee Id.");

            var result = _appDbContext.Employees.Add(employee);
            _appDbContext.SaveChanges();
            return result;
        }

        public List<Employees> GetAllEmployees() =>
            _appDbContext.Employees
            .Include(x => x.Departments)
            .ToList();

        public Employees GetEmployee(int employeeId) =>
            _appDbContext.Employees
                .Include(e => e.Departments)
                .FirstOrDefault(e => e.EmployeeId == employeeId);

        public List<Employees> GetEmployees(int departmentId) =>
            _appDbContext.Employees
                .Where(x => x.DepartmentId == departmentId)
                .Include(x => x.Departments)
                .ToList();

        public Employees UpdateEmployee(Employees employee)
        {
             var result = _appDbContext.Employees
                .FirstOrDefault(e => e.EmployeeId == employee.EmployeeId);

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
            else if (employee.Departments != null)
            {
                result.DepartmentId = employee.Departments.DepartmentId;
            }
            result.PhotoPath = employee.PhotoPath;

            _appDbContext.SaveChanges();

            return result;
        }

        public Departments GetDepartment(int departmentId) =>
            (from department in _appDbContext.Departments
            where department.DepartmentId == departmentId
            select department).First();

        public List<Departments> GetDepartments()
        {
            var t = (from department in _appDbContext.Departments select department).ToList();
            return t;
        }
    }
}
