
var http = require('http');
var url = require('url');
var qs = require('querystring')

var todoItems = [];

var events = require('events')

/**
 * Parses url and validates extracted item id. If id is not valid sets appropriate response status code and message. Returns id value on valid id and -1 on invalid id value
 * @param path
 * @param response
 * @returns
 */
function validateItemIdAndSetResponseOnError(requestUrl, response) {
	var path = url.parse(requestUrl).pathname;
	var id = parseInt(path.slice(1), 10)
	
	if(isNaN(id)) {
		response.statusCode = 400;
		repsonse.end('Invalid item id\n');
		id = -1;
	}
	else if(!todoItems[id]) {
		response.statusCode = 404;
		response.end('Item not found\n');
		id = -1;
	}
	return id;
	
}

/**
 * Parses todo item from http client request. New event 'parsedItem' will be emitted by emitter once item is read
 * @param request - Http client request
 * @param emitter - EventEmitter
 * @returns
 */

function parseItem(request, emitter) {
	var item = '';
	request.setEncoding('utf8')
	request.on('data', function(chunk){
		item += chunk;
	}); 
	request.on('end', function(){
		emitter.emit('parsedItem', item);
	});
}

function add(req, res) {
	var body = '';
	req.setEncoding('utf8');
	req.on('data', function(chunk){ body += chunk });
	req.on('end', function(){
		var obj = qs.parse(body);
		todoItems.push(obj.item);
		show(res);
	});
}


function show(res) {
	var html = '<html><head><title>Todo List</title></head><body>'
	+ '<h1>Todo List</h1>'
	+ todoItems.map(function(item){
		return '<li>' + item + '</li>'
	}).join('')
	+ '</ul>'
	+ '<form method="post" action="/">'
	+ '<p><input type="text" name="item" /></p>'
	+ '<p><input type="submit" value="Add Item" /></p>'
	+ '</form></body></html>';
	res.setHeader('Content-Type', 'text/html');
	res.setHeader('Content-Length', Buffer.byteLength(html));
	res.end(html);

}

function notFound(res) {
	res.statusCode = 404;
	res.setHeader('Content-Type', 'text/plain');
	res.end('Not Found');
}

function badRequest(res) {
	res.statusCode = 400;
	res.setHeader('Content-Type', 'text/plain');
	res.end('Bad Request');
}

var server = http.createServer(function(request, response) {
	var emitter = new events.EventEmitter();
	switch (request.method) {
	case 'POST':
		if (url.parse(request.url).pathname == '/api') {
			parseItem(request,emitter);
			emitter.on('parsedItem', item => todoItems.push(item));
			response.end('OK\n');
		}
		else if (request.url == '/') {
			add(request, response);
		}
		break;
	
	case 'GET':
		if (url.parse(request.url).pathname == '/api') {
			var body = todoItems.map( (item, i) => i + ')' + item + '\n').join('');
			response.setHeader('Content-Length', Buffer.byteLength(body))
			response.setHeader('Content-Type', 'text/plain; charset="utf-8"');
			response.end(body);
		}
		else if (request.url == '/') {
			show(response);
		}
		break;

	case 'DELETE':
		if (url.parse(request.url).pathname == '/api') {
			id = validateItemIdAndSetResponseOnError(request.url, response);
			if (id != -1) {
				// valid id
				todoItems.splice(id, 1);
				response.end('OK\n');
			}
		}
		else {
			notFound(response);
		}
		break;
		
	case 'PUT':
		if (url.parse(request.url).pathname == '/api') {
			id = validateItemIdAndSetResponseOnError(request.url, response);
			if (id != -1) { 
				// valid id
				parseItem(request, emitter)
				emitter.on('parsedItem', item => todoItems[id]=item);
				response.end('OK\n');
			}
		}
		else {
			notFound(response);
		}
		break;
	
	default:
		response.statusCode = 404;
		response.end(request.method + ' not implemented\n');
	}
})

server.listen(3000);