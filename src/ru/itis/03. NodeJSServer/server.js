const express = require('express');
const parser = require('body-parser');
const app = express();
app.use(parser.urlencoded({ extended: true }));
require('./app/routes')(app);
app.use(express.static('public'));
app.listen(80);
console.log("server started at 80");