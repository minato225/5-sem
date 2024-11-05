using System.ComponentModel.DataAnnotations;

namespace WcfService
{    
    public partial class Employees
    {
        [Key]
        public int EmployeeId { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Email { get; set; }
        public System.DateTime DateOfBrith { get; set; }
        public int Gender { get; set; }
        public int DepartmentId { get; set; }
        public string PhotoPath { get; set; }
    
        public Departments Departments { get; set; }
    }
}
