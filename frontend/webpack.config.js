// frontend/webpack.config.js

const path = require('path'); // Модуль для работы с путями файловой системы
const HtmlWebpackPlugin = require('html-webpack-plugin'); // Плагин для генерации HTML-файлов
const MiniCssExtractPlugin = require('mini-css-extract-plugin'); // Плагин для извлечения CSS в отдельные файлы

module.exports = {
    // Определение нескольких точек входа для разных частей приложения
    entry: {
        main: './src/js/index.js', // Точка входа для основной части приложения
        register: './src/js/register.js', // Точка входа для страницы регистрации
        dashboard: './src/js/dashboard.js', // Точка входа для панели управления
        login: './src/js/login.js' // Точка входа для страницы входа
    },
    output: {
        path: path.resolve(__dirname, 'build'), // Путь к выходной директории
        filename: 'js/[name].bundle.js', // Имя выходных файлов бандлов
        publicPath: '/', // Базовый путь для всех ассетов
        clean: true, // Очистка папки сборки перед каждой сборкой
    },
    module: {
        rules: [
            {
                // Обработка JavaScript и JSX файлов с помощью Babel
                test: /\.(js|jsx)$/, // Регулярное выражение для поиска файлов .js и .jsx
                exclude: /node_modules/, // Исключение директории node_modules из обработки
                use: {
                    loader: 'babel-loader', // Лоадер для транспиляции кода с помощью Babel
                    options: {
                        presets: [
                            '@babel/preset-env', // Поддержка современных возможностей JavaScript
                            '@babel/preset-react' // Поддержка синтаксиса React JSX
                        ]
                    }
                }
            },
            {
                // Обработка CSS файлов
                test: /\.css$/, // Регулярное выражение для поиска файлов .css
                use: [
                    MiniCssExtractPlugin.loader, // Извлечение CSS в отдельные файлы
                    'css-loader', // Лоадер для интерпретации @import и url() как import/require()
                ],
            },
            {
                // Обработка файлов изображений
                test: /\.(png|jpg|jpeg|gif|svg)$/, // Регулярное выражение для поиска файлов изображений
                type: 'asset/resource', // Тип модуля для обработки ресурсов
                generator: {
                    filename: 'images/[name][ext][query]' // Путь и имя выходных файлов изображений
                },
            },
            {
                // Обработка файлов шрифтов
                test: /\.(woff|woff2|eot|ttf|otf)$/, // Регулярное выражение для поиска файлов шрифтов
                type: 'asset/resource', // Тип модуля для обработки ресурсов
                generator: {
                    filename: 'fonts/[name][ext][query]' // Путь и имя выходных файлов шрифтов
                },
            },
        ],
    },
    plugins: [
        new MiniCssExtractPlugin({
            filename: 'css/[name].css', // Имя выходных CSS файлов
        }),
        new HtmlWebpackPlugin({
            filename: 'index.html', // Имя выходного HTML файла
            template: './src/pages/index/index.html', // Шаблон HTML
            chunks: ['main'], // Включение только main.js и main.css
        }),
        new HtmlWebpackPlugin({
            filename: 'login.html', // Имя HTML файла для страницы входа
            template: './src/pages/login/login.html', // Шаблон HTML для страницы входа
            chunks: ['login'], // Включение только login.js и соответствующих CSS
        }),
        new HtmlWebpackPlugin({
            filename: 'register.html', // Имя HTML файла для страницы регистрации
            template: './src/pages/register/register.html', // Шаблон HTML для страницы регистрации
            chunks: ['register'], // Включение только register.js и соответствующих CSS
        }),
        new HtmlWebpackPlugin({
            filename: 'dashboard.html', // Имя HTML файла для панели управления
            template: './src/pages/dashboard/dashboard.html', // Шаблон HTML для панели управления
            chunks: ['dashboard'], // Включение только dashboard.js и соответствующих CSS
        }),
    ],
    resolve: {
        extensions: ['.js', '.jsx'], // Расширения файлов, которые будут автоматически резолвиться
    },
    devServer: {
        static: {
            directory: path.join(__dirname, 'build'), // Директория для обслуживания статических файлов
        },
        historyApiFallback: true, // Поддержка истории навигации для SPA
        port: 3000, // Порт для запуска dev-сервера
        open: true, // Автоматическое открытие браузера при запуске сервера
        hot: true, // Включение горячей перезагрузки модулей
    },
    mode: 'production', // Режим сборки (development или production)
};