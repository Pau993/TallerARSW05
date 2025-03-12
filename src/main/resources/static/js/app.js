var BlueprintsApp = (function () {
    var blueprints = [];
    var selectedAuthor = '';

    function getBlueprints() {
        apimock.getBlueprintsByAuthor(selectedAuthor, function(data) {
            blueprints = data;
            renderBlueprints();
        });
    }

    function renderBlueprints() {
        const tableBody = $('#blueprints-table');
        tableBody.empty();
        let totalPoints = 0;

        const transformedBlueprints = blueprints.map(bp => ({
            name: bp.name,
            points: bp.points
        }));

        transformedBlueprints.map(bp => {
            const row = $('<tr></tr>');
            row.append(`<td>${bp.name}</td>`);
            row.append(`<td>${bp.points}</td>`);

            const button = $('<button class="btn btn-secondary">Open</button>');
            button.click(function() {
                BlueprintsApp.openBlueprint(bp.name);
            });
            const cell = $('<td></td>').append(button);
            row.append(cell);

            tableBody.append(row);
        });

        totalPoints = transformedBlueprints.reduce((sum, bp) => sum + bp.points, 0);
        $('#total-points').text(totalPoints);
    }

    function drawBlueprint(points) {
        const canvas = document.getElementById('blueprint-canvas');
        const ctx = canvas.getContext('2d');
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        if (!points) return;

        ctx.beginPath();
        let x = 0;
        let y = canvas.height / 2;
        ctx.moveTo(x, y);

        for (let i = 0; i < points; i++) {
            x += 20;
            y = canvas.height / 2 + Math.sin(i * 0.2) * 50;
            ctx.lineTo(x, y);
        }
        ctx.stroke();
    }

    function openBlueprint(name) {
        const author = $('#author').val();

        if (!$('#current-blueprint-name').length) {
            $('body').append('<div id="current-blueprint-name"></div>');
        }
        $('#current-blueprint-name').text("Cargando...");

        apimock.getBlueprintsByNameAndAuthor(author, name, function(blueprint) {
            if (blueprint) {
                $('#current-blueprint-name').text(name);
                drawBlueprint(blueprint.points);
            } else {
                $('#current-blueprint-name').text("Plano no encontrado");
            }
        });
    }

    function setAuthor(author) {
        selectedAuthor = author;
        $('#author-name').text(`${author}'s blueprints:`);
    }

    return {
        getBlueprints: getBlueprints,
        openBlueprint: openBlueprint,
        setAuthor: setAuthor,
        updateBlueprints: function (author) {
            setAuthor(author);
            getBlueprints();
        }
    };
})();


$('#get-blueprints-btn').click(function () {
    const author = $('#author').val();
    BlueprintsApp.updateBlueprints(author);
});