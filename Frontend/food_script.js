// Food Input HTML Boxes
const food_name_input = document.getElementById('food_name');
const food_category_input = document.getElementById('food_category');
const food_sub_category_input = document.getElementById('food_sub_category');
const food_amount_input = document.getElementById('food_amount');
const food_unit_input = document.getElementById('food_unit');

const submit_food = document.getElementById('submit_food');

// Table HTML Objects
const table_output = document.getElementById('food_data_output');
const refreshButton = document.getElementById('refresh');



submit_food.onclick = () => {

    url = 'http://localhost:8080/food';

    var data = {
        name : food_name_input.value,
        category : food_category_input.value,
        sub_category : food_sub_category_input.value,
        amount : food_amount_input.value,
        unit : food_unit_input.value
    };

    fetch(url, {method : 'POST', body: JSON.stringify(data),
    headers: data ? { 'Content-Type': 'application/json' } : {}});

    refreshButton.onclick();

};

refreshButton.onclick = () => {
    url = 'http://localhost:8080/food';

    fetch(url, {
        method: 'GET'
    }).then(response => {
        return response.json();
    }).then(function(data) {
        let placehholder = document.getElementById('food_data_output');

        let out = "";

        for (let obj of data) {
            tag = obj.id + '_id';

            out += `
            <tr id = >
                <td> ${obj.id} </td>
                <td> ${obj.name} </td>
                <td> ${obj.category} </td>
                <td> ${obj.sub_category} </td>
                <td> ${obj.amount} </td>
                <td> ${obj.unit} </td>
                <td> <button>
            </tr>
            `;

        }
        placehholder.innerHTML = out;
    });

};

