
import '../css/styles.css'; // Подключение глобальных стилей (опционально)
import '../css/login.css'; // Подключение глобальных стилей (опционально)

const loginForm = document.getElementById('loginForm');
const errorMessage = document.getElementById('errorMessage');
const loadingIndicator = document.getElementById('loading');

function showError(message) {
    errorMessage.textContent = message;
    errorMessage.style.display = 'block';
    errorMessage.classList.add('shake');
    setTimeout(() => errorMessage.classList.remove('shake'), 500);
}

function clearError() {
    errorMessage.style.display = 'none';
    errorMessage.textContent = '';
}

function showLoading(show) {
    loadingIndicator.style.display = show ? 'block' : 'none';
    loginForm.querySelector('button').disabled = show;
}

// Очищаем ошибку при вводе
document.getElementById('email').addEventListener('input', clearError);
document.getElementById('password').addEventListener('input', clearError);

loginForm.addEventListener('submit', async function (e) {
    e.preventDefault();
    clearError();
    showLoading(true);

    const formData = {
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };

    try {
        // Шаг 1: Аутентификация
        const response = await fetch('/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || 'Ошибка аутентификации');
        }

        if (!data.token) {
            throw new Error('Токен не получен от сервера');
        }

        // Сохраняем токен и email
        localStorage.setItem('token', data.token);
        localStorage.setItem('userEmail', data.email);

        // Шаг 2: Переход на dashboard
        window.location.href = '/dashboard';

    } catch (error) {
        console.error('Error:', error);
        showError(error.message || 'Произошла ошибка при входе в систему');
    } finally {
        showLoading(false);
    }
});

// Проверяем наличие токена при загрузке страницы
document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('token');
    if (token) {
        // Если токен есть, проверяем его валидность
        fetch('/dashboard', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(response => {
            if (response.ok) {
                // Если токен валидный, перенаправляем на dashboard
                window.location.href = '/dashboard';
            } else {
                // Если токен невалидный, удаляем его
                localStorage.removeItem('token');
                localStorage.removeItem('userEmail');
            }
        }).catch(() => {
            localStorage.removeItem('token');
            localStorage.removeItem('userEmail');
        });
    }
});