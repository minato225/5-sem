using System.ComponentModel.DataAnnotations;

namespace WcfService
{
    public class Departments
    {
        [Key]
        public int DepartmentId { get; set; }
        public string DepartmentName { get; set; }
    }
}
