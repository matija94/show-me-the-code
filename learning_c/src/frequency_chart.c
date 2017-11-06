#include <stdio.h>
#include <stdlib.h>



/* print vertical bar chart with frequencies of word tokens, empty-space tokens and numbers tokens */
void freqChart() {
	int words, space, numbers;
	words = space = numbers = 0;
	char c;
	// count occurrence of letters ,spaces and numbers
	while ((c = getchar()) != '\n') {
		if (c >= '0' && c <= '9')
			numbers += 1;
		else if (c == ' ' || c == '\t' || c == '\n')
			space += 1;
		else
			words += 1;
	}

	// calculate chart height
	int height;
	if (words > space && words > numbers)
		height = words + 1;
	else if (space > words && space > numbers)
		height = words + 1;
	else
		height = numbers + 1;

	// x-axis
	char s[] = " letters  space  numbers";

	// points for ticks on x-axis
	int wordsLabel = 4;
	int spaceLabel = 12;
	int numLabel = 20;
	// width of the chart
	int width = strlen(s);

	// draw empty chart
	char chart[height][width];
	int i, j;
	for (i = 0; i < height; i++) {
		for (j = 0; j < width; j++) {
			chart[i][j]=' ';
		}
	}
	// draw x-axis labels
	for (i = 0; i < width; i++) {
		chart[height - 1][i] = s[i];
	}

	// draw bars
	for (i = height - 2; i >= 0; i--) {
		if (words > 0) {
			chart[i][wordsLabel] = '|';
			words -= 1;
		}
		if (space > 0) {
			chart[i][spaceLabel] = '|';
			space-= 1;
		}
		if (numbers > 0) {
			chart[i][numLabel] = '|';
			numbers -= 1;
		}
	}


	// print chart
	for (i = 0; i < height; i++) {
		for (j = 0; j < width; j++) {
			printf("%c", chart[i][j]);
		}
		printf("\n");
	}

}

/*int main(void) {

	freqChart();
}*/
