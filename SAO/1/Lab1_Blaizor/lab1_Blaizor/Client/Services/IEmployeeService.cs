﻿using lab1_Blaizor.Shared;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace lab1_Blaizor.Client.Services
{
    public interface IEmployeeService
    {
        Task<IEnumerable<Employee>> GetAllEmployees();
        Task<IEnumerable<Employee>> GetEmployees(int departmentId);
        Task<Employee> UpdateEmployee(Employee employee);
    }
}