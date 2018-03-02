var stats = require('my_statistics_module');

arr = [2,4,6,8,10];

console.log(stats.mean(arr));


var events = require('events');
var net = require('net');

var channel = new events.EventEmitter();
channel.clients = {};
channel.subscriptions = {};

function welcome(clientSocket, channel) {
	var welcome = 'Welcome!\n' + 'Guests online: ' + channel.listenerCount('broadcast');
	clientSocket.write(welcome + '\n');
}

channel.on('join', function(id,client){

	welcome(client, this);
	
	this.clients[id] = client;
	this.subscriptions[id] = function(senderId, message) {
		if (id != senderId){
			this.clients[id].write(message);
		}
	}
	this.on('broadcast', this.subscriptions[id]);
});

channel.on('leave', function(id){
	channel.removeListener('broadcast', this.subscriptions[id]);
	channel.emit('broadcast', id, id + ' has left the chat.\n');
});


channel.on('shutdown', function(){
	channel.emit('broadcast', '', 'Chat has shut down.\n');
	channel.removeAllListeners('broadcast');
});



var server = net.createServer(function(client) {
	var id = client.remoteAddress + ':' + client.remotePort;
	console.log('Client ', id, ' has connected!');
	
	channel.emit('join', id, client);

	client.on('data', function(data) {
		data = data.toString();
		if (data == 'shutdown\r\n') {
			channel.emit('shutdown');
		}
		channel.emit('broadcast', id, data);
	});
	
	client.on('end', function(){
		channel.emit('leave', id);
	})
});

server.listen(8888);



