// find-my-doc/front/vite.config.ts
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import rewriteAll from 'vite-plugin-rewrite-all';
import path from 'path';

export default defineConfig({
  plugins: [
    react(), // Плагин для поддержки React
    rewriteAll(), // Плагин для обработки клиентских маршрутов
  ],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'), // Алиас для удобного импорта
    },
  },
  build: {
    outDir: './dist', // Сборка в папку find-my-doc/front/dist
  },
});