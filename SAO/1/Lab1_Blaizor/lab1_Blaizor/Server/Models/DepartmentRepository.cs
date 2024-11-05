using lab1_Blaizor.Shared;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace lab1_Blaizor.Server.Models
{
    public class DepartmentRepository : IDepartmentRepository
    {
        private readonly AppDbContext appDbContext;

        public DepartmentRepository(AppDbContext appDbContext) => this.appDbContext = appDbContext;

        public async Task<Department> GetDepartment(int departmentId) =>
            await appDbContext.Departments.FirstOrDefaultAsync(d => d.DepartmentId == departmentId);

        public async Task<IEnumerable<Department>> GetDepartments() =>
            await appDbContext.Departments.ToListAsync();
    }
}
