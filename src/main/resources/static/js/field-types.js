function newString(name, field, isHorizontal) {
    return newField(name, field, isHorizontal, "text");
}

function newDateTime(name, field, isHorizontal) {
    return newField(name, field, isHorizontal, "datetime");
}

function newNumber(name, field, isHorizontal) {
    return newField(name, field, isHorizontal, "number");
}

function newField(name, field, isHorizontal, type) {
    if(isHorizontal) {
        return `<div class="field-label">
            <label class="label has-text-left line-40p">${name}</label>
        </div>
        <div class="field-body">
            <div class="control is-expanded">
                <input class="input" type="${type}" id="${field}">
            </div>
        </div>`;
    } else {
        return `<div class="field">
            <label class="label>${name}</label>
            <div class="control">
                <input class="input" type="${type}" id="${field}>
            </div>
        </div>`;
    }
}

// "timestamp": "2022-06-21T09:30:00.875541-03:00",
// 	"idCamera": "entrada",
// 	"possiveisPlacas": [
// 		{
// 			"placa": "CON1234",
// 			"confiancaPlaca": 0.996,
// 			"confiancaCaractere": [
// 				1,
// 				1,
// 				1,
// 				1,
// 				1,
// 				1,
// 				1
// 			]
// 		}
// 	],
// 	"imagem": {
// 		"type": "image/jpg",
// 		"data": "/9j/4AAQSkZJRgABAQAAAQABAAD/2w...kj86AP//Z",
// 		"posicaoPlaca": {
// 			"h": 41,
// 			"w": 78,
// 			"x": 174,
// 			"y": 225
// 		}

// 	}