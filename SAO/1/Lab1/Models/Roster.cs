using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace Lab1.Models
{
	public class Roster
	{
		[Key]
		public string playerid { get; set; }
		public string jersey { get; set; }
		public string fname { get; set; }
		public string sname { get; set; }
		public string position { get; set; }
		public DateTime birthday { get; set; }
		public string weight { get; set; }
		public string height { get; set; }
		public string birthcity { get; set; }
		public string birthstate { get; set; }
		public int ClubId { get; set; }
	}
}
