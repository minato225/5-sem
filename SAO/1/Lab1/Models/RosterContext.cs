using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Lab1.Models
{
	public sealed class RosterContext : DbContext
	{
		public RosterContext(DbContextOptions<RosterContext> options)
			: base(options) => Database.EnsureCreated();

        public DbSet<Roster> Roster { get; set; }
		public DbSet<Club> Clubs { get; set; }
	}
}
