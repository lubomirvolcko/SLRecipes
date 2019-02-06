const express = require('express');
const router = express.Router();
const mysql = require('mysql');
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'recipes2'
});


//get all users
router.get('/', (req, res, next) => {
    const qryStr = "select * from user"
    connection.query(qryStr, (err, rows, fields) => {
        if(err) {
            console.log("Failed to query for users: " + err);
            res.sendStatus(500);
            res.send("Failed to get data");
            return
        }
        /*
        const users =  rows.map((row) => {
            return {username: row.username};
        });

        res.status(200).json(users);
        */
       res.status(200).json(rows);
    }); 
});

//get one user by id
router.get("/:id", (req, res) => {
    const userId = req.params.id;
    const qryStr = "select * from user where id = ?"
    connection.query(qryStr, [userId], (err, rows, fields) => {
        res.status(200).json(rows);
    });
});

//login user
router.post("/login", (req, res) => {
    var username = req.body.username;
    var pass = req.body.pass;
    const qryStr = "select * from user where username like ? and password like ?";
    connection.query(qryStr, [username, pass], (err, results, fields) => {
        if(err) {
            console.log("Login failed: " + err);
            res.sendStatus(500);
            res.send("Login failed");
            return
        }

        res.status(200, "OK");
        res.send("Login successful");
        res.end();
    });
});

//create user
router.post("/add", (req, res) => {
    var username = req.body.username;
    var email = req.body.email;
    var pass = req.body.pass;
    const qryStr = "insert into user (username, email, password) values(?, ?, ?)";
    connection.query(qryStr, [username, email, pass], (err, results, fields) => {
        if(err) {
            console.log("Failed to insert new user: " + err);
            res.sendStatus(500);
            res.send("Registration failed");
            return
        }

        res.status(200, "OK");
        res.send("Registration successful");
        res.end();
    });
});

//update user
router.post("/update", (req, res) => {
    var userId = req.body.userId;
    var username = req.body.username;
    var email = req.body.email;
    var pass = req.body.pass;
    const qryStr = "update user set username = ?, email = ?, password = ? where id = ?";
    connection.query(qryStr, [username, email, pass, userId], (err, results, fields) => {
        if(err) {
            console.log("Failed to update data: " + err);
            res.sendStatus(500);
            res.send("Data failed to update");
            return
        }

        res.status(200, "OK");
        res.send("Data updated");
        res.end();
    });
});

module.exports = router;