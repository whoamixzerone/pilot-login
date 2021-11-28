document.addEventListener('DOMContentLoaded', event => {
    const $btn_login = document.getElementById('btn_login');

    $btn_login.addEventListener('click', evt => {
        evt.preventDefault();

        let act = document.getElementById('account').value;
        let pwd = document.getElementById('password').value;
        const body = {
            'account': act,
            'password': pwd
        };

//        let token = post('auth/login', body);
//        get('v1/users/me', token)
        post('auth/login', body).then(data => {
            localStorage.setItem('jwt', data.accessToken);
            console.log(data.accessToken);

            get('v1/users/me', localStorage.getItem('jwt')).then(data => console.log(data));
        });
    });
});

function get(path, token) {
    let headers = {};
    let url = `http://localhost:8080/${path}`;

    if(token && token != '') {
        headers = {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        };
    } else {
        headers = {
            'Content-Type': 'application/json'
        };
    }

    let options = {
        method: 'GET',
        headers: headers
    };

//    let res = await fetch(url, options)
//    let data = await res.json();

    return fetch(url, options).then(res => res.json());

//    if(res.ok) {
//        console.log(data);
//        return;
//    }
//
//    throw new Error(data);
}

function post(path, datas) {
    let headers = {
        'Content-Type': 'application/json'
    };
    let url = `http://localhost:8080/${path}`;

    let options = {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(datas)
    };

    return fetch(url, options).then(res => res.json());

//    if(res.ok) {
//        localStorage.setItem('jwt', data.accessToken);
//        return data.accessToken;
//    }

//    throw new Error(data);
}