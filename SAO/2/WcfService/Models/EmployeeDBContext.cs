using System.Data.Entity;

namespace WcfService
{
    public class EmployeeDBEntities : DbContext
    {
        public EmployeeDBEntities(string connectionString)
        {
            Database.Connection.ConnectionString = connectionString;
        }

        public virtual DbSet<Departments> Departments { get; set; }
        public virtual DbSet<Employees> Employees { get; set; }
    }
}
