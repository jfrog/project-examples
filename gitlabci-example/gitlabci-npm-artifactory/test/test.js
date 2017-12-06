/**
 * Created by jainishs on 2/2/17.
 */

var assert = require('assert');
var colors = require('colors');

function aE(s, color, code) {
    assert.equal(s[color], a(s, code));
    assert.equal(colors[color](s), a(s, code));
    assert.equal(s[color], colors[color](s));
    assert.equal(s[color].strip, s);
    assert.equal(s[color].strip, colors.strip(s));
}

aE('gitlab-npm-artifactory', 'red', 31);

function a(s, code) {
    return '\x1B[' + code.toString() + 'm' + s + '\x1B[39m';
}
