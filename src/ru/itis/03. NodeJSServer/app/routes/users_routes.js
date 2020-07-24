bodyParser = require("body-parser").json();

module.exports = function (app) {
    app.get('/profile', (request, response) => {
        let result = {
            "first_name":"Иванов",
            "last_name":"Максим",
            "age":19,
            "phone":"8952-031-85-00",
            "email":"super.maxa2001@yandex.ru",
            "profession":"Студент ИТИСа",
            "skills":["Чтоб я еще хоть раз в жизни занялся фронтом", "Я ж этого не выдержу", "Жесть"],
            "info":"Почему же, блин, моя версия сайта не работает??? Пиец обидно, я 6 дней искал ошибку, а в итоге она была в том, что вместо круглых обычных скобочек я поставил квадратные, когда создавал столбцы в БД"
        }
        response.setHeader("Content-Type", "application/json");
        response.send(JSON.stringify(result));
    });

    app.post('/resume', bodyParser, (request, response) => {
        let body = request.body;
        const {Client} = require('pg');
        const client = new Client({
            user: 'postgres',
            host: 'localhost',
            password: 'qwerty007',
            database: 'mysitedb'
        });
        const req = 'INSERT INTO resumes(name, phone, text) VALUES($1, $2, $3) RETURNING *';
        client.connect();
        client.query(req,
            [body['name'], body['phone'], body['text']],
            (err, data) => {
                response.setHeader("Content-Type", "application/json");
                response.send(JSON.stringify(data.rows));
                client.end();
            })
    });

    app.get('/resume', (request, response) => {
        const { Client } = require('pg');
        const client = new Client({
            user: 'postgres',
            host: 'localhost',
            password: 'qwerty007',
            database: 'mysitedb'
        });
        client.connect();
        client.query('SELECT name, phone, text FROM resumes', (err, data) => {
            response.setHeader("Content-Type", "application/json");
            response.send(JSON.stringify(data.rows));
            client.end();
        });
    });
};