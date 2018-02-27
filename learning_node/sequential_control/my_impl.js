var fs = require('fs');

var configFilename = './rss_feeds.txt'
	
function writeFile(filename) {
	fs.writeFile(filename, 'Hello World', function(err){
		next(err, filename);
	});
}

function readFile(filename) {
	fs.readFile(filename, function(err, data) {
		(err) ? next(err) : next(null,data.toString());
	});
}

function print(data) {
	console.log(data);
}

tasks = [
	
	writeFile,
	readFile,
	print
];

function next(err, result) {
	if (err) throw err;
	
	task = tasks.shift();
	
	if(task) {
		task(result)
	}
}

next(null, '/home/matija/Desktop/testfile');

