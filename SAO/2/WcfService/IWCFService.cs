using System.Collections.Generic;
using System.ServiceModel;

namespace WcfService
{
    [ServiceContract]
    public interface IWCFService
    {
        [OperationContract]
        List<Employees> GetEmployees(int departmentId);

        [OperationContract]
        List<Employees> GetAllEmployees();

        [OperationContract]
        Employees GetEmployee(int employeeId);

        [OperationContract]
        Employees UpdateEmployee(Employees employee);

        [OperationContract]
        List<Departments> GetDepartments();
        
        [OperationContract]
        Departments GetDepartment(int departmentId);
    }
}