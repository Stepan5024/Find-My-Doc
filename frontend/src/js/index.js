import '../css/styles.css';


document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('token');
    const loginButton = document.getElementById('loginButton');
    const registerButton = document.getElementById('registerButton');
    const dashboardButton = document.getElementById('dashboardButton');

    if (token) {
        // Пользователь залогинен
        loginButton.style.display = 'none';
        registerButton.style.display = 'none';
        dashboardButton.style.display = 'inline-block';
    } else {
        // Пользователь не залогинен
        loginButton.style.display = 'inline-block';
        registerButton.style.display = 'inline-block';
        dashboardButton.style.display = 'none';
    }
});
