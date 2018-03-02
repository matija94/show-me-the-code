var http = require('http');
var url = require('url');
var todoItems = [];


var server = http.createServer(function(request, response) {
	switch (request.method) {
	case 'POST':
		var item = '';
		request.setEncoding('utf8')
		request.on('data', function(chunk){
			item += chunk;
		}); 
		request.on('end', function(){
			todoItems.push(item);
			response.end('OK\n');
		});
		break;

	default:
		response.statusCode = 404;
		response.end(request.method + ' not implemented\n');
	}
})

server.listen(3000);