import '../css/styles.css'; // Подключение глобальных стилей (опционально)
import '../css/register.css'; // Подключение глобальных стилей (опционально)

document.getElementById('registerForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = {
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        firstName: document.getElementById('firstName').value,
        lastName: document.getElementById('lastName').value,
        middleName: document.getElementById('middleName').value,
        phoneNumber: document.getElementById('phoneNumber').value
    };

    try {
        const response = await fetch('/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();

        if (data.token) {
            // Сохраняем токен и email
            localStorage.setItem('token', data.token);
            localStorage.setItem('userEmail', data.email);

            // Перенаправляем на dashboard
            window.location.href = '/dashboard';
        } else {
            alert('Token not received');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Failed to register');
    }
});