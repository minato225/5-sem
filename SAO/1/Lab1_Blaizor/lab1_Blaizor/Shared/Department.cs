using System.ComponentModel.DataAnnotations;

namespace lab1_Blaizor.Shared
{
    public class Department
    {
        [Key]
        public int DepartmentId { get; set; }
        public string DepartmentName { get; set; }
    }
}
