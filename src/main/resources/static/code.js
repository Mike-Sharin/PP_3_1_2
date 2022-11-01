alert("Проверка что работает скрипт!");

const url = "http://localhost:9090/api/"
const containerAllUsers = document.getElementById('allUsers')
const containerAuthorizedUser = document.getElementById('authorizedUser')
const containerEditModalRoles = document.getElementById('editUserRoles')
//modalArticulo
const modalEdit = new bootstrap.Modal(document.getElementById('modalEditUser'))


fetch(url+"users/")
    .then(response => response.json())
    .then(data => listUsers(data))
    .catch(error => console.log(error))

const listUsers = (users) => {
    let result = ''

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
    containerAllUsers.innerHTML = result
}

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if(e.target.closest(selector)){
            handler(e)
        }
    })
}

// on(document, 'click', '.btnDelete', e => {
//     const fila = e.target.parentNode.parentNode
//     const id =  fila.firstElementChild.innerHTML
//
//     // fetch(url+id, {
//     //     method : 'DELETE'
//     // })
//     //     .then( res => res.json())
//     //     .then( () => location.reload())
//
//     console.log(url+"users/"+id)
// })

on(document, 'click', '.btnEdit', e => {
    const fila = e.target.parentNode.parentNode
    editUserId.value = fila.children[0].innerHTML
    editUserFirstName.value = fila.children[1].innerHTML
    editUserLastName.value = fila.children[2].innerHTML
    editUserAge.value = fila.children[3].innerHTML
    editUserEmail.value = fila.children[4].innerHTML

    fetch(url+"roles/")
        .then(response => response.json())
        .then(data => listRoles(data))
        .catch(error => console.log(error))

    $('#modalEditUser').modal('show')
})

on(document, 'click', '.btnDelete', e => {
    const fila = e.target.parentNode.parentNode
    deleteUserId.value = fila.children[0].innerHTML
    deleteUserFirstName.value = fila.children[1].innerHTML
    deleteUserLastName.value = fila.children[2].innerHTML
    deleteUserAge.value = fila.children[3].innerHTML
    deleteUserEmail.value = fila.children[4].innerHTML

    // fetch(url+"roles/")
    //     .then(response => response.json())
    //     .then(data => listRoles(data))
    //     .catch(error => console.log(error))

    $('#modalDeleteUser').modal('show')
})

formEditUser.addEventListener('submit', (e) => {
    e.preventDefault()

    fetch(url+"users", {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify({
            id: editUserId.value,
            firstName: editUserFirstName.value,
            lastName: editUserLastName.value,
            age: editUserAge.value,
            password: editUserPassword.value,
            email: editUserEmail.value,
            roles: []
            })
        })
        .then( response => response.json())
            .then( data => {
                const listUser = []
                listUser.push(data)
                mostrar(listUser)
    })
    console.log("Edit")

    $('#modalEditUser').modal('hide')
})


const listRoles = (roles) => {
    let result1 = '';
    roles.forEach(role => {
        result1 += `<option name="roles" th:text="${role.name.replace('ROLE_', '')}"
                                                    th:value="${role.id}">${role.name.replace('ROLE_', '')}</option>`
    })
    containerEditModalRoles.innerHTML = result1
}

// const authorizedUser = (users) => {
//     let result = ''
//
//     users.forEach(user => {
//         if(user.email == ) {
//             let result1 = '';
//             (user.roles).forEach(role => {
//                 result1 += `${role.name.replace('ROLE_', '')} `
//             })
//
//             result += `
//                     <tr>
//                         <td>${user.id}</td>
//                         <td>${user.firstName}</td>
//                         <td>${user.lastName}</td>
//                         <td>${user.age}</td>
//                         <td>${user.email}</td>
//                         <td>` + result1 + `</td>
//                     </tr>`
//         }
//     })
//
//     containerAuthorizedUser.innerHTML = result
// }
