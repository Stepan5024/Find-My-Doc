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

    console.log('Form submission started with data:', formData);

    try {
        // Получение IP-адреса
        const ipResponse = await fetch('https://api.ipify.org?format=json');
        if (!ipResponse.ok) {
            throw new Error(`Failed to fetch IP address: ${ipResponse.statusText}`);
        }
        const ipData = await ipResponse.json();
        console.log('Detected client IP:', ipData.ip);

        // Логирование запроса регистрации
        console.log('Sending registration request to /auth/register');

        const response = await fetch('/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        if (!response.ok) {
            console.error('Registration request failed with status:', response.status);
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        console.log('Server response:', data);

        if (data.token) {
            console.log('Registration successful. Token received:', data.token);

            // Сохраняем токен и email
            localStorage.setItem('token', data.token);
            localStorage.setItem('userEmail', data.email);

            // Перенаправляем на dashboard
            console.log('Redirecting to /dashboard');
            window.location.href = '/dashboard';
        } else {
            console.warn('Token not received in response. Redirecting to /dashboard for debugging.');
            window.location.href = '/dashboard'; // TODO потом убрать
        }
    } catch (error) {
        console.error('An error occurred during the registration process:', error);
        alert('Failed to register. Please try again.');
    }
});
