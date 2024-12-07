import React from 'react';
import ReactDOM from 'react-dom/client';
import '../css/styles.css'; // Подключение глобальных стилей (опционально)

// Главный компонент приложения
const App = () => {
    return (
        <div>
            <h1>Welcome to Find My Doc!</h1>
            <p>Your application is now running.</p>
        </div>
    );
};

// Найдите элемент с id="root" в index.html и отрендерите приложение
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<App />);
