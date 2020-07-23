const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const port = 81;
app.use(bodyParser.urlencoded({extended: true}));
require('./app/routes')(app);
app.use(express.static('public'));
app.listen(port);
console.log("Server started at " + port);