var http = require('http');
var work = require('./lib/timetrack');
var postgres = require('pg');

var conString = "tcp://matija:sjm2254wow@localhost:5432/testing";
var db = new postgres.Client(conString);
db.connect();


var server = http.createServer(function(req,res){
	switch(req.method) {
	case 'POST':
		switch(req.url) {
		case '/':
			work.add(db,req,res);
			break;
		case '/archive':
			work.archive(db,req,res);
			break;
		case '/delete':
			work.delete(db,req,res);
			break;
		}
		break;
	case 'GET':
		switch(req.url) {
		case '/':
			work.show(db,res);
			break;
		case '/archived':
			work.showArchived(db,res);
		}
	}
});

db.query(
		"CREATE TABLE IF NOT EXISTS work ("
		+ "id serial NOT NULL, "
		+ "hours numeric(5,2) default 0, "
		+ "date date, "
		+ "archived smallint default 0,"
		+ "description text,"
		+ "PRIMARY KEY(id))",
		function(err, res) {
			if (err) {
				throw err;
			}
			console.log('Server started...');
			server.listen(3000, '127.0.0.1');
		}
	);