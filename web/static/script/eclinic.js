
function lengthCalculate() {
    return 1 + Math.floor(($("#main-content").height() - 158.78 - 46.44 - 37.3) / 45.44);
}

function isEmpty(str) {
    return (!str || 0 === str.length);
}