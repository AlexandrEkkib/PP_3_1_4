const data = document.getElementById("one_user");
const url = 'http://localhost:8080/api/current';
const panel = document.getElementById("user_panel");


function userAuthInfo() {
    fetch(url)
        .then((res) => res.json())
        .then((u) => {
            let temp = '';
            temp += `<tr>
            <td>${u.id}</td>
            <td>${u.username}</td>
            <td>${u.surname}</td>
            <td>${u.age}</td>
            <td>${u.email}</td>
            <td id=${'role' + u.id}>${u.roles.map(role => role.name.substring(5)).join(', ')}</td>
            </tr>`;
            data.innerHTML = temp;
            panel.innerHTML = `<h5>${u.email} with roles: ${u.roles.map(role => role.name.substring(5)).join(', ')}</h5>`
        });
}

userAuthInfo()