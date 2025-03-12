var apimock = (function () {
    var mockdata = [];

    mockdata["juan"] = [
        { name: 'house', points: 233 },
        { name: 'office', points: 150 },
        { name: 'garden', points: 120 }
    ];

    mockdata["maria"] = [
        { name: 'park', points: 300 },
        { name: 'school', points: 200 },
        { name: 'hospital', points: 250 }
    ];

    mockdata["carlos"] = [
        { name: 'mall', points: 400 },
        { name: 'stadium', points: 350 },
        { name: 'library', points: 180 }
    ];

    mockdata["Manuel"] = [
        { name: 'buldier', points: 400 },
        { name: 'torreColpatria', points: 350 },
        { name: 'school', points: 180 }
    ];

    mockdata["Paula"] = [
        { name: 'SantaFe', points: 400 },
        { name: 'Unicentro', points: 350 },
        { name: 'Andino', points: 180 }
    ];

    return {
        getBlueprintsByAuthor: function (author, callback) {
            callback(mockdata[author]);
        },
        getBlueprintsByNameAndAuthor: function (author, blueprintName, callback) {
            const blueprints = mockdata[author];
            if (blueprints) {
                const blueprint = blueprints.find(bp => bp.name === blueprintName);
                callback(blueprint);
            } else {
                callback(null);
            }
        }
    };
})();