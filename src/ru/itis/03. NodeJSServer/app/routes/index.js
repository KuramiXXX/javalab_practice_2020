const usersRoutes = require("./user_routes");
module.exports = function (app) {
    usersRoutes(app);
};