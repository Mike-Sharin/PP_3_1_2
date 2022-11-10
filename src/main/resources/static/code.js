const urlUsers = "http://localhost:9090/api/users/"
const urlRoles = "http://localhost:9090/api/roles/"

const containerAllUsers = document.getElementById('allUsers')
const containerAuthorizedUser = document.getElementById('authorizedUser')

fetch(urlUsers)
    .then(response => response.json())
    .then(data => listUsers(data))
    .catch(error => console.log(error))

fetch(urlUsers)
    .then(response => response.json())
    .then(() => authorizedUser())
    .catch(error => console.log(error))

fetch(urlRoles)
    .then(response => response.json())
    .then(data => listRoles(data, "", "new"))
    .catch(error => console.log(error))

const listUsers = (users) => {
    let result = ''

    users.forEach(user => {
        let result1 = '';

        user.roles.forEach(role => {
            result1 += `${role.name.replace('ROLE_', '')} `
        })

        result += `<tr>
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
                                data-toggle="modal">
                            Edit
                        </button>
                    </td>  
                    <td>
                         <!-- Кнопка-триггер модального окна -->
                        <button type="button"
                                class="btn btn-danger btn-sm btnDelete"
                                role="button"
                                data-toggle="modal">
                            Delete
                        </button>
                    </td>               
                </tr>`
    })

    if (!(containerAllUsers === null)) {
        containerAllUsers.innerHTML = result
    }
}

const listRoles = (roles, list, operation) => {
    var mas = list.split(' ');

    let result = '';
    roles.forEach(role => {
        let selected = '';
        for (var i = 0; i < mas.length; i++) {
            if (`${role.name.replace('ROLE_', '')}` === mas[i]) {
                selected = 'selected'
            }
        }

        result += `<option name="roles" 
                            text="${role.name.replace('ROLE_', '')}"
                            value="${role.id}"` +
            selected + `>                            
                        ${role.name.replace('ROLE_', '')}
                    </option>`
    })

    var container;

    switch (operation) {
        case "new":
            container = document.getElementById('newUserRoles')
            break;
        case "edit":
            container = document.getElementById('editUserRoles')
            break;
        case "delete":
            container = document.getElementById('deleteUserRoles')
            break;
    }

    if (!(container === null)) {
        container.innerHTML = result
    }
}

const authorizedUser = () => {
    let result = ''
    var email = document.getElementById('authenticationUser').innerHTML;

    fetch(urlUsers + email, {method: 'GET'})
        .then(response => response.json())
        .then((autUser) => {
            autUser.roles.forEach(role => {
                result += `${role.name.replace('ROLE_', '')} `
            })

            result = `<tr>
                                             <td>${autUser.id}</td>
                                             <td>${autUser.firstName}</td>
                                             <td>${autUser.lastName}</td>
                                             <td>${autUser.age}</td>
                                             <td>${autUser.email}</td>
                                             <td>` + result + `</td>
                                        </tr>`

            containerAuthorizedUser.innerHTML = result
        })
        .catch(error => console.log(error))
}

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}

on(document, 'click', '.btnEdit', e => {
    const fila = e.target.parentNode.parentNode
    editUserId.value = fila.children[0].innerHTML
    editUserFirstName.value = fila.children[1].innerHTML
    editUserLastName.value = fila.children[2].innerHTML
    editUserAge.value = fila.children[3].innerHTML
    editUserEmail.value = fila.children[4].innerHTML

    fetch(urlRoles)
        .then(response => response.json())
        .then(data => listRoles(data, fila.children[5].innerHTML, "edit"))
        .catch(error => console.log(error))

    $('#modalEditUser').modal('show')
})

formEditUser.addEventListener('submit', (e) => {
    e.preventDefault()

    let selectedRoles = [];

    for (let i = 0; i < editUserRoles.options.length; i++) {
        if (editUserRoles.options[i].selected) {
            selectedRoles[i] = {
                id: editUserRoles.options[i].value,
                name: editUserRoles.options[i].text
            }
        }
    }

    fetch(urlUsers, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            id: editUserId.value,
            firstName: editUserFirstName.value,
            lastName: editUserLastName.value,
            age: editUserAge.value,
            password: editUserPassword.value,
            email: editUserEmail.value,
            roles: selectedRoles
        })
    })
        .then(() => location.reload())

    $('#modalEditUser').modal('hide')
})

on(document, 'click', '.btnDelete', e => {
    const fila = e.target.parentNode.parentNode
    deleteUserId.value = fila.children[0].innerHTML
    deleteUserFirstName.value = fila.children[1].innerHTML
    deleteUserLastName.value = fila.children[2].innerHTML
    deleteUserAge.value = fila.children[3].innerHTML
    deleteUserEmail.value = fila.children[4].innerHTML

    fetch(urlRoles)
        .then(response => response.json())
        .then(data => listRoles(data, fila.children[5].innerHTML, "delete"))
        .catch(error => console.log(error))

    $('#modalDeleteUser').modal('show')
})

formDeleteUser.addEventListener('submit', (e) => {
    e.preventDefault()
    const id = deleteUserId.value

    fetch(urlUsers + id, {
        method: 'DELETE'
    })
        .then(() => location.reload())

    $('#modalDeleteUser').modal('hide')
});

formNewUser.addEventListener('submit', (e) => {
    e.preventDefault()

    let selectedRoles = [];
    let j = 0;

    for (var i = 0; i < newUserRoles.options.length; i++) {
        if (newUserRoles.options[i].selected) {
            selectedRoles[j++] = {
                id: newUserRoles.options[i].value,
                name: newUserRoles.options[i].text
            }
        }
    }

    fetch(urlUsers, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            firstName: newUserFirstName.value,
            lastName: newUserLastName.value,
            age: newUserAge.value,
            password: newUserPassword.value,
            email: newUserEmail.value,
            roles: selectedRoles
        })
    })
        .then(() => location.reload())
});
