// convert numerical string representation to int type
int parseInt(char s[]) {
	 int i,n;

	 n = 0;
	 for(i=0;i<strlength(s);i++){
		 n = 10 * n + (s[i] - '0');
	 }
	 return n;
}
