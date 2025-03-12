//Paula Natalia Paez Vega
//Manuel Felipe Barrera
var APIClient = (function() {
    const baseUrl = "http://localhost:8080/api/blueprints";

    function getBlueprintsByAuthor(author, callback) {
        $.get(baseUrl + "/blueprints?author=" + author, function(data) {
            callback(data);
        }).fail(function(error) {
            callback(null, error); 
        });
    }

    function getBlueprintsByNameAndAuthor(author, blueprintName, callback) {
        $.get(baseUrl + "/blueprints?author=" + author + "&name=" + blueprintName, function(data) {
            callback(data);
        }).fail(function(error) {
            callback(null, error);
        });
    }

    return {
        getBlueprintsByAuthor: getBlueprintsByAuthor,
        getBlueprintsByNameAndAuthor: getBlueprintsByNameAndAuthor
    };
})();