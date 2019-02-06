const express = require('express');
const router = express.Router();
const mysql = require('mysql');
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'recipes2'
});


//get all recipes
router.get('/', (req, res, next) => {
    const qryStr = "select * from meal"
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

//get one recipe by id
router.get("/id/:id", (req, res) => {
    const mealId = req.params.id;
    const qryStr = "select * from meal where id = ?"
    connection.query(qryStr, [mealId], (err, rows, fields) => {
        res.status(200).json(rows);
    });
});

//create recipe
router.post("/add", (req, res) => {
    var meal = req.body.meal;
    var category = req.body.category;
    var area = req.body.area;
    var instructions = req.body.instructions;
    var image = req.body.image;
    var tags = req.body.tags;
    var ingredients = req.body.ingredients;
    var measures = req.body.measures;
    var video = req.body.video;
    var source = req.body.source;

    const qryStr = "insert into meal (meal, category, area, instructions, image, tags, ingredients, measures, video, source) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    connection.query(qryStr, [meal, category, area, instructions, image, tags, ingredients, measures, video, source], (err, results, fields) => {
        if(err) {
            console.log("Failed to insert new recipe: " + err);
            res.sendStatus(500);
            res.send("Failed to add new recipe");
            return
        }

        res.status(200, "OK");
        res.send("Recipe was added");
        res.end();
    });
});

//update recipe
router.post("/update", (req, res) => {
    var mealId = req.body.mealId;
    var meal = req.body.meal;
    var category = req.body.category;
    var area = req.body.area;
    var instructions = req.body.instructions;
    var image = req.body.image;
    var tags = req.body.tags;
    var ingredients = req.body.ingredients;
    var measures = req.body.measures;
    var video = req.body.video;
    var source = req.body.source;

    const qryStr = "update meal set meal = ?, category = ?, area = ?, instructions = ?, image = ?, tags = ?, ingredients = ?, measures = ?, video = ?, source = ? where id = ?";
    connection.query(qryStr, [meal, category, area, instructions, image, tags, ingredients, measures, video, source, mealId], (err, results, fields) => {
        if(err) {
            console.log("Failed to update recipe: " + err);
            res.sendStatus(500);
            res.send("Update failed");
            return
        }

        res.status(200, "OK");
        res.send("Update successful");
        res.end();
    });
});

//get recipes by category
router.get("/category/:category", (req, res) => {
    const category = req.params.category;
    const qryStr = "select * from meal where category = ?"
    connection.query(qryStr, [category], (err, rows, fields) => {
        res.status(200).json(rows);
    });
});

//get recipes by meal
router.get("/meal/:meal", (req, res) => {
    const meal = req.params.meal;
    const qryStr = "select * from meal where meal like '%"+ meal +"%'"
    connection.query(qryStr, (err, rows, fields) => {
        res.status(200).json(rows);
    });
});

//get recipes by ingredient
router.get("/ing/:ingredients", (req, res) => {
    const ing = req.params.ingredients;
    const qryStr = "select * from meal where ingredients like '%"+ ing +"%'"
    connection.query(qryStr, (err, rows, fields) => {
        res.status(200).json(rows);
    });
});

module.exports = router;