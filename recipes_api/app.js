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
    res.send("Hello from ROOOT");
});

app.use("/user", userRouter);
app.use("/meal", mealRouter);

//localhost:3003
app.listen(3003, () => {
    console.log("Server is up and listening on 3003...");
});