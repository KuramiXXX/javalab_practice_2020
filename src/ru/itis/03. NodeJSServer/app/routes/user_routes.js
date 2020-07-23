bodyParser = require('body-parser').json;

module.exports = function (app) {
    app.get('/about', (request, response) => {
        let result = "Максим Иванов, профессиональный ничегонеделатель";
        response.setHeader("Content-Type", "application/json");
        response.send(JSON.stringify(result));
    });

    app.post('/resume', bodyParser, (request, response) => {
        console.log('ok');
        let body = request.body;
        const {Client} = require('pg');
        const client = new Client({
            host: 'localhost',
            database: 'requests',
            user: 'postgres',
            password: 'qwerty007'
        });
        const newuser = 'insert into users(username, request_time, request_message) values($1, $2, $3) RETURNING *';
        client.connect;
        client.query(newuser, [body['username'], body['request_time'], body['request_message']], (err, data) => {
            console.log(err);
            response.setHeader("Content-Type", "application/json");
            response.send(JSON.stringify(data.rows));
            client.end();
        });
    }, 'JSON');

    app.get('/users', (request, response) => {
        const {Client} = require('pg');
        const client = new Client({
            host: 'localhost',
            database: 'requests',
            user: 'postgres',
            password: 'qwerty007'
        });
        client.connect();
        client.query('SELECT * FROM users', (err, data) => {
            response.setHeader("Content-Type", "application/json");
            response.send(JSON.stringify(data.rows));
            client.end();
        });
    });
};