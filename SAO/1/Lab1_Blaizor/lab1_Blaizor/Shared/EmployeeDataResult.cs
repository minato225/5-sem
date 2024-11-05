using System.Collections.Generic;

namespace lab1_Blaizor.Shared
{
    public class EmployeeDataResult
    {
        public IEnumerable<Employee> Employees { get; set; }
        public int Count { get; set; }
    }
}
