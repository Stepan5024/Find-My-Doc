// frontend/webpack.config.js

const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');

module.exports = {
    // Define multiple entry points
    entry: {
        main: './src/js/index.js',
        register: './src/js/register.js',
        dashboard: './src/js/dashboard.js',
        login: './src/js/login.js'
    },
    output: {
        path: path.resolve(__dirname, 'build'),
        filename: 'js/[name].bundle.js', // Output bundles to js/ directory
        publicPath: '/', // Base path for all assets
        clean: true, // Clean build folder before each build
    },
    module: {
        rules: [
            {
                // Process JavaScript and JSX files with Babel
                test: /\.(js|jsx)$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: [
                            '@babel/preset-env', // Support modern JavaScript features
                            '@babel/preset-react' // Support React JSX
                        ]
                    }
                }
            },
            {
                // Process CSS files
                test: /\.css$/,
                use: [
                    MiniCssExtractPlugin.loader,
                    'css-loader',
                ],
            },
            {
                // Process image files
                test: /\.(png|jpg|jpeg|gif|svg)$/,
                type: 'asset/resource',
                generator: {
                    filename: 'images/[name][ext][query]'
                },
            },
            {
                // Process font files
                test: /\.(woff|woff2|eot|ttf|otf)$/,
                type: 'asset/resource',
                generator: {
                    filename: 'fonts/[name][ext][query]'
                },
            },
        ],
    },
    plugins: [
        new MiniCssExtractPlugin({
            filename: 'css/[name].css',
        }),
        new HtmlWebpackPlugin({
            filename: 'index.html',
            template: './src/index.html',
            chunks: ['main'], // Includes only main.js and main.css
        }),
        new HtmlWebpackPlugin({
            filename: 'login.html',
            template: './src/login.html',
            chunks: ['login'],
        }),
        new HtmlWebpackPlugin({
            filename: 'register.html',
            template: './src/register.html',
            chunks: ['register'],
        }),
        new HtmlWebpackPlugin({
            filename: 'dashboard.html',
            template: './src/dashboard.html',
            chunks: ['dashboard'],
        }),
    ],
    resolve: {
        extensions: ['.js', '.jsx'],
    },
    devServer: {
        static: {
            directory: path.join(__dirname, 'build'),
        },
        historyApiFallback: true,
        port: 3000,
        open: true,
        hot: true,
    },
    mode: 'production',
};