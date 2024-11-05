using Lab1.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Linq;

namespace Lab1.Controllers
{
    public class HomeController : Controller
    {
        private readonly RosterContext _context;

        public HomeController(RosterContext context) => _context = context;

        [Route("/")]
        public IActionResult Index() => View(_context.Clubs);

        [Route("/Players")]
        public IActionResult Players()
        {
            return View(_context.Roster.ToList());
        }

        [Route("/InsertRoster")]
        public IActionResult InsertRoster()
        {
            return Ok("sdss");
            return View();
        }

        [HttpPost]
        [Route("/Test")]
        public IActionResult Test([FromForm] Roster roster)
        {
            try
            {
                roster.playerid = Guid.NewGuid().ToString();

                _context.Entry(roster).State = EntityState.Modified;
                _context.Add(roster);
                _context.SaveChanges();
                return new RedirectResult($"/Players?clubId=1");

            }
            catch (Exception e)
            {
                ViewData.Add("error", e.Message);
                return View("InsertRoster", roster);
            }
        }
    }
}
