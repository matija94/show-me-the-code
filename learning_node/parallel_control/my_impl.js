var fs = require('fs');
var completedTask = 0;
var tasks = [];
var wordCounts = {};
var filesDir = './text';


function checkIfComplete() {
	completedTask++;
	if (completedTask == tasks.length) {
		for (var index in wordCounts) {
			console.log(index + ': ' + wordCounts[index]);
		}
	}
}

function countWordsText(text) {
	var words = text
		.toLowerCase()
		.split(/\W+/)
		.sort();
	
	for (var index in words) {
		var word = words[index];
		if (word) {
			wordCounts[word] = (wordCounts[word]) ? wordCounts[word]+1 : 1;
		}
	}
}


fs.readdir(filesDir, function(err, files) {
	if(err) throw err
	
	for (var index in files) {
		var task = (function(file){
			return function() {
				fs.readFile(file, function(err, data) {
					if (err) throw err;
					countWordsText(data.toString());
					checkIfComplete();
				})
			}
		})(filesDir + '/' + files[index]);
		tasks.push(task);
	}
	for(var task in tasks) {
		tasks[task]();
	}
});
