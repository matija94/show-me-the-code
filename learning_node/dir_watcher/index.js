var events = require('events')
var fs = require('fs')

const watchDir = './watch';
const processedDir = './done';

class Watcher extends events.EventEmitter {
	
	constructor(watchDir, processDir) {
		super();
		this.watchDir = watchDir;
		this.processDir = processDir;
	}
	
	watch() {
		var watcher = this;
		fs.readdir(this.watchDir, function(err, files){
			if (err) throw err;
			for (var index in files) {
				watcher.emit('process', files[index]);
			}
		});
	}
	
	start() {
		var watcher = this;
		fs.watchFile(this.watchDir, function(){
			watcher.watch();
		});
	}
}

var watcher = new Watcher(watchDir, processedDir);

watcher.on('process', function(file){
	var watchFile = this.watchDir + '/' + file;
	var processedFile = this.processDir + '/' + file.toLowerCase();
	
	fs.rename(watchFile, processedFile, function(err){
		if (err) throw err;
	});
});

watcher.start();