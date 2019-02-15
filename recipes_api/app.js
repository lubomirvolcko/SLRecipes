const express = require('express');
const app = express();
const morgan = require('morgan');
const bodyParser = require('body-parser');
/*
const mysql = require('mysql');
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'recipes2'
});
*/
const userRouter = require('./api/routes/user');
const mealRouter = require('./api/routes/meal');

app.use(morgan('short'));
app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());


app.get("/", (req, res, next) => {
    console.log("Responding to root route");
    res.send("Server is running");
});

app.use("/user", userRouter);
app.use("/meal", mealRouter);

const PORT = process.env.PORT || 3003
//localhost:PORT
app.listen(PORT, () => {
    console.log("Server is up and listening on : " + PORT);
});