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

        post('auth/login', body);
    });
});

async function get(path, token, datas) {
    let headers = {};
    let url = `http://localhost:8080/${path}`;

    if(token && token != '') {
        headers = {
            'Content-Type': 'application/json',
            'authentication': `Bearer ${token}`
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

    let res = await fetch(url, options)
    let data = res.json();

    if(res.ok) {
        console.log(data);
    }

    throw new Error(data);
}

async function post(path, datas) {
    let headers = {
        'Content-Type': 'application/json'
    };
    let url = `http://localhost:8080/${path}`;

    let options = {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(datas)
    };

    let res = await fetch(url, options);
    let data = res.json();

    if(res.ok) {
        console.log(data);
    }

    throw new Error(data);
}