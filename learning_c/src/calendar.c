/*
 * calendar.c
 *
 *  Created on: Mar 6, 2018
 *      Author: matija
 */


char *month_name(int n) {
	static char *months[] = {
			"Illegal month",
			"January",
			"February",
			"March",
			"April",
			"May",
			"June",
			"July",
			"August",
			"September",
			"October",
			"November",
			"December"
	};

	return (n < 1 || n > 12) ? months[0] : months[n];
}

