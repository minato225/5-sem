using lab1_Blaizor.Server.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using WcfServiceReference;
using System.Threading.Tasks;

namespace lab1_Blaizor.Server.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class DepartmentsController : ControllerBase
    {
        private readonly WCFServiceClient client;

        public DepartmentsController()
        {
            client = new WCFServiceClient();
        }

        [HttpGet]
        public async Task<ActionResult> GetDepartments()
        {
            try
            {
                var res = await client.GetDepartmentsAsync();
                return Ok(res);
            }
            catch (Exception)
            {
                return StatusCode(StatusCodes.Status500InternalServerError,
                    "Error retrieving data from the database");
            }
        }

        [HttpGet("{id:int}")]
        public async Task<ActionResult<Departments>> GetDepartment(int id)
        {
            try
            {
               // var result = await departmentRepository.GetDepartment(id);
                var result = await this.client.GetDepartmentAsync(id);

                return result is null ? NotFound() : result;
            }
            catch (Exception)
            {
                return StatusCode(StatusCodes.Status500InternalServerError,
                    "Error retrieving data from the database");
            }
        }
    }
}
