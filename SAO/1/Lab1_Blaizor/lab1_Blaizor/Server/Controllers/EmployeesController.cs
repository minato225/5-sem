using lab1_Blaizor.Server.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Threading.Tasks;   
using WcfServiceReference;

namespace lab1_Blaizor.Server.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class EmployeesController : ControllerBase
    {
        private readonly IEmployeeRepository employeeRepository;
        private readonly WCFServiceClient client;

        public EmployeesController(IEmployeeRepository employeeRepository)
        {
            this.employeeRepository = employeeRepository;
            this.client = new WCFServiceClient();
        }

        [HttpGet("all")]
        public async Task<ActionResult> GetEmployees()
        {
            try
            {
                // return Ok(await employeeRepository.GetAllEmployees());
                return Ok(await this.client.GetAllEmployeesAsync());
            }
            catch (Exception)
            {
                return StatusCode(StatusCodes.Status500InternalServerError,
                    "Error retrieving data from the database");
            }
        }

        [HttpGet("{id:int}")]
        public async Task<ActionResult<Employees>> GetEmployee(int id)
        {
            try
            {
                // var result = await employeeRepository.GetEmployee(id);
                var result = await this.client.GetEmployeeAsync(id);

                return result is null ? NotFound() : result;
            }
            catch (Exception)
            {
                return StatusCode(StatusCodes.Status500InternalServerError,
                    "Error retrieving data from the database");
            }
        }

        [HttpGet]
        public async Task<ActionResult> GetEmployees(int DepId)
        {
            try
            {
                //    return Ok(await employeeRepository.GetEmployees(DepId));
                return Ok(await this.client.GetEmployeesAsync(DepId));
            }
            catch (Exception)
            {
                return StatusCode(StatusCodes.Status500InternalServerError,
                    "Error retrieving data from the database");
            }
        }

        [HttpPut("{id:int}")]
        public async Task<ActionResult<Employees>> UpdateEmployee(int id, Employees employee)
        {
            try
            {
                if (id != employee.EmployeeId)
                {
                    return BadRequest("Employee ID mismatch");
                }

                // if (await employeeRepository.GetEmployee(id) is null)
                if (await this.client.GetEmployeeAsync(id) is null)
                {
                    return NotFound($"Employee with Id = {id} not found");
                }

                //return await employeeRepository.UpdateEmployee(employee);

                var t = await this.client.UpdateEmployeeAsync(employee);
                return t;
            }
            catch (Exception)
            {
                return StatusCode(StatusCodes.Status500InternalServerError,
                    value: "Error updating employee record");
            }
        }
    }
}
