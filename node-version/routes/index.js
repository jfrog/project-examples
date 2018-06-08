var express = require('express');
var package = require('../package.json');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { version: package.version });
});

module.exports = router;
