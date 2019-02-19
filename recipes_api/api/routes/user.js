const express = require('express');
const router = express.Router();
const mysql = require('mysql');
const connection = mysql.createConnection({
   host: 'remotemysql.com',
   user: 'h0QlUFTV95',
   password: 'ezzVcs5uu6',
   database: 'h0QlUFTV95'
   /*
   host: '194.160.229.181',
   user: 'sladmin',
   password: 'slpass',
   database: 'sl_recipes'
   
   host: 'localhost',
   user: 'root',
   password: '',
   database: 'recipes2'
   */
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
router.get("/login/:username/:pass", (req, res) => {
    var username = req.params.username;
    var pass = req.params.pass;
    const qryStr = "select * from user where username like ? and password like ?";
    connection.query(qryStr, [username, pass], (err, rows, fields) => {
        if(err) {
            console.log("Login failed: " + err);
            res.sendStatus(500);
            res.send("Login failed");
            return
        }

        var idUser = rows.map((x) => x.id);
        if(idUser=="")
            var message = {
                message:"Invalid username or password",
                userData:{
                    idUser: JSON.stringify(rows.map((x) => x.id)).replace('[', '').replace(']',''),
                    username: JSON.stringify(rows.map((x) => x.username)).replace('[', '').replace(']','').replace('\"','').replace('\"',''),
                    pass: JSON.stringify(rows.map((x) => x.password)).replace('[', '').replace(']','').replace('\"','').replace('\"',''),
                    email: JSON.stringify(rows.map((x) => x.email)).replace('[', '').replace(']','').replace('\"','').replace('\"','')
                }
            };
            //var message = "Invalid username or password";
        else
            var message = {
                message:"Login successful",
                userData:[{
                    idUser: JSON.stringify(rows.map((x) => x.id)).replace('[', '').replace(']',''),
                    username: JSON.stringify(rows.map((x) => x.username)).replace('[', '').replace(']','').replace('\"','').replace('\"',''),
                    pass: JSON.stringify(rows.map((x) => x.password)).replace('[', '').replace(']','').replace('\"','').replace('\"',''),
                    email: JSON.stringify(rows.map((x) => x.email)).replace('[', '').replace(']','').replace('\"','').replace('\"','')
                }]
            };
            
        res.writeHead(200, {'Content-Type': 'application/json'});
        res.end(JSON.stringify(message));
    });
});

//create user
router.post("/add", (req, res) => {
    var username = req.body.username;
    var email = req.body.email;
    var pass = req.body.pass;
    
    const qryStrCheckUsername = "select * from user where username like ?"
    const qryStrCheckEmail = "select * from user where email like ?"
    const qryStr = "insert into user (username, email, password) values(?, ?, ?)";

    connection.query(qryStrCheckUsername, username, (err, rows, fields) => {
        if(err) {
            console.log("Registration failed username: " + err);
            connection.end();
            res.sendStatus(500);
            res.send("Registration failed");
            res.end();
            return
        }

        if(!rows.length){
            connection.query(qryStrCheckEmail, email, (err, rows, fields) => {
                if(err) {
                    console.log("Registration failed email: " + err);
                    connection.end();
                    res.sendStatus(500);
                    res.send("Registration failed");
                    res.end();
                    return
                }

                if(!rows.length) {
                    connection.query(qryStr, [username, email, pass], (err, results, fields) => {
                        if(err) {
                            console.log("Failed to insert new user: " + err);
                            connection.end();
                            res.sendStatus(500);
                            res.send("Registration failed");
                            return
                        }
                
                        var userData = {
                            username: "ok",
                            pass: "ok",
                            email: "ok"
                        };
                        
                        connection.end();
                        res.writeHead(200, {'Content-Type': 'application/json'});
                        res.end(JSON.stringify(userData));
                        res.end();
                    });
                } else {
                    var userData = {
                        username: "null",
                        pass: "null",
                        email: email
                    };
                    
                    res.writeHead(200, {'Content-Type': 'application/json'});
                    res.end(JSON.stringify(userData));
                    
                    
                }

            }); // check email
        } else { // if checkusername is used
            var userData = {
                username: username,
                pass: "null",
                email: "null"
            };
           
            res.writeHead(200, {'Content-Type': 'application/json'});
            res.end(JSON.stringify(userData));           
        } 
        
    }); // check username
    
    
}); // create user

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