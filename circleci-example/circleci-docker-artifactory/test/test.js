/**
 * Created by jainishs on 5/25/17.
 */

var assert = require('assert');
var package = require('../package.json');
var semverRegex = require('semver-regex');
assert.equal(semverRegex().test(package.version), true);