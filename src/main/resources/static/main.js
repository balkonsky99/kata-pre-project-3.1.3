    //Default variable
    const url = '/api/users/';
    let result = '';
    const userList = document.querySelector('.user-list');
    const modalEdit = new bootstrap.Modal(document.getElementById('modalEdit'));
    const formEdit = document.querySelector('.form-edit');
    const modalDelete = new bootstrap.Modal(document.getElementById('modalDelete'));
    const formDelete = document.querySelector('.form-delete');
    const newUser = document.querySelector('#newUser');
    const tabNewUser = document.querySelector('#tabNewUser');
    const formNew = document.querySelector('#formNewUser');
    const selectRoles = document.querySelector('.selectRoles');
    const newEnabled = document.getElementById('newEnabled');
    const newUsername = document.getElementById('newUsername');
    const newPassword = document.getElementById('newPassword');
    const newFirstName = document.getElementById('newFirstName');
    const newLastName = document.getElementById('newLastName');
    const newAge = document.getElementById('newAge');
    const newListRoleNames = document.getElementById('newListRoleNames');
    let idForm = 0;

    // btnEdit.addEventListener('click', ()=>{
    //     modalEdit.show();
    // })

    newUser.addEventListener('click', ()=>{
        newEnabled.checked = false;
        newUsername.value = '';
        newPassword.value = '';
        newFirstName.value = '';
        newLastName.value = '';
        newAge.value = '';
        newListRoleNames.value = '';
        selectRoles.children[0].className = 'form-group text-center w-50 mx-auto'
    })

    const getAllUsers = users => {
        users.sort((a, b) => Number(a.id) - Number(b.id)).forEach(user => {
            let userRoles = '';
            user.roles.forEach(role => {userRoles += role.name.substring(5) + ' '});
            result += `
            <tr id="user_${user.id}">
                <th scope="row">${user.id}</th>
                <td>${user.username}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>
                    <span>${userRoles}</span>
                </td>
                <td><button type="button" class="btn btn-info btnEdit" data-toggle="modal">Изменить</button></td>
                <td><button type="button" class="btn btn-danger btnDelete" data-toggle="modal">Удалить</button></td>
            </tr>
            `;
        })
        userList.innerHTML = result;
    }

    const getUserInfo = e => {
        const row = e.target.parentNode.parentNode;
        idForm = Number(row.children[0].innerHTML);
        const modal = (e.target.className === 'btn btn-info btnEdit') ? modalEdit : modalDelete;
        fetch(url)
            .then(res => res.json())
            .then( users => {
                users.forEach(user => {
                    if(user.id === idForm) {
                        modal._element.querySelector('.form-body').innerHTML = `
                            <div class="form-check w-75 mx-auto mb-3">
                                <input class="form-check-input" type="checkbox" id="enabled" value="${user.enabled}">
                                <label class="form-check-label" for="enabled">Разрешена авторизация</label>
                            </div>
                            <div class="form-group text-center w-75 mx-auto">
                                <label for="username" class="col-form-label font-weight-bolder">Логин</label>
                                <input type="text" class="form-control" id="username" value="${user.username}">
                            </div>
                            <div class="form-group text-center w-75 mx-auto">
                                <label for="password" class="col-form-label font-weight-bolder">Пароль</label>
                                <input type="password" class="form-control" id="password" value="${user.password}">
                            </div>
                            <div class="form-group text-center w-75 mx-auto">
                                <label for="firstName" class="col-form-label font-weight-bolder">Имя</label>
                                <input type="text" class="form-control" id="firstName" value="${user.firstName}">
                            </div>
                            <div class="form-group text-center w-75 mx-auto">
                                <label for="lastName" class="col-form-label font-weight-bolder">Фамилия</label>
                                <input type="text" class="form-control" id="lastName" value="${user.lastName}">
                            </div>
                            <div class="form-group text-center w-75 mx-auto">
                                <label for="age" class="col-form-label font-weight-bolder">Возраст</label>
                                <input type="number" class="form-control" id="age" value="${user.age}">
                            </div>
                        ` + selectRoles.innerHTML;

                        modal._element.querySelector('#enabled').checked = user.enabled;
                        modal._element.querySelector('.list-role').children[0].htmlFor = 'listRoleNames';
                        modal._element.querySelector('.list-role').children[1].id = 'listRoleNames';
                        user.roles.forEach(role => {
                            for (let option of modal._element.querySelector('select').children) {
                                if (option.value === role.name) {
                                    option.selected = true;
                                }
                            }
                        });
                    }
                })
            })
            .catch(error => console.log(error))
        modal.show();
    }

    const getRoles = select => {
        let roles = []
        select.forEach(option => {
            roles.push({id:option.attributes.role_id.value, name:option.value})
        })
        return roles;
    }

    const openModal = (element, event, selector) => {
        element.addEventListener(event, e => {
            if(e.target.closest(selector)) {
                selectRoles.children[0].className = 'form-group text-center list-role w-75 mx-auto'
                getUserInfo(e);
            }
        })
    }


    fetch(url)
        .then(res => res.json())
        .then(data => getAllUsers(data))
        .catch(error => console.log(error))


    //Open edit
    openModal(document, 'click', '.btnEdit')

    //Open delete
    openModal(document, 'click', '.btnDelete')

    //Add new user
    formNew.addEventListener('submit', e => {
        e.preventDefault();

        let roles = getRoles(Array.from(document.getElementById('newListRoleNames').selectedOptions));
        fetch(url, {
            method:'POST',
            headers:{
                'Content-Type':'application/json'
            },
            body:JSON.stringify({
                enabled:newEnabled.checked,
                username:newUsername.value,
                password:newPassword.value,
                firstName:newFirstName.value,
                lastName:newLastName.value,
                age:newAge.value,
                roles: roles
            })
        }).then(res => {
            res.json().then(user => {
                let newUser = [];
                newUser.push(user);
                getAllUsers(newUser);
            })
        }).catch(error => console.log(error))

        document.getElementById('userTableLink').click();
    })

    //Edit user
    formEdit.addEventListener('submit', e => {
        e.preventDefault();

        let enabled = document.getElementById('enabled');
        let username = document.getElementById('username');
        let password = document.getElementById('password');
        let firstName = document.getElementById('firstName');
        let lastName = document.getElementById('lastName');
        let age = document.getElementById('age');
        let listRoleNames = document.getElementById('listRoleNames');
        let roles = getRoles(Array.from(document.getElementById('listRoleNames').selectedOptions));
        fetch(url+idForm, {
            method:'PATCH',
            headers:{
                'Content-Type':'application/json'
            },
            body:JSON.stringify({
                enabled:enabled.checked,
                username:username.value,
                password:password.value,
                firstName:firstName.value,
                lastName:lastName.value,
                age:age.value,
                roles: roles
            })
        }).then(res => {
            res.json().then(user => {
                let roleNames = '';
                user.roles.forEach(role => {roleNames += role.name.substring(5) + ' '})
                const row = document.getElementById(`user_${idForm}`);
                row.children[1].innerHTML = user.username;
                row.children[2].innerHTML = user.firstName;
                row.children[3].innerHTML = user.lastName;
                row.children[4].innerHTML = user.age;
                row.children[5].innerHTML = roleNames;
            })
        }).catch(error => console.log(error))

        modalEdit.hide();
    })

    //Delete user
    formDelete.addEventListener('submit', e => {
        e.preventDefault();

        fetch(url+idForm, {method:'DELETE'})
        .then(res => {
            document.getElementById(`user_${idForm}`).remove();
        }).catch(error => console.log(error))

        modalDelete.hide();
    })
