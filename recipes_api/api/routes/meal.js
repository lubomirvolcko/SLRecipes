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


//get all recipes
router.get('/', (req, res, next) => {
    const qryStr = "select id, meal, category, image, review from meal"
    connection.query(qryStr, (err, rows, fields) => {
        if(err) {
            console.log("Failed to query for users: " + err);
            res.sendStatus(500);
            res.send("Failed to get data");
            return
        }
       var recipes = { recipes: rows}
       res.status(200).json(recipes);
    }); 
});

//get all recipes
router.get('/toprated', (req, res, next) => {
    const qryStr = "select id, meal, category, image, review from meal where id <= 50"
    connection.query(qryStr, (err, rows, fields) => {
        if(err) {
            console.log("Failed to query for users: " + err);
            res.sendStatus(500);
            res.send("Failed to get data");
            return
        }
       var recipes = { recipes: rows}
       res.status(200).json(recipes);
    }); 
});

//get all recipes
router.get('/lastviewed', (req, res, next) => {
    const qryStr = "select id, meal, category, image, review from meal where id <= 3"
    connection.query(qryStr, (err, rows, fields) => {
        if(err) {
            console.log("Failed to query for users: " + err);
            res.sendStatus(500);
            res.send("Failed to get data");
            return
        }
       var recipes = { recipes: rows}
       res.status(200).json(recipes);
    }); 
});

//get all recipes
router.get('/new', (req, res, next) => {
    const qryStr = "select * from meal order by id desc limit 50"
    connection.query(qryStr, (err, rows, fields) => {
        if(err) {
            console.log("Failed to query for users: " + err);
            res.sendStatus(500);
            res.send("Failed to get data");
            return
        }
       var recipes = { recipes: rows}
       res.status(200).json(recipes);
    }); 
});

//get one recipe by id
router.get("/id/:id", (req, res) => {
    const mealId = req.params.id;
    const qryStr = "select * from meal where id = ?"
    connection.query(qryStr, [mealId], (err, rows, fields) => {
        var recipes = { recipes: rows}
       res.status(200).json(recipes);
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
        var recipes = { recipes: rows}
       res.status(200).json(recipes);
    });
});

//get recipes by meal
router.get("/found/:meal", (req, res) => {
    const meal = req.params.meal;
    const qryStr = "select * from meal where meal like '%"+ meal +"%'"
    connection.query(qryStr, (err, rows, fields) => {
        
       var recipes = { recipes: rows}
       res.status(200).json(recipes);
    });
});

//get recipes by ingredient
router.get("/ing/:ingredients", (req, res) => {
    const ing = req.params.ingredients;
    const qryStr = "select * from meal where ingredients like '%"+ ing +"%'"
    connection.query(qryStr, (err, rows, fields) => {
        var recipes = { recipes: rows}
       res.status(200).json(recipes);
    });
});

/*
var message = {
    message:"Invalid username or password",
    userData:{
        idUser: JSON.stringify(rows.map((x) => x.id)).replace('[', '').replace(']',''),
        username: JSON.stringify(rows.map((x) => x.username)).replace('[', '').replace(']','').replace('\"','').replace('\"',''),
        pass: JSON.stringify(rows.map((x) => x.password)).replace('[', '').replace(']','').replace('\"','').replace('\"',''),
        email: JSON.stringify(rows.map((x) => x.email)).replace('[', '').replace(']','').replace('\"','').replace('\"','')
    }
}
*/

//get all categories
router.get("/cat", (req, res) => {
    const qryStr = "select category from meal group by category order by category asc"
    connection.query(qryStr, (err, rows, fields) => {
        var cat = { categories: rows}
       res.status(200).json(cat);
    });
});

module.exports = router;