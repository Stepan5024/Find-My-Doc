import '../css/styles.css'; // Подключение глобальных стилей (опционально)
import '../css/register.css'; // Подключение глобальных стилей (опционально)

console.log('API Base URL:', config.apiBaseUrl); // Добавлено для проверки


import { config } from '../config/config';

const requestUrl = `${config.apiBaseUrl}/auth/register`;

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

    console.log('Form submission initiated. Collected data:', formData);

    try {
        // Получение IP-адреса
        console.log('Fetching client IP address...');
        const ipResponse = await fetch('https://api.ipify.org?format=json');
        if (!ipResponse.ok) {
            throw new Error(`Failed to fetch IP address: ${ipResponse.statusText}`);
        }
        const ipData = await ipResponse.json();
        console.log('Client IP address detected:', ipData.ip);

        // Логирование запроса на регистрацию
        console.log(`Preparing to send POST request to URL: ${requestUrl}`);
        console.log('Request payload:', JSON.stringify(formData));
        console.log('Sending request from IP address:', ipData.ip);

        const response = await fetch(requestUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        console.log('Request sent. Awaiting response...');
        if (!response.ok) {
            console.error(`Request to ${requestUrl} failed with status code:`, response.status);
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        console.log(`Response received from ${requestUrl}:`, data);

        if (data.token) {
            console.log('Registration successful. Token received:', data.token);
            console.log('Storing token and email in local storage...');

            // Сохраняем токен и email
            localStorage.setItem('token', data.token);
            localStorage.setItem('userEmail', data.email);

            console.log('Redirecting user to /dashboard...');
            window.location.href = '/dashboard';
        } else {
            console.warn('Token not received in response. Redirecting to /dashboard for debugging.');
            window.location.href = '/dashboard'; // TODO потом убрать
        }
    } catch (error) {
        console.error('Error occurred during the registration process:', error.message);
        alert('Failed to register. Please check your details and try again.');
    }
});
