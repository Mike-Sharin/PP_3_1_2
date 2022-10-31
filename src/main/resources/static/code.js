alert("fgdfg");
const url = "http://localhost:9090/api/users/"
const conteiner = document.getElementById('allUsers')
let result = ''


fetch(url)
    .then(response => response.json())
    .then(data => listUsers(data))
    .catch(error => console.log(error))

const listUsers = (users) => {
    users.forEach(user => {
        let result1 = '';
        (user.roles).forEach(role => {
            result1 += `${role.name.replace('ROLE_', '')} `
        })

        result += `
                <tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>                    
                    <td>` + result1 + `</td>   
                    <td>
                        <!-- Кнопка-триггер модального окна -->
                        <button type="button"
                                class="btn btn-info btn-sm btnEdit"
                                data-toggle="modal"
                                th:data-target="${'#modalEditUser-' + user.id}">
                            Edit
                        </button>
                    </td>  
                    <td>
                         <!-- Кнопка-триггер модального окна -->
                        <button type="button"
                                class="btn btn-danger btn-sm btnDelete"
                                role="button"
                                data-toggle="modal"
                                th:data-target="${'#modalDeleteUser-' + user.id}">
                            Delete
                        </button>
                    </td>               
                </tr>`
    })
    conteiner.innerHTML = result
}

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if(e.target.closest(selector)){
            handler(e)
        }
    })
}

on(document, 'click', '.btnDelete', e => {
    console.log('Delete')
})
