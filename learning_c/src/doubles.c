#include <ctype.h>

double parseDouble(char s[]) {
	double val,power;
	int i,sign;
	i=0;
	sign = (s[i]=='-')?-1:1;
	if (s[i] == '+' || s[i] == '-') {
		i++;
	}
	for (val=0.0;isdigit(s[i]);i++){
		val = 10.0 * val + s[i] - '0';
	}
	if (s[i] == '.' || s[i] == ',') {
		i++;
	}
	for (power=1.0;isdigit(s[i]);i++) {
		val = 10.0 * val + s[i] - '0';
		power *= 10.0;
	}

	return sign * val / power;
}
