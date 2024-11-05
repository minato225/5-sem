using lab1_Blaizor.Shared;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Json;
using System.Threading.Tasks;

namespace lab1_Blaizor.Client.Services
{
    public class DepartmentService : IDepartmentService
    {
        private readonly HttpClient httpClient;

        public DepartmentService(HttpClient httpClient)
        {
            this.httpClient = httpClient;
        }

        public async Task<IEnumerable<Department>> GetAllDepartments() => 
            await httpClient.GetFromJsonAsync<IEnumerable<Department>>("/api/departments");

        public async Task<Department> GetDepartment(int departmentId) =>
            await httpClient.GetFromJsonAsync<Department>($"/api/departments/{departmentId}");
    }
}