using lab1_Blaizor.Shared;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Json;
using System.Threading.Tasks;

namespace lab1_Blaizor.Client.Services
{
    public class EmployeeService : IEmployeeService
    {
        private readonly HttpClient httpClient;

        public EmployeeService(HttpClient httpClient) => this.httpClient = httpClient;

        public async Task<IEnumerable<Employee>> GetAllEmployees() =>
            await httpClient.GetFromJsonAsync<IEnumerable<Employee>>($"api/employees/all");

        public async Task<IEnumerable<Employee>> GetEmployees(int departmentId) =>
            await httpClient.GetFromJsonAsync<IEnumerable<Employee>>(
                $"api/employees?DepId={departmentId}");

        public async Task<Employee> UpdateEmployee(Employee employee)
        {
            var response = await httpClient.PutAsJsonAsync($"/api/employees/{employee.EmployeeId}", employee);
            return await response.Content.ReadFromJsonAsync<Employee>();
        }
    }
}