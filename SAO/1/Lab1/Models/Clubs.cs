using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace Lab1.Models
{
	public class Club
	{
		[Key]
		public int ClubId { get; set; }
		public string Name { get; set; }
		public string Country { get; set; }
	}
}
